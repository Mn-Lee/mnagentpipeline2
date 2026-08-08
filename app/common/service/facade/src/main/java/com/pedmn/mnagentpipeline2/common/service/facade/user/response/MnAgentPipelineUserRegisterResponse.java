package com.pedmn.mnagentpipeline2.common.service.facade.user.response;

import com.pedmn.mnagentpipeline2.common.service.facade.base.ToString;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MnAgentPipelineUserRegisterResponse extends ToString {

    private String token;

    private MnAgentPipelineUserInfoResponse userInfo;
}
