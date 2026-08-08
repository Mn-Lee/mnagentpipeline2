package com.pedmn.mnagentpipeline2.common.service.facade.user.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MnAgentPipelineUserLoginResponse {

    private String token;

    private MnAgentPipelineUserInfoResponse userInfo;
}
