package com.pedmn.mnagentpipeline2.common.service.facade.base;

import lombok.Getter;

@Getter
public enum MnAgentPipelineErrorCode {

    SYSTEM_ERROR(ErrorTypes.SYSTEM, "001", "System error"),

    INTEGRATION_ERROR(ErrorTypes.SYSTEM, "002", "Integration error"),

    DB_ERROR(ErrorTypes.SYSTEM, "003", "Database error"),

    ILLEGAL_ARGUMENT(ErrorTypes.BIZ, "201", "Illegal argument"),

    USER_EMAIL_EXIST(ErrorTypes.BIZ, "202", "User email already exists"),

    USER_NOT_EXIST(ErrorTypes.BIZ, "203", "User does not exist"),

    USER_PASSWORD_ERROR(ErrorTypes.BIZ, "204", "User password is incorrect"),

    USER_NOT_LOGIN(ErrorTypes.BIZ, "205", "User is not logged in");

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
