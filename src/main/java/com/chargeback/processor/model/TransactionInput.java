package com.chargeback.processor.model;

import com.chargeback.processor.entity.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.chargeback.processor.util.ConstantsUtil.TRANSACTION_TABLE_NAME;
import static com.chargeback.processor.util.ParseUtils.parseDateByTable;
import static com.chargeback.processor.util.ParseUtils.parseDoubleOrNull;
import static com.chargeback.processor.util.ParseUtils.parseIntegerOrNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionInput {
    private String transactionId;
    private String parentTxnId;
    private String merchant;
    private String merchantDescriptor;
    private String midNumber;
    private String merchantId;
    private String dateCreated;
    private String txnType;
    private String responseType;
    private String responseText;
    private String authCode;
    private String cvvResponse;
    private String avsResponse;
    private String merchantTxnId;
    private String totalAmount;
    private String orderId;
    private String clientOrderId;
    private String billingCycleNumber;
    private String recycleNumber;
    private String campaignId;
    private String campaignName;
    private String orderType;
    private String customerId;
    private String paySource;
    private String cardBin;
    private String cardLastFour;
    private String cardType;
    private String achBankName;
    private String achRoutingNumber;
    private String achAccountNumber;
    private String affId;
    private String sourceValueOne;
    private String sourceValueTwo;
    private String sourceValueThree;
    private String sourceValueFour;
    private String sourceValueFive;
    private String isChargedBack;
    private String chargeBackAmount;
    private String chargeBackDate;
    private String chargeBackReasonCode;
    private String chargeBackNote;
    private String currencySymbol;

    private String dateUpdated;
    private String clientTxnId;
    private String surcharge;
    private String orderAgentName;
    private String campaignCategoryName;
    private String refundReason;
    private String paySourceId;
    private String customOne;
    private String customTwo;
    private String customThree;
    private String customFour;
    private String customFive;

    public Transaction toTransaction() {
        return Transaction.builder()
                .transactionId(Long.parseLong(this.transactionId))
                .achAccountNumber(this.achAccountNumber)
                .achBankName(this.achBankName)
                .achRoutingNumber(this.achRoutingNumber)
                .affId(this.affId)
                .authCode(this.authCode)
                .avsResponse(this.avsResponse)
                .billingCycleNumber(Long.valueOf(this.billingCycleNumber))
                .campaignCategoryName(this.campaignCategoryName)
                .campaignId(parseIntegerOrNull(this.campaignId))
                .campaignName(this.campaignName)
                .cardBin(this.cardBin)
                .cardLastFour(this.cardLastFour)
                .cardType(this.cardType)
                .chargeBackAmount(parseDoubleOrNull(this.chargeBackAmount))
                .chargeBackDate(parseDateByTable(TRANSACTION_TABLE_NAME, this.chargeBackDate))
                .chargeBackNote(this.chargeBackNote)
                .chargeBackReasonCode(this.chargeBackReasonCode)
                .clientOrderId(this.clientOrderId)
                .clientTxnId(this.clientTxnId)
                .currencySymbol(this.currencySymbol)
                .customOne(this.customOne)
                .customTwo(this.customTwo)
                .customThree(this.customThree)
                .customFour(this.customFour)
                .customFive(this.customFive)
                .customerId(Long.valueOf(this.customerId))
                .cvvResponse(this.cvvResponse)
                .dateCreated(parseDateByTable(TRANSACTION_TABLE_NAME, this.dateCreated))
                .dateUpdated(parseDateByTable(TRANSACTION_TABLE_NAME, this.dateUpdated))
                .isChargedBack(Boolean.getBoolean(this.isChargedBack))
                .merchant(this.merchant)
                .merchantDescriptor(this.merchantDescriptor)
                .merchantId(Long.valueOf(this.merchantId))
                .merchantTxnId(this.merchantTxnId)
                .midNumber(this.midNumber)
                .orderAgentName(this.orderAgentName)
                .orderId(this.orderId)
                .orderType(this.orderType)
                .parentTxnId(this.parentTxnId)
                .paySource(this.paySource)
                .paySourceId(this.paySourceId)
                .recycleNumber(parseIntegerOrNull(this.recycleNumber))
                .refundReason(this.refundReason)
                .responseText(this.responseText)
                .responseType(this.responseType)
                .sourceValueOne(this.sourceValueOne)
                .sourceValueTwo(this.sourceValueTwo)
                .sourceValueThree(this.sourceValueThree)
                .sourceValueFour(this.sourceValueFour)
                .sourceValueFive(this.sourceValueFive)
                .surcharge(parseDoubleOrNull(this.surcharge))
                .totalAmount(parseDoubleOrNull(this.totalAmount))
                .txnType(this.txnType)
                .build();
    }
}
