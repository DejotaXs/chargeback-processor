package com.chargeback.processor.service;

import com.chargeback.processor.component.FileProcessor;
import com.chargeback.processor.model.MatchItems;
import com.chargeback.processor.model.TableColumn;
import com.chargeback.processor.model.TableStruct;
import com.chargeback.processor.util.ParseUtils;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReaderBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.chargeback.processor.enums.ClientRowEnum.findAmountPostitionByTableName;
import static com.chargeback.processor.enums.ClientRowEnum.findDatePostitionByTableName;
import static com.chargeback.processor.util.ConstantsUtil.CSV_EXTENSION;
import static com.chargeback.processor.util.ConstantsUtil.FIRST_LINE;
import static com.chargeback.processor.util.ConstantsUtil.HEADER_LINE;
import static com.chargeback.processor.util.ConstantsUtil.SEPARATOR;
import static com.chargeback.processor.util.ParseUtils.camelToSnakeUpper;
import static com.chargeback.processor.util.ParseUtils.parseDateByTable;

@Service
public class CsvFileService implements FileProcessor<TableStruct> {

    public TableStruct retrieveStructFromFile(
            final MultipartFile file,
            final Optional<Integer> dateRowNumber,
            final Optional<String> dateFormat,
            final Optional<Integer> amountRowNumber) throws IOException {

        final String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        final Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));

        final CSVParser parser = new CSVParserBuilder().withSeparator(SEPARATOR).build();

        final List<String[]> lines = new CSVReaderBuilder(reader)
                .withCSVParser(parser)
                .build()
                .readAll();

        return convertToStruct(fileName, lines, dateRowNumber, dateFormat, amountRowNumber);
    }

    private TableStruct convertToStruct(final String fileName,
                                        final List<String[]> lines,
                                        final Optional<Integer> dateRowNumber,
                                        final Optional<String> dateFormat,
                                        final Optional<Integer> amountRowNumber) {

        final String formattedName = camelToSnakeUpper(fileName.replace(CSV_EXTENSION, ""));

        final List<String> fileHeader = Arrays.stream(lines.get(HEADER_LINE))
                .map(ParseUtils::spaceToSnakeUpper)
                .toList();

        final List<TableColumn> tableColumns = lines.stream()
                .skip(FIRST_LINE)
                .map(line -> tableColumnBuilder(line, formattedName, dateRowNumber, dateFormat, amountRowNumber))
                .toList();


        return TableStruct.builder()
                .name(formattedName)
                .columns(fileHeader)
                .tableColumns(tableColumns)
                .build();
    }

    private TableColumn tableColumnBuilder(final String[] line,
                                           final String tableName,
                                           final Optional<Integer> dateRowNumber,
                                           final Optional<String> dateFormat,
                                           final Optional<Integer> amountRowNumber) {
        String firstSixNumbers = null;
        String lastFourNumbers = null;

        final List<String> rawLines = Arrays.stream(line).toList();

        final List<String> prettyList = rawLines.stream()
                .map(it -> it.replace("'", ""))
                .toList();

        final Optional<String> optCardNumber = prettyList.stream()
                .filter(item -> item.length() == 16)
                .filter(item -> item.contains("*") || item.contains("."))
                .findFirst();

        final String date =
                prettyList.get(dateRowNumber.orElseGet(() -> findDatePostitionByTableName(tableName)) - 1);

        final String amount =
                prettyList.get(amountRowNumber.orElseGet(() -> findAmountPostitionByTableName(tableName)) - 1);

        final LocalDateTime localDateTime = parseDateByTable(tableName, date, dateFormat);

        if (optCardNumber.isPresent()) {
            final String cardNumber = optCardNumber.get();
            int lastIndex = cardNumber.length();
            firstSixNumbers = cardNumber.substring(0, 6);
            lastFourNumbers = cardNumber.substring(lastIndex - 4, lastIndex);
        }

        final MatchItems matchItems = buildMatchItems(amount, localDateTime, firstSixNumbers, lastFourNumbers);

        return TableColumn.builder()
                .info(prettyList)
                .matchItems(matchItems)
                .build();
    }

    private MatchItems buildMatchItems(final String amount,
                                       final LocalDateTime localDateTime,
                                       final String firstSixNumbers,
                                       final String lastFourNumbers) {
        return MatchItems.builder()
                .amount(amount)
                .date(localDateTime)
                .firstSixNumbers(firstSixNumbers)
                .lastFourNumbers(lastFourNumbers)
                .build();
    }
}
