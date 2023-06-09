package com.chargeback.processor.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class TableStruct {
    private String name;
    private List<String> columns;
    private List<TableColumn> tableColumns;
}

