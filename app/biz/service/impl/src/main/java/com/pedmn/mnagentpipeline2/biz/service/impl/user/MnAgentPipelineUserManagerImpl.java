package com.pedmn.mnagentpipeline2.biz.service.impl.user;

import com.pedmn.mnagentpipeline2.common.service.facade.base.MnAgentPipelineErrorCode;
import com.pedmn.mnagentpipeline2.common.service.facade.user.request.MnAgentPipelineUserLoginRequest;
import com.pedmn.mnagentpipeline2.common.service.facade.user.request.MnAgentPipelineUserLogoutRequest;
import com.pedmn.mnagentpipeline2.common.service.facade.user.request.MnAgentPipelineUserRegisterRequest;
import com.pedmn.mnagentpipeline2.common.service.facade.user.request.MnAgentPipelineUserSessionRequest;
import com.pedmn.mnagentpipeline2.common.service.facade.user.response.MnAgentPipelineUserInfoResponse;
import com.pedmn.mnagentpipeline2.common.service.facade.user.response.MnAgentPipelineUserLoginResponse;
import com.pedmn.mnagentpipeline2.common.service.facade.user.response.MnAgentPipelineUserLogoutResponse;
import com.pedmn.mnagentpipeline2.common.service.facade.user.response.MnAgentPipelineUserRegisterResponse;
import com.pedmn.mnagentpipeline2.common.service.facade.user.response.MnAgentPipelineUserSessionResponse;
import com.pedmn.mnagentpipeline2.common.service.util.MnAgentPipelineAssertUtil;
import com.pedmn.mnagentpipeline2.core.model.domain.MnAgentPipelineUser;
import com.pedmn.mnagentpipeline2.core.service.repository.MnAgentPipelineUserRepository;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.UUID;

@Service
public class MnAgentPipelineUserManagerImpl implements MnAgentPipelineUserManager {

    private static final String LOGIN_TOKEN_KEY_PREFIX = "mnagentpipeline:login:token:";

    private static final Duration LOGIN_TOKEN_TTL = Duration.ofDays(3);

    @Resource
    private MnAgentPipelineUserRepository mnAgentPipelineUserRepository;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public MnAgentPipelineUserRegisterResponse register(MnAgentPipelineUserRegisterRequest request) {
        checkRegisterRequest(request);

        MnAgentPipelineUser user = new MnAgentPipelineUser();
        user.setEmail(request.getEmail());
        user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        user.setNickName(request.getNickName());
        user.setBizData(request.getBizData());

        MnAgentPipelineUser insertedUser = mnAgentPipelineUserRepository.insert(user);
        String token = saveLoginToken(insertedUser.getUserId());

        MnAgentPipelineUserRegisterResponse response = new MnAgentPipelineUserRegisterResponse();
        response.setToken(token);
        response.setUserInfo(toUserInfoResponse(insertedUser));
        return response;
    }

    @Override
    public MnAgentPipelineUserLoginResponse login(MnAgentPipelineUserLoginRequest request) {
        checkLoginRequest(request);

        MnAgentPipelineUser user = mnAgentPipelineUserRepository.findByEmail(request.getEmail());
        MnAgentPipelineAssertUtil.notNull(user, MnAgentPipelineErrorCode.USER_NOT_EXIST);
        MnAgentPipelineAssertUtil.isTrue(BCrypt.checkpw(request.getPassword(), user.getPassword()),
                MnAgentPipelineErrorCode.USER_PASSWORD_ERROR);

        String token = saveLoginToken(user.getUserId());

        MnAgentPipelineUserLoginResponse response = new MnAgentPipelineUserLoginResponse();
        response.setToken(token);
        response.setUserInfo(toUserInfoResponse(user));
        return response;
    }

    @Override
    public MnAgentPipelineUserSessionResponse getSession(MnAgentPipelineUserSessionRequest request) {
        MnAgentPipelineUserSessionResponse response = new MnAgentPipelineUserSessionResponse();
        if (request == null || isBlank(request.getToken())) {
            response.setLoggedIn(false);
            return response;
        }

        String userId = stringRedisTemplate.opsForValue().get(buildLoginTokenKey(request.getToken()));
        if (isBlank(userId)) {
            response.setLoggedIn(false);
            return response;
        }

        MnAgentPipelineUser user = mnAgentPipelineUserRepository.findByUserId(userId);
        if (user == null) {
            stringRedisTemplate.delete(buildLoginTokenKey(request.getToken()));
            response.setLoggedIn(false);
            return response;
        }

        stringRedisTemplate.expire(buildLoginTokenKey(request.getToken()), LOGIN_TOKEN_TTL);
        response.setLoggedIn(true);
        response.setUserInfo(toUserInfoResponse(user));
        return response;
    }

    @Override
    public MnAgentPipelineUserLogoutResponse logout(MnAgentPipelineUserLogoutRequest request) {
        if (request != null && !isBlank(request.getToken())) {
            stringRedisTemplate.delete(buildLoginTokenKey(request.getToken()));
        }

        MnAgentPipelineUserLogoutResponse response = new MnAgentPipelineUserLogoutResponse();
        response.setSuccess(true);
        return response;
    }

    private void checkRegisterRequest(MnAgentPipelineUserRegisterRequest request) {
        MnAgentPipelineAssertUtil.notNull(request, MnAgentPipelineErrorCode.ILLEGAL_ARGUMENT);
        MnAgentPipelineAssertUtil.notBlank(request.getEmail(), MnAgentPipelineErrorCode.ILLEGAL_ARGUMENT);
        MnAgentPipelineAssertUtil.notBlank(request.getPassword(), MnAgentPipelineErrorCode.ILLEGAL_ARGUMENT);
    }

    private void checkLoginRequest(MnAgentPipelineUserLoginRequest request) {
        MnAgentPipelineAssertUtil.notNull(request, MnAgentPipelineErrorCode.ILLEGAL_ARGUMENT);
        MnAgentPipelineAssertUtil.notBlank(request.getEmail(), MnAgentPipelineErrorCode.ILLEGAL_ARGUMENT);
        MnAgentPipelineAssertUtil.notBlank(request.getPassword(), MnAgentPipelineErrorCode.ILLEGAL_ARGUMENT);
    }

    private String saveLoginToken(String userId) {
        String token = UUID.randomUUID().toString().replace("-", "");
        stringRedisTemplate.opsForValue().set(buildLoginTokenKey(token), userId, LOGIN_TOKEN_TTL);
        return token;
    }

    private String buildLoginTokenKey(String token) {
        return LOGIN_TOKEN_KEY_PREFIX + token;
    }

    private MnAgentPipelineUserInfoResponse toUserInfoResponse(MnAgentPipelineUser user) {
        MnAgentPipelineUserInfoResponse response = new MnAgentPipelineUserInfoResponse();
        response.setUserId(user.getUserId());
        response.setEmail(user.getEmail());
        response.setNickName(user.getNickName());
        response.setBizData(user.getBizData());
        return response;
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
