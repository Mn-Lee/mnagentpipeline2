package com.pedmn.mnagentpipeline2.common.service.facade.user.response;

import com.pedmn.mnagentpipeline2.common.service.facade.base.ToString;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MnAgentPipelineUserSessionResponse extends ToString {

    private boolean loggedIn;

    private MnAgentPipelineUserInfoResponse userInfo;
}
