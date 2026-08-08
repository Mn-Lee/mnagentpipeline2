package com.pedmn.mnagentpipeline2.biz.service.impl.user;

import com.pedmn.mnagentpipeline2.common.service.facade.user.request.MnAgentPipelineUserLoginRequest;
import com.pedmn.mnagentpipeline2.common.service.facade.user.request.MnAgentPipelineUserLogoutRequest;
import com.pedmn.mnagentpipeline2.common.service.facade.user.request.MnAgentPipelineUserRegisterRequest;
import com.pedmn.mnagentpipeline2.common.service.facade.user.request.MnAgentPipelineUserSessionRequest;
import com.pedmn.mnagentpipeline2.common.service.facade.user.response.MnAgentPipelineUserLoginResponse;
import com.pedmn.mnagentpipeline2.common.service.facade.user.response.MnAgentPipelineUserLogoutResponse;
import com.pedmn.mnagentpipeline2.common.service.facade.user.response.MnAgentPipelineUserRegisterResponse;
import com.pedmn.mnagentpipeline2.common.service.facade.user.response.MnAgentPipelineUserSessionResponse;

public interface MnAgentPipelineUserManager {

    MnAgentPipelineUserRegisterResponse register(MnAgentPipelineUserRegisterRequest request);

    MnAgentPipelineUserLoginResponse login(MnAgentPipelineUserLoginRequest request);

    MnAgentPipelineUserSessionResponse getSession(MnAgentPipelineUserSessionRequest request);

    MnAgentPipelineUserLogoutResponse logout(MnAgentPipelineUserLogoutRequest request);
}
