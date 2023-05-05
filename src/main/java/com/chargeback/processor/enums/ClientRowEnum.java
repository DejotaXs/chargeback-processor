package com.chargeback.processor.enums;

import com.chargeback.processor.exception.types.UnexpectedFileErrorException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

import static com.chargeback.processor.util.ConstantsUtil.GETBANK_AMOUNT_ROW;
import static com.chargeback.processor.util.ConstantsUtil.GETBANK_DATE_ROW;
import static com.chargeback.processor.util.ConstantsUtil.TRUST_PAYMENTS_AMOUNT_ROW;
import static com.chargeback.processor.util.ConstantsUtil.TRUST_PAYMENTS_DATE_ROW;

@Getter
@AllArgsConstructor
public enum ClientRowEnum {

    GETBANK(GETBANK_AMOUNT_ROW, GETBANK_DATE_ROW),
    TRUST_PAYMENTS(TRUST_PAYMENTS_AMOUNT_ROW, TRUST_PAYMENTS_DATE_ROW);

    private final int amountPosition;
    private final int datePosition;

    public static int findAmountPostitionByTableName(String tableName) {
        return getClientRowEnumByTableNameOrThrow(tableName).amountPosition;
    }

    public static int findDatePostitionByTableName(String tableName) {
        return getClientRowEnumByTableNameOrThrow(tableName).datePosition;
    }

    private static ClientRowEnum getClientRowEnumByTableNameOrThrow(String tableName) {
        if (tableName == null) {
            throw new UnexpectedFileErrorException("File/Table name is null");
        }

        return Arrays.stream(ClientRowEnum.values())
                .filter(it -> it.name().equals(tableName))
                .findFirst()
                .orElseThrow(() -> new UnexpectedFileErrorException("Parse format do not Exist"));
    }
}
