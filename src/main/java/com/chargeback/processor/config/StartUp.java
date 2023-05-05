package com.chargeback.processor.config;

import com.chargeback.processor.entity.Transaction;
import com.chargeback.processor.model.TransactionInput;
import com.chargeback.processor.repository.TransactionRepository;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static com.chargeback.processor.util.ConstantsUtil.FIRST_LINE;
import static com.chargeback.processor.util.ConstantsUtil.SEPARATOR;
import static com.chargeback.processor.util.ConstantsUtil.TRANSACTION_SAMPLE_PATH;

@Slf4j
@AllArgsConstructor
@Component
public class StartUp implements CommandLineRunner {

    private final TransactionRepository transactionRepository;

    @Override
    public void run(String... args) {
        try {
            final List<Transaction> transactionList = parseCsvFile()
                    .stream()
                    .skip(FIRST_LINE)
                    .map(TransactionInput::toTransaction)
                    .toList();

            transactionRepository.saveAll(transactionList);
        } catch (FileNotFoundException ex) {
            log.error("Error occurred when tried to open csv sample file");
            // The meaning here is do not break the system. On the future could have
            // a feature for import the files for Transaction table.
        }
    }

    private List<TransactionInput> parseCsvFile() throws FileNotFoundException {
        return new CsvToBeanBuilder<TransactionInput>(new FileReader(TRANSACTION_SAMPLE_PATH))
                .withSeparator(SEPARATOR)
                .withMappingStrategy(setColumMapping())
                .build()
                .parse();
    }

    private static ColumnPositionMappingStrategy<TransactionInput> setColumMapping() {
        ColumnPositionMappingStrategy<TransactionInput> strategy = new ColumnPositionMappingStrategy<>();
        Class<TransactionInput> targetClass = TransactionInput.class;
        strategy.setType(targetClass);
        Field[] classFields = targetClass.getDeclaredFields();
        Object[] objClassFieldNames = Arrays.stream(classFields).map(Field::getName).toList().toArray();
        String[] columns = Arrays.copyOf(objClassFieldNames, objClassFieldNames.length, String[].class);
        strategy.setColumnMapping(columns);
        return strategy;
    }
}
