package com.chargeback.processor.util;

import com.chargeback.processor.exception.types.DateParseErrorException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static com.chargeback.processor.util.ConstantsUtil.DOUBLE_UNDERSCORE;
import static com.chargeback.processor.util.ConstantsUtil.EMPTY_CHAR;
import static com.chargeback.processor.util.ConstantsUtil.GENERIC_TIME_FORMAT;
import static com.chargeback.processor.util.ConstantsUtil.GETBANK_DATE_FORMAT;
import static com.chargeback.processor.util.ConstantsUtil.GETBANK_TABLE_NAME;
import static com.chargeback.processor.util.ConstantsUtil.GETBANK_TIME_FORMAT;
import static com.chargeback.processor.util.ConstantsUtil.NULL_DATE;
import static com.chargeback.processor.util.ConstantsUtil.NULL_ROW;
import static com.chargeback.processor.util.ConstantsUtil.REGEX_SNAKE_CASE;
import static com.chargeback.processor.util.ConstantsUtil.REGEX_SPECIAL_CHARS;
import static com.chargeback.processor.util.ConstantsUtil.REPLACEMENT_SNAKE_CASE;
import static com.chargeback.processor.util.ConstantsUtil.TRANSACTION_DATE_FORMAT;
import static com.chargeback.processor.util.ConstantsUtil.TRANSACTION_TABLE_NAME;
import static com.chargeback.processor.util.ConstantsUtil.TRUST_PAYMENTS_DATE_FORMAT;
import static com.chargeback.processor.util.ConstantsUtil.TRUST_PAYMENTS_TABLE_NAME;
import static com.chargeback.processor.util.ConstantsUtil.TRUST_PAYMENTS_TIME_FORMAT;
import static com.chargeback.processor.util.ConstantsUtil.UNDERSCORE;

public class ParseUtils {
    public static Double parseDoubleOrNull(final String input) {
        if (isNull(input)) {
            return null;
        }

        return Double.parseDouble(input);
    }

    public static Integer parseIntegerOrNull(final String input) {
        if (isNull(input)) {
            return null;
        }

        return Integer.valueOf(input);
    }

    private static boolean isNull(final String input) {
        if (input == null) {
            return true;
        }

        return input.equalsIgnoreCase(NULL_ROW);
    }

    public static String camelToSnakeUpper(final String inputStr) {
        return removeSpecialChars(inputStr)
                .replaceAll(REGEX_SNAKE_CASE, REPLACEMENT_SNAKE_CASE)
                .toUpperCase();
    }

    public static String spaceToSnakeUpper(final String inputStr) {
        return removeSpecialChars(inputStr).trim()
                .replace(EMPTY_CHAR, UNDERSCORE)
                .replace(DOUBLE_UNDERSCORE, UNDERSCORE)
                .toUpperCase();
    }

    public static LocalDateTime parseDateByTable(final String tableName, final String date) {
        return parseDateByTable(tableName, date, Optional.empty());
    }

    public static LocalDateTime parseDateByTable(final String tableName,
                                                 final String date,
                                                 Optional<String> dateFormat) {
        if (date == null) {
            return NULL_DATE;
        }

        if (date.equalsIgnoreCase(NULL_ROW)) {
            return NULL_DATE;
        }

        return switch (tableName) {
            case TRANSACTION_TABLE_NAME -> LocalDateTime.parse(date, TRANSACTION_DATE_FORMAT);

            case GETBANK_TABLE_NAME ->
                    LocalDateTime.of(LocalDate.parse(date, GETBANK_DATE_FORMAT), GETBANK_TIME_FORMAT);

            case TRUST_PAYMENTS_TABLE_NAME ->
                    LocalDateTime.of(LocalDate.parse(date, TRUST_PAYMENTS_DATE_FORMAT), TRUST_PAYMENTS_TIME_FORMAT);

            default -> processRandomDataOrThrow(dateFormat, date);

        };
    }

    private static String removeSpecialChars(final String str) {
        return str.replaceAll(REGEX_SPECIAL_CHARS, EMPTY_CHAR);
    }

    private static LocalDateTime processRandomDataOrThrow(Optional<String> dateFormat, String date) {

        String formatStr = dateFormat.orElseThrow(() ->
                new DateParseErrorException("Undefined type of database parse"));

        return LocalDateTime.of(LocalDate.parse(date, DateTimeFormatter.ofPattern(formatStr)), GENERIC_TIME_FORMAT);
    }
}
