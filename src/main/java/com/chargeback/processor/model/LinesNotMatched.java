package com.chargeback.processor.model;

import lombok.Builder;

@Builder
public record LinesNotMatched(
        long total,
        long errCard,
        long errAmount,
        long errDate
) {
}
