package com.steven.AIAnswering.common;

/**
 * Custom Error Codes
 *
 */
public enum ErrorCode {

    SUCCESS(0, "ok"),
    PARAMS_ERROR(40000, "Request parameter error"),
    NOT_LOGIN_ERROR(40100, "Not logged in"),
    NO_AUTH_ERROR(40101, "No authority"),
    NOT_FOUND_ERROR(40400, "Requested data does not exist"),
    FORBIDDEN_ERROR(40300, "Prohibit access"),
    SYSTEM_ERROR(50000, "Internal system exceptions"),
    OPERATION_ERROR(50001, "Operation failed");

    /**
     * Status code
     */
    private final int code;

    /**
     * Message
     */
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
