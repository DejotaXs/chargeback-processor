package com.chargeback.processor.model;

import com.chargeback.processor.entity.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class MatchResult {
    private Transaction transaction;
    private boolean isOk;
    private boolean isErrCard;
    private boolean isErrAmount;
    private boolean isErrDate;
}
