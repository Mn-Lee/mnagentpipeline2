package com.pedmn.mnagentpipeline2.controller.user;

import com.pedmn.mnagentpipeline2.biz.service.impl.user.MnAgentPipelineUserManager;
import com.pedmn.mnagentpipeline2.common.service.facade.user.request.MnAgentPipelineUserLoginRequest;
import com.pedmn.mnagentpipeline2.common.service.facade.user.request.MnAgentPipelineUserLogoutRequest;
import com.pedmn.mnagentpipeline2.common.service.facade.user.request.MnAgentPipelineUserRegisterRequest;
import com.pedmn.mnagentpipeline2.common.service.facade.user.request.MnAgentPipelineUserSessionRequest;
import com.pedmn.mnagentpipeline2.common.service.facade.user.response.MnAgentPipelineUserLoginResponse;
import com.pedmn.mnagentpipeline2.common.service.facade.user.response.MnAgentPipelineUserLogoutResponse;
import com.pedmn.mnagentpipeline2.common.service.facade.user.response.MnAgentPipelineUserRegisterResponse;
import com.pedmn.mnagentpipeline2.common.service.facade.user.response.MnAgentPipelineUserSessionResponse;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class MnAgentPipelineUserController {

    private static final String BEARER_PREFIX = "Bearer ";

    @Resource
    private MnAgentPipelineUserManager mnAgentPipelineUserManager;

    @PostMapping("/register")
    public MnAgentPipelineUserRegisterResponse register(@RequestBody MnAgentPipelineUserRegisterRequest request) {
        return mnAgentPipelineUserManager.register(request);
    }

    @PostMapping("/login")
    public MnAgentPipelineUserLoginResponse login(@RequestBody MnAgentPipelineUserLoginRequest request) {
        return mnAgentPipelineUserManager.login(request);
    }

    @GetMapping("/session")
    public MnAgentPipelineUserSessionResponse getSession(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestHeader(value = "X-Login-Token", required = false) String loginToken) {
        MnAgentPipelineUserSessionRequest request = new MnAgentPipelineUserSessionRequest();
        request.setToken(resolveToken(authorization, loginToken));
        return mnAgentPipelineUserManager.getSession(request);
    }

    @PostMapping("/logout")
    public MnAgentPipelineUserLogoutResponse logout(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestHeader(value = "X-Login-Token", required = false) String loginToken,
            @RequestBody(required = false) MnAgentPipelineUserLogoutRequest request) {
        if (request == null) {
            request = new MnAgentPipelineUserLogoutRequest();
        }
        if (isBlank(request.getToken())) {
            request.setToken(resolveToken(authorization, loginToken));
        }
        return mnAgentPipelineUserManager.logout(request);
    }

    private String resolveToken(String authorization, String loginToken) {
        if (!isBlank(loginToken)) {
            return loginToken;
        }
        if (isBlank(authorization)) {
            return null;
        }
        if (authorization.startsWith(BEARER_PREFIX)) {
            return authorization.substring(BEARER_PREFIX.length());
        }
        return authorization;
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
