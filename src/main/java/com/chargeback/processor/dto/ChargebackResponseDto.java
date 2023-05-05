package com.chargeback.processor.dto;

import com.chargeback.processor.model.LinesNotMatched;
import lombok.Builder;

@Builder
public record ChargebackResponseDto(
        long rows,
        long lines,
        long linesMatched,
        LinesNotMatched linesNotMatched,
        long timeElapsed) {
}

