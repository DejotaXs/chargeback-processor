package com.chargeback.processor.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TransactionResponseDto(
        String achAccountNumber,
        String achBankName,
        String achRoutingNumber,
        String avsResponse,
        String campaignCategoryName,
        String campaignName,
        String cardBin,
        String cardType,
        Double chargeBackAmount,
        LocalDateTime chargeBackDate,
        String chargeBackNote,
        String chargeBackReasonCode,
        String currencySymbol,
        String cvvResponse,
        LocalDateTime dateCreated,
        LocalDateTime dateUpdated,
        Boolean isChargedBack,
        String merchant) {
}
