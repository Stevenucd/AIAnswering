package com.steven.AIAnswering.exception;

import com.steven.AIAnswering.common.ErrorCode;

/**
 * Custom Exception Class
 *
 */
public class BusinessException extends RuntimeException {

    /**
     * Error code
     */
    private final int code;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }

    public int getCode() {
        return code;
    }
}
