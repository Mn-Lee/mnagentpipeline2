package com.pedmn.mnagentpipeline2.common.service.util;

import com.pedmn.mnagentpipeline2.common.service.facade.base.MnAgentPipelineErrorCode;
import com.pedmn.mnagentpipeline2.common.service.facade.base.MnAgentPipelineException;

public final class MnAgentPipelineAssertUtil {

    private MnAgentPipelineAssertUtil() {
    }

    /**
     * 断言表达式的值为 true，否则抛出指定错误信息。
     */
    public static void isTrue(final boolean expValue, MnAgentPipelineErrorCode resultCode, final Object... objs) {
        if (!expValue) {
            resultCode = resultCode != null ? resultCode : MnAgentPipelineErrorCode.SYSTEM_ERROR;
            String logString = getLogString(objs);
            String resultMsg = isBlank(logString) ? resultCode.getErrorMsg() : logString;
            throw new MnAgentPipelineException(resultCode, resultMsg);
        }
    }

    /**
     * 断言表达式的值为 true，否则抛出指定错误信息。
     */
    public static void isTrue(boolean retry, final boolean expValue, MnAgentPipelineErrorCode resultCode,
                              final Object... objs) {
        if (!expValue) {
            resultCode = resultCode != null ? resultCode : MnAgentPipelineErrorCode.SYSTEM_ERROR;
            String logString = getLogString(objs);
            String resultMsg = isBlank(logString) ? resultCode.getErrorMsg() : logString;
            throw new MnAgentPipelineException(resultCode, resultMsg, retry);
        }
    }

    /**
     * 断言表达式的值为 false，否则抛出指定错误信息。
     */
    public static void isFalse(final boolean expValue, final MnAgentPipelineErrorCode resultCode,
                               final Object... objs) {
        isTrue(!expValue, resultCode, objs);
    }

    /**
     * 断言两个对象相等，否则抛出指定错误信息。
     */
    public static void equals(final Object obj1, final Object obj2, final MnAgentPipelineErrorCode resultCode,
                              final Object... objs) {
        isTrue(obj1 == null ? obj2 == null : obj1.equals(obj2), resultCode, objs);
    }

    /**
     * 断言两个对象不等，否则抛出指定错误信息。
     */
    public static void notEquals(final Object obj1, final Object obj2, final MnAgentPipelineErrorCode resultCode,
                                 final Object... objs) {
        isTrue(obj1 == null ? obj2 != null : !obj1.equals(obj2), resultCode, objs);
    }

    /**
     * 断言字符串为空，否则抛出指定错误信息。
     */
    public static void blank(final String str, final MnAgentPipelineErrorCode resultCode, final Object... objs) {
        isTrue(isBlank(str), resultCode, objs);
    }

    /**
     * 断言字符串非空，否则抛出指定错误信息。
     */
    public static void notBlank(final String str, final MnAgentPipelineErrorCode resultCode, final Object... objs) {
        isTrue(!isBlank(str), resultCode, objs);
    }

    /**
     * 断言对象为 null，否则抛出指定错误信息。
     */
    public static void isNull(final Object object, final MnAgentPipelineErrorCode resultCode, final Object... objs) {
        isTrue(object == null, resultCode, objs);
    }

    /**
     * 断言对象非 null，否则抛出指定错误信息。
     */
    public static void notNull(final Object object, final MnAgentPipelineErrorCode resultCode, final Object... objs) {
        isTrue(object != null, resultCode, objs);
    }

    private static String getLogString(final Object... objs) {
        if (objs == null || objs.length == 0) {
            return "";
        }

        StringBuilder logString = new StringBuilder();
        for (Object obj : objs) {
            if (obj != null) {
                logString.append(obj);
            }
        }
        return logString.toString();
    }

    private static boolean isBlank(final String str) {
        return str == null || str.trim().isEmpty();
    }
}
