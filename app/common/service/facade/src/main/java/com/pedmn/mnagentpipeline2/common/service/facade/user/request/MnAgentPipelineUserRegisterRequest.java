package com.pedmn.mnagentpipeline2.common.service.facade.user.request;

import com.pedmn.mnagentpipeline2.common.service.facade.base.ToString;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MnAgentPipelineUserRegisterRequest extends ToString {

    private String email;

    private String password;

    private String nickName;

    private String bizData;
}
