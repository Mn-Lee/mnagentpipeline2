package com.pedmn.mnagentpipeline2.common.dal.enums;

import lombok.Getter;

@Getter
public enum MnAgentTableEnum {

    MNAGENTPIPELINE_USER("mnagentpipeline_user", "用户表");

    private final String name;

    private final String desc;

    MnAgentTableEnum(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }
}
