package com.chargeback.processor.service;

import com.chargeback.processor.component.FileProcessor;
import com.chargeback.processor.dto.ChargebackResponseDto;
import com.chargeback.processor.model.LinesNotMatched;
import com.chargeback.processor.model.MatchResult;
import com.chargeback.processor.model.TableStruct;
import com.chargeback.processor.repository.ChargebackRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChargebackService {

    private final ChargebackRepository chargebackRepository;
    private final FileProcessor<TableStruct> fileProcessor;
    private final TransactionService transactionService;

    public ChargebackResponseDto processFile(
            final MultipartFile multipartFile,
            final Optional<Integer> dateRowNumber,
            final Optional<String> dateFormat,
            final Optional<Integer> amountRowNumber)
            throws IOException, SQLException {
        final long initialTime = System.currentTimeMillis();

        final TableStruct tableStruct =
                fileProcessor.retrieveStructFromFile(multipartFile, dateRowNumber, dateFormat, amountRowNumber);

        chargebackRepository.createTableIfNotExists(tableStruct);

        chargebackRepository.save(tableStruct);

        final List<MatchResult> matchResults = tableStruct.getTableColumns()
                .stream()
                .map(column -> transactionService.findByMatchItems(column.getMatchItems()))
                .toList();

        matchResults.stream()
                .filter(MatchResult::isOk)
                .forEach(okResult -> transactionService.markChargeback(okResult.getTransaction()));


        return responseDto(tableStruct, matchResults, initialTime);
    }

    private ChargebackResponseDto responseDto(final TableStruct tableStruct,
                                              final List<MatchResult> matchResults,
                                              final long initialTime) {

        final long fileLines = tableStruct.getTableColumns().size();
        long errCard = 0;
        long errAmount = 0;
        long errDate = 0;
        long totalError = 0;

        long totalOk = matchResults.stream().filter(MatchResult::isOk).count();

        if (totalOk != fileLines) {
            errCard = matchResults.stream().filter(MatchResult::isErrCard).count();
            errAmount = matchResults.stream().filter(MatchResult::isErrAmount).count();
            errDate = matchResults.stream().filter(MatchResult::isErrDate).count();
            totalError = errAmount + errCard + errDate;
        }

        return ChargebackResponseDto.builder()
                .rows(tableStruct.getColumns().size())
                .lines(fileLines)
                .linesMatched(totalOk)
                .linesNotMatched(LinesNotMatched.builder()
                        .total(totalError)
                        .errDate(errDate)
                        .errAmount(errAmount)
                        .errCard(errCard)
                        .build())
                .timeElapsed(System.currentTimeMillis() - initialTime)
                .build();
    }
}
