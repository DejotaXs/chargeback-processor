package com.chargeback.processor.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
public class MatchItems {
    private String firstSixNumbers;
    private String lastFourNumbers;
    private String amount;
    private LocalDateTime date;
}
