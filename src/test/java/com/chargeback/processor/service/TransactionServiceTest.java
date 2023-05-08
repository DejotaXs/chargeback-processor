package com.chargeback.processor.service;

import com.chargeback.processor.dto.TransactionResponseDto;
import com.chargeback.processor.entity.Transaction;
import com.chargeback.processor.model.MatchItems;
import com.chargeback.processor.model.MatchResult;
import com.chargeback.processor.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class TransactionServiceTest {

    private static final String CARD_BIN_MOCK  = "012345";
    private static final String CARD_LAST_FOUR_MOCK  = "0123";
    private static final String TRANSACTION_AMOUNT_MOCK  = "1.0";

    private static final LocalDateTime DATE_MOCK = LocalDateTime.of(2023, 1, 1, 0, 0);

    @Mock
    private TransactionRepository transactionRepositoryMock;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void findAll() {
        final List<Transaction> expectedTransactionList = List.of(buildTransaction());

        doReturn(expectedTransactionList).when(transactionRepositoryMock).findAll();

        final List<TransactionResponseDto> resultTransactionList = transactionService.findAll();

        assertNotNull(resultTransactionList);
        assertEquals(expectedTransactionList.size(), resultTransactionList.size());
        assertEquals(expectedTransactionList.get(0).getAchAccountNumber(), resultTransactionList.get(0).achAccountNumber());
        assertEquals(expectedTransactionList.get(0).getAchBankName(), resultTransactionList.get(0).achBankName());

        verify(transactionRepositoryMock, times(1)).findAll();
    }

    @Test
    void markChargeback() {
        final Transaction genericTransaction = buildTransaction();
        final Transaction expectedTransaction = buildTransaction(true, 1.0);

        doReturn(expectedTransaction).when(transactionRepositoryMock).save(genericTransaction);

        transactionService.markChargeback(genericTransaction);

        verify(transactionRepositoryMock, times(1)).save(genericTransaction);
    }

    @Test
    void findTransactionNullByMatchItemsAndErrCard() {
        final Optional<Transaction> expectedTransactionOpt = Optional.empty();

        MatchItems matchItems = buildMatchItems(TRANSACTION_AMOUNT_MOCK, DATE_MOCK);

        doReturn(expectedTransactionOpt).when(transactionRepositoryMock).findByCardBinAndCardLastFour(
                CARD_BIN_MOCK, CARD_LAST_FOUR_MOCK
        );

        final MatchResult resultMacth = transactionService.findByMatchItems(matchItems);

        assertNotNull(resultMacth);

        assertNull(resultMacth.getTransaction());

        assertFalse(resultMacth.isOk());
        assertTrue(resultMacth.isErrCard());
        assertFalse(resultMacth.isErrAmount());
        assertFalse(resultMacth.isErrDate());

        verify(transactionRepositoryMock, times(1))
                .findByCardBinAndCardLastFour(CARD_BIN_MOCK, CARD_LAST_FOUR_MOCK);
    }

    @Test
    void findTransactionByMatchItemsAndErrAmount() {
        final Optional<Transaction> expectedTransactionOpt = Optional.of(buildTransaction());

        MatchItems matchItems = buildMatchItems("0.0", DATE_MOCK);

        doReturn(expectedTransactionOpt).when(transactionRepositoryMock).findByCardBinAndCardLastFour(
                CARD_BIN_MOCK, CARD_LAST_FOUR_MOCK
        );

        final MatchResult resultMacth = transactionService.findByMatchItems(matchItems);

        assertNotNull(resultMacth);

        assertNotNull(resultMacth.getTransaction());

        assertFalse(resultMacth.isOk());
        assertFalse(resultMacth.isErrCard());
        assertTrue(resultMacth.isErrAmount());
        assertFalse(resultMacth.isErrDate());

        verify(transactionRepositoryMock, times(1))
                .findByCardBinAndCardLastFour(CARD_BIN_MOCK, CARD_LAST_FOUR_MOCK);
    }

    @Test
    void findTransactionByMatchItemsAndErrDate() {
        final Optional<Transaction> expectedTransactionOpt = Optional.of(buildTransaction());

        MatchItems matchItems = buildMatchItems(TRANSACTION_AMOUNT_MOCK, LocalDateTime.now());

        doReturn(expectedTransactionOpt).when(transactionRepositoryMock).findByCardBinAndCardLastFour(
                CARD_BIN_MOCK, CARD_LAST_FOUR_MOCK
        );

        final MatchResult resultMacth = transactionService.findByMatchItems(matchItems);

        assertNotNull(resultMacth);

        assertNotNull(resultMacth.getTransaction());

        assertFalse(resultMacth.isOk());
        assertFalse(resultMacth.isErrCard());
        assertFalse(resultMacth.isErrAmount());
        assertTrue(resultMacth.isErrDate());

        verify(transactionRepositoryMock, times(1))
                .findByCardBinAndCardLastFour(CARD_BIN_MOCK, CARD_LAST_FOUR_MOCK);
    }

    @Test
    void findTransactionByMatchItemsAndOk() {
        final Optional<Transaction> expectedTransactionOpt = Optional.of(buildTransaction());

        MatchItems matchItems = buildMatchItems(TRANSACTION_AMOUNT_MOCK, DATE_MOCK);

        doReturn(expectedTransactionOpt).when(transactionRepositoryMock).findByCardBinAndCardLastFour(
                CARD_BIN_MOCK, CARD_LAST_FOUR_MOCK
        );

        final MatchResult resultMacth = transactionService.findByMatchItems(matchItems);

        assertNotNull(resultMacth);

        assertNotNull(resultMacth.getTransaction());

        assertTrue(resultMacth.isOk());
        assertFalse(resultMacth.isErrCard());
        assertFalse(resultMacth.isErrAmount());
        assertFalse(resultMacth.isErrDate());

        verify(transactionRepositoryMock, times(1))
                .findByCardBinAndCardLastFour(CARD_BIN_MOCK, CARD_LAST_FOUR_MOCK);
    }

    private Transaction buildTransaction() {
        return buildTransaction(null, null);
    }
    private Transaction buildTransaction(Boolean isChargedBack, Double chargeBackAmount) {
        return Transaction.builder()
                .achAccountNumber("123456")
                .achBankName("Bank Of Netherlands")
                .cardBin(CARD_BIN_MOCK)
                .cardLastFour(CARD_LAST_FOUR_MOCK)
                .totalAmount(Double.valueOf(TRANSACTION_AMOUNT_MOCK))
                .isChargedBack(isChargedBack)
                .chargeBackAmount(chargeBackAmount)
                .dateCreated(DATE_MOCK)
                .build();
    }

    private MatchItems buildMatchItems(String amount, LocalDateTime date){
        return MatchItems.builder()
                .firstSixNumbers(CARD_BIN_MOCK)
                .lastFourNumbers(CARD_LAST_FOUR_MOCK)
                .amount(amount)
                .date(date)
                .build();
    }
}