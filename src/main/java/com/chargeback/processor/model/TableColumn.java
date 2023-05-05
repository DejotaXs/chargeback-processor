package com.chargeback.processor.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class TableColumn {
    private List<String> info;
    private MatchItems matchItems;
}

