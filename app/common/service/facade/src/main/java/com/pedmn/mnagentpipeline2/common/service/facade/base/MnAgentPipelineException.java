package com.pedmn.mnagentpipeline2.common.service.facade.base;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MnAgentPipelineException extends RuntimeException {

    private static final long serialVersionUID = 1145302714797897106L;

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 错误枚举
     */
    private MnAgentPipelineErrorCode errorCodeEnum;

    /**
     * 是否重试
     */
    private boolean retry = true;

    public MnAgentPipelineException(MnAgentPipelineErrorCode errorCodeEnum) {
        super(errorCodeEnum.getErrorMsg());
        this.errorCodeEnum = errorCodeEnum;
        this.errorCode = errorCodeEnum.getCode();
        this.errorMessage = errorCodeEnum.getErrorMsg();
    }

    public MnAgentPipelineException(MnAgentPipelineErrorCode errorCodeEnum, String errorMessage) {
        super(errorMessage);
        this.errorCodeEnum = errorCodeEnum;
        this.errorCode = errorCodeEnum.getCode();
        this.errorMessage = errorMessage;
    }

    public MnAgentPipelineException(Throwable cause, MnAgentPipelineErrorCode errorCodeEnum) {
        super(errorCodeEnum.getErrorMsg(), cause);
        this.errorCodeEnum = errorCodeEnum;
        this.errorCode = errorCodeEnum.getCode();
        this.errorMessage = errorCodeEnum.getErrorMsg();
    }

    public MnAgentPipelineException(Throwable cause, MnAgentPipelineErrorCode errorCodeEnum, boolean retry) {
        super(errorCodeEnum.getErrorMsg(), cause);
        this.errorCodeEnum = errorCodeEnum;
        this.errorCode = errorCodeEnum.getCode();
        this.errorMessage = errorCodeEnum.getErrorMsg();
        this.retry = retry;
    }

    public MnAgentPipelineException(MnAgentPipelineErrorCode errorCodeEnum, String errorMessage, boolean retry) {
        super(errorMessage);
        this.errorCodeEnum = errorCodeEnum;
        this.errorCode = errorCodeEnum.getCode();
        this.errorMessage = errorMessage;
        this.retry = retry;
    }

    public MnAgentPipelineException(MnAgentPipelineErrorCode errorCodeEnum, boolean retry) {
        super(errorCodeEnum.getErrorMsg());
        this.errorCodeEnum = errorCodeEnum;
        this.errorCode = errorCodeEnum.getCode();
        this.errorMessage = errorCodeEnum.getErrorMsg();
        this.retry = retry;
    }
}
