package com.pedmn.mnagentpipeline2.core.service.repository;

import com.pedmn.mnagentpipeline2.common.dal.dao.MnAgentPipelineUserDao;
import com.pedmn.mnagentpipeline2.common.dal.dataobject.MnAgentPipelineUserDO;
import com.pedmn.mnagentpipeline2.common.dal.enums.MnAgentTableEnum;
import com.pedmn.mnagentpipeline2.common.service.facade.base.MnAgentPipelineErrorCode;
import com.pedmn.mnagentpipeline2.common.service.util.MnAgentPipelineAssertUtil;
import com.pedmn.mnagentpipeline2.core.converter.MnAgentPipelineUserConverter;
import com.pedmn.mnagentpipeline2.core.model.domain.MnAgentPipelineUser;
import com.pedmn.mnagentpipeline2.core.service.sequence.MnAgentSequenceService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MnAgentPipelineUserRepositoryImpl implements MnAgentPipelineUserRepository {

    private static final String NOT_DELETED = "N";

    @Resource
    private MnAgentPipelineUserDao mnAgentPipelineUserDao;

    @Resource
    private MnAgentSequenceService mnAgentSequenceService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MnAgentPipelineUser insert(MnAgentPipelineUser user) {
        MnAgentPipelineUserDO existDO = mnAgentPipelineUserDao.lockByEmail(user.getEmail());
        MnAgentPipelineAssertUtil.isNull(existDO, MnAgentPipelineErrorCode.USER_EMAIL_EXIST);

        String userId = mnAgentSequenceService.generateSingleSequenceId(MnAgentTableEnum.MNAGENTPIPELINE_USER);
        user.setUserId(userId);
        mnAgentPipelineUserDao.insert(MnAgentPipelineUserConverter.INSTANCE.toDO(user));
        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MnAgentPipelineUser update(MnAgentPipelineUser user) {
        MnAgentPipelineUserDO existDO = mnAgentPipelineUserDao.lockByUserId(user.getUserId());
        MnAgentPipelineAssertUtil.notNull(existDO, MnAgentPipelineErrorCode.USER_NOT_EXIST);

        mnAgentPipelineUserDao.update(MnAgentPipelineUserConverter.INSTANCE.toDO(user));
        existDO = mnAgentPipelineUserDao.lockByUserId(user.getUserId());
        return MnAgentPipelineUserConverter.INSTANCE.toModel(existDO);
    }

    @Override
    public MnAgentPipelineUser findByUserId(String userId) {
        MnAgentPipelineUserDO userDO = mnAgentPipelineUserDao.findByKey(userId, NOT_DELETED);
        return MnAgentPipelineUserConverter.INSTANCE.toModel(userDO);
    }

    @Override
    public MnAgentPipelineUser findByEmail(String email) {
        MnAgentPipelineUserDO userDO = mnAgentPipelineUserDao.findByEmail(email, NOT_DELETED);
        return MnAgentPipelineUserConverter.INSTANCE.toModel(userDO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(String userId) {
        MnAgentPipelineUserDO existDO = mnAgentPipelineUserDao.lockByUserId(userId);
        MnAgentPipelineAssertUtil.notNull(existDO, MnAgentPipelineErrorCode.USER_NOT_EXIST);

        return mnAgentPipelineUserDao.delete(userId);
    }
}
