package com.chargeback.processor.entity;

import com.chargeback.processor.dto.TransactionResponseDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "TRANSACTION_TABLE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction implements EntityPattern<TransactionResponseDto> {

    @Id
    @NotNull(message = "Transaction ID cannot be null")
    private long transactionId;

    @Size(max = 255, message = "Invalid length for Account Number")
    private String achAccountNumber;

    @Size(max = 255, message = "Invalid length for Bank Name")
    private String achBankName;

    @Size(max = 255, message = "Invalid length for Routing Number")
    private String achRoutingNumber;

    @Size(max = 255, message = "Invalid length for Aff Id")
    private String affId;

    @Size(max = 255, message = "Invalid length for Auth Code")
    private String authCode;

    @Size(max = 255, message = "Invalid length for Avs Response")
    private String avsResponse;

    private Long billingCycleNumber;

    @Size(max = 255, message = "Invalid length for Campaign Category Name")
    private String campaignCategoryName;

    private Integer campaignId;

    @Size(max = 255, message = "Invalid length for Campaign Name")
    private String campaignName;

    @Size(max = 255, message = "Invalid length for Card Bin")
    private String cardBin;

    @Size(max = 255, message = "Invalid length for Card Bin last 4 digits")
    @Column(name = "card_last_4")
    private String cardLastFour;

    @Size(max = 255, message = "Invalid length for Card type")
    private String cardType;

    private Double chargeBackAmount;

    private LocalDateTime chargeBackDate;

    @Size(max = 255, message = "Invalid length for Charge Back Note")
    private String chargeBackNote;

    @Size(max = 255, message = "Invalid length for charge back reason code")
    private String chargeBackReasonCode;

    @Size(max = 255, message = "Invalid length for client order id")
    private String clientOrderId;

    @Size(max = 255, message = "Invalid length for client txn id")
    private String clientTxnId;

    @Size(max = 255, message = "Invalid length for currency symbol")
    private String currencySymbol;

    @Size(max = 255, message = "Invalid length for custom 1")
    @Column(name = "custom_1")
    private String customOne;

    @Size(max = 255, message = "Invalid length for custom 2")
    @Column(name = "custom_2")
    private String customTwo;

    @Size(max = 255, message = "Invalid length for custom 3")
    @Column(name = "custom_3")
    private String customThree;

    @Size(max = 255, message = "Invalid length for custom 4")
    @Column(name = "custom_4")
    private String customFour;

    @Size(max = 255, message = "Invalid length for custom 5")
    @Column(name = "custom_5")
    private String customFive;

    private Long customerId;

    @Size(max = 255, message = "Invalid length for cvv response")
    private String cvvResponse;

    private LocalDateTime dateCreated;

    private LocalDateTime dateUpdated;

    private Boolean isChargedBack;

    @Size(max = 255, message = "Invalid length for merchant")
    private String merchant;

    @Size(max = 255, message = "Invalid length for merchant descriptor")
    private String merchantDescriptor;

    private long merchantId;

    @Size(max = 255, message = "Invalid length for merchant txn id")
    private String merchantTxnId;

    @Size(max = 255, message = "Invalid length for mid number")
    private String midNumber;

    @Size(max = 255, message = "Invalid length for order agent name")
    private String orderAgentName;

    @Size(max = 255, message = "Invalid length for order id")
    private String orderId;

    @Size(max = 255, message = "Invalid length for order type")
    private String orderType;

    @Size(max = 255, message = "Invalid length for parent txn id")
    private String parentTxnId;

    @Size(max = 255, message = "Invalid length for pay source")
    private String paySource;

    @Size(max = 255, message = "Invalid length for pay source id")
    private String paySourceId;

    private Integer recycleNumber;

    @Size(max = 255, message = "Invalid length for refund reason")
    private String refundReason;

    @Size(max = 255, message = "Invalid length for response text")
    private String responseText;

    @Size(max = 255, message = "Invalid length for response type")
    private String responseType;

    @Size(max = 255, message = "Invalid length for source value 1")
    @Column(name = "source_value_1")
    private String sourceValueOne;

    @Size(max = 255, message = "Invalid length for source value 2")
    @Column(name = "source_value_2")
    private String sourceValueTwo;

    @Size(max = 255, message = "Invalid length for source value 3")
    @Column(name = "source_value_3")
    private String sourceValueThree;

    @Size(max = 255, message = "Invalid length for source value 4")
    @Column(name = "source_value_4")
    private String sourceValueFour;

    @Size(max = 255, message = "Invalid length for source value 5")
    @Column(name = "source_value_5")
    private String sourceValueFive;

    private Double surcharge;

    private Double totalAmount;

    @Size(max = 255, message = "Invalid length for txn type")
    private String txnType;

    @Override
    public TransactionResponseDto toResponseDto() {
        return TransactionResponseDto.builder()
                .achAccountNumber(this.achAccountNumber)
                .achBankName(this.achBankName)
                .achRoutingNumber(this.achRoutingNumber)
                .avsResponse(this.avsResponse)
                .campaignCategoryName(this.campaignCategoryName)
                .campaignName(this.campaignName)
                .cardBin(this.cardBin)
                .cardType(this.cardType)
                .chargeBackAmount(this.chargeBackAmount)
                .chargeBackDate(this.chargeBackDate)
                .chargeBackNote(this.chargeBackNote)
                .chargeBackReasonCode(this.chargeBackReasonCode)
                .currencySymbol(this.currencySymbol)
                .cvvResponse(this.cvvResponse)
                .dateCreated(this.dateCreated)
                .dateUpdated(this.dateUpdated)
                .isChargedBack(this.isChargedBack)
                .merchant(this.merchant)
                .build();
    }
}
