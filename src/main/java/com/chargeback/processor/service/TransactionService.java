package com.chargeback.processor.service;

import com.chargeback.processor.dto.TransactionResponseDto;
import com.chargeback.processor.entity.Transaction;
import com.chargeback.processor.model.MatchItems;
import com.chargeback.processor.model.MatchResult;
import com.chargeback.processor.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public List<TransactionResponseDto> findAll() {
        log.info("Returning all transactions records at [{}]", LocalDateTime.now());
        return transactionRepository.findAll()
                .stream()
                .map(Transaction::toResponseDto)
                .collect(Collectors.toList());
    }

    public MatchResult findByMatchItems(final MatchItems matchItems) {
        final LocalDate clientTransactionDate = matchItems.getDate().toLocalDate();
        final Double clientTransactionAmount = Math.abs(Double.parseDouble(matchItems.getAmount()));
        boolean errAmount = false;
        boolean errDate = false;

        final Optional<Transaction> transactionOpt = transactionRepository
                .findByCardBinAndCardLastFour(matchItems.getFirstSixNumbers(), matchItems.getLastFourNumbers());

        if (transactionOpt.isPresent()) {
            Transaction transaction = transactionOpt.get();

            Double transactionAmount = (transaction.getTotalAmount().describeConstable().orElse(0.0));

            errAmount = clientTransactionAmount.compareTo(transactionAmount) != 0;

            if (!errAmount) {
                LocalDate transactionDate = transaction.getDateCreated().toLocalDate();
                errDate = clientTransactionDate.compareTo(transactionDate) != 0;
            }
        }

        return MatchResult.builder()
                .transaction(transactionOpt.orElse(null))
                .isOk(!errAmount && !errDate && transactionOpt.isPresent())
                .isErrCard(transactionOpt.isEmpty())
                .isErrAmount(errAmount)
                .isErrDate(errDate)
                .build();
    }

    public void markChargeback(final Transaction transaction) {
        transaction.setChargeBackDate(transaction.getDateCreated());
        transaction.setIsChargedBack(true);
        transaction.setChargeBackAmount(transaction.getTotalAmount());
        transactionRepository.save(transaction);
    }
}
