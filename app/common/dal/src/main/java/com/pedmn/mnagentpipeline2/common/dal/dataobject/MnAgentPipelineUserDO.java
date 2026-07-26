package com.pedmn.mnagentpipeline2.common.dal.dataobject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MnAgentPipelineUserDO extends MnAgentBaseDO {

    private String userId;

    private String nickName;

    private String password;

    private String email;

    private String bizData;
}
