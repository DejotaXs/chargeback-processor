package com.chargeback.processor.component;

import com.chargeback.processor.model.TableColumn;
import com.chargeback.processor.model.TableStruct;
import com.chargeback.processor.repository.ChargebackRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

@Slf4j
@Component
@AllArgsConstructor
public class DynamicTableComponent implements ChargebackRepository {
    private final Statement dbStatement;

    @Override
    public void save(final TableStruct tableStruct) {
        final String tableName = tableStruct.getName();
        log.info("Saving table [{}]", tableName);

        tableStruct.getTableColumns().forEach(column -> {
            try {
                insertLine(column, tableName);
            } catch (SQLException e) {
                // Not break the process because only one register.
                log.error("Error at trying to insert column: [{}] at [{}]", column, LocalDateTime.now());
            }
        });
    }

    @Override
    public void createTableIfNotExists(final TableStruct tableStruct) throws SQLException {
        if (!isTableCreated(tableStruct.getName())) {
            log.info("Creating new table [{}]", tableStruct.getName());
            createTable(tableStruct);
        }
    }

    private void insertLine(final TableColumn tableColumn, final String tableName) throws SQLException {
        final String sql = "INSERT INTO " + tableName + " VALUES ( " + mountLine(tableColumn) + " ) ";
        dbStatement.executeUpdate(sql);
    }

    private String mountLine(final TableColumn tableColumn) {
        final StringBuilder sb = new StringBuilder();
        sb.append("'").append(tableColumn.getInfo().get(0)).append("'");

        tableColumn.getInfo().stream()
                .skip(1)
                .forEach(info -> sb.append(" , '").append(info).append("'"));

        return sb.toString();
    }

    private boolean isTableCreated(final String tableName) {
        final String sql = String.format("SELECT count(*) FROM %s", tableName);

        try {
            final ResultSet rs = dbStatement.executeQuery(sql);
            rs.close();
            log.info("Table [{}] already exists", tableName);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private void createTable(final TableStruct tableStruct) throws SQLException {
        final StringBuilder sb = new StringBuilder();

        sb.append("CREATE TABLE ").append(tableStruct.getName());

        sb.append(" ( ").append(tableStruct.getColumns().get(0)).append(" VARCHAR(255) ");

        tableStruct.getColumns()
                .stream()
                .skip(1)
                .forEach(column -> sb.append(", ").append(column).append(" VARCHAR(255) "));

        final String sql = sb.append(")").toString();

        dbStatement.executeUpdate(sql);
    }
}
