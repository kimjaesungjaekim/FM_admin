package com.developer.fillme.constant;

public enum EException {
    SECRET_KEY_EXCEPTION("APP00", "System error please try again later"),
    USER_NOT_FOUND("APP01", "User not found"),
    USER_ALREADY_EXISTS("APP02", "User already exists"),
    PW_NOT_MATCH("APP03", "Passwords do not match"),
    INVALID_USER("APP04", "Incorrect account or password"),
    INVALID_REFRESH_TOKEN("APP05", "Refresh token has been cancelled or does not exist"),
    INVALID_TOKEN("APP06", "Refresh token invalid"),
    INVALID_ID("APP07", "Invalid id"),
    USER_NOT_EXIST("APP08", "User does not exist"),
    PHONE_NUMBER_EXISTS("APP09", "phone number already in use"),
    TOKEN_NOT_EXIST("APP010", "Invalid token please login again"),
    RECORD_FOUND("APP011", "No record found"),
    TYPE_FOUND("APP012", "No this type found"),
    WRONG_PASSWORD("APP13", "Wrong password"),
    ;

    private final String code;
    private final String message;

    EException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
