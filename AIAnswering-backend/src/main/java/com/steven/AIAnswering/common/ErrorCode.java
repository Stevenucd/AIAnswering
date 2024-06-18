package com.steven.AIAnswering.common;

/**
 * 自定义错误码
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
public enum ErrorCode {

    SUCCESS(0, "ok"),
    PARAMS_ERROR(40000, "Request parameter error"),
    NOT_LOGIN_ERROR(40100, "not logged in"),
    NO_AUTH_ERROR(40101, "no authority"),
    NOT_FOUND_ERROR(40400, "Requested data does not exist"),
    FORBIDDEN_ERROR(40300, "prohibit access"),
    SYSTEM_ERROR(50000, "Internal system anomalies"),
    OPERATION_ERROR(50001, "failure of operation");

    /**
     * 状态码
     */
    private final int code;

    /**
     * 信息
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
