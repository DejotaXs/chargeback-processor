package com.chargeback.processor.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ConstantsUtil {
    private static final String TRANSACTION_SAMPLE_DIR = "src/main/resources/";
    private static final String TRANSACTION_SAMPLE_NAME = "transaction_sample_data";
    public static final String CSV_EXTENSION = ".csv";
    public static final String TRANSACTION_SAMPLE_PATH = TRANSACTION_SAMPLE_DIR + TRANSACTION_SAMPLE_NAME + CSV_EXTENSION;
    public static final char SEMICOLON = ';';
    public static final int FIRST_LINE = 1;
    public static final int HEADER_LINE = 0;
    public static final int GETBANK_DATE_ROW = 8;
    public static final int GETBANK_AMOUNT_ROW = 12;
    public static final int TRUST_PAYMENTS_DATE_ROW = 31;
    public static final int TRUST_PAYMENTS_AMOUNT_ROW = 15;
    public static final String TRANSACTION_TABLE_NAME = "TRANSACTION";
    public static final String GETBANK_TABLE_NAME = "GETBANK";
    public static final String TRUST_PAYMENTS_TABLE_NAME = "TRUST_PAYMENTS";
    public static final String NULL_ROW = "NULL";
    public static final String REGEX_SPECIAL_CHARS = "[^a-zA-Z0-9]";
    public static final String REGEX_SNAKE_CASE = "([a-z])([A-Z]+)";
    public static final String REPLACEMENT_SNAKE_CASE = "$1_$2";
    public static final String EMPTY_CHAR = " ";
    public static final String UNDERSCORE = "_";
    public static final String DOUBLE_UNDERSCORE = "__";
    public static final char SEPARATOR = SEMICOLON;
    public static final DateTimeFormatter TRANSACTION_DATE_FORMAT = DateTimeFormatter.ofPattern("M/d/yyyy H:mm");
    public static final DateTimeFormatter GETBANK_DATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    public static final DateTimeFormatter TRUST_PAYMENTS_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static final LocalTime GENERIC_TIME_FORMAT = LocalTime.of(0, 0);
    public static final LocalTime GETBANK_TIME_FORMAT = GENERIC_TIME_FORMAT;
    public static final LocalTime TRUST_PAYMENTS_TIME_FORMAT = GENERIC_TIME_FORMAT;

    public static final LocalDateTime NULL_DATE = LocalDateTime.of(1900, 1, 1, 0, 0);
}
