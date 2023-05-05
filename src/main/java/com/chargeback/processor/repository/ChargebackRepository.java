package com.chargeback.processor.repository;

import com.chargeback.processor.model.TableStruct;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
public interface ChargebackRepository {
    void createTableIfNotExists(TableStruct tableStruct) throws SQLException;
    void save(TableStruct tableStruct);
}
