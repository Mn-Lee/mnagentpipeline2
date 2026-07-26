package com.pedmn.mnagentpipeline2.common.service.facade.base;

import lombok.Getter;

@Getter
public enum MnAgentPipelineErrorCode {

    // ============================= 系统错误【001-199】==================================

    /**
     * 系统异常
     */
    SYSTEM_ERROR(ErrorTypes.SYSTEM, "001", "系统异常"),

    /**
     * 系统集成异常
     */
    INTEGRATION_ERROR(ErrorTypes.SYSTEM, "002", "系统集成异常"),

    /**
     * DB异常
     */
    DB_ERROR(ErrorTypes.SYSTEM, "003", "DB异常"),

    // ============================= 义务错误【201-999】==================================

    /**
     * 参数错误
     */
    ILLEGAL_ARGUMENT(ErrorTypes.BIZ, "201", "参数错误"),

    /**
     * 用户email已存在
     */
    USER_EMAIL_EXIST(ErrorTypes.BIZ, "202", "用户email已存在"),

    /**
     * 用户不存在
     */
    USER_NOT_EXIST(ErrorTypes.BIZ, "203", "用户不存在"),

    ;

    private static final String CODE_PREFIX = "AP1";

    private final String errorType;

    private final String errorCode;

    private final String errorMsg;

    private final String code;

    MnAgentPipelineErrorCode(String errorType, String errorCode, String errorMsg) {
        this.errorType = errorType;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.code = generateCode(errorType, errorCode);
    }

    public String generateCode(String errorType, String errorCode) {
        return CODE_PREFIX + errorType + errorCode;
    }

    public static final class ErrorTypes {

        public static final String SYSTEM = "0";

        public static final String BIZ = "1";

        private ErrorTypes() {
        }
    }
}
