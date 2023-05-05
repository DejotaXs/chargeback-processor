package com.chargeback.processor.repository;

import com.chargeback.processor.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findByCardBinAndCardLastFour(@Param("cardBin") String firstSixNumbers,
                                                       @Param("cardLastFour") String lastFourNumbers);
}