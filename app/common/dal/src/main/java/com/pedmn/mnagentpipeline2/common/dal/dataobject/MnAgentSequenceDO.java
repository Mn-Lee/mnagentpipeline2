package com.pedmn.mnagentpipeline2.common.dal.dataobject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MnAgentSequenceDO extends MnAgentBaseDO {

    private String sequenceName;

    private Long currentValue;
}
