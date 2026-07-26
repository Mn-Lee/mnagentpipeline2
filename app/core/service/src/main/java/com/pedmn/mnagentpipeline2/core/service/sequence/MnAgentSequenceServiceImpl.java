package com.pedmn.mnagentpipeline2.core.service.sequence;

import com.pedmn.mnagentpipeline2.common.dal.dao.MnAgentSequenceDao;
import com.pedmn.mnagentpipeline2.common.dal.dataobject.MnAgentSequenceDO;
import com.pedmn.mnagentpipeline2.common.dal.enums.MnAgentTableEnum;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class MnAgentSequenceServiceImpl implements MnAgentSequenceService {

    private static final long INIT_SEQUENCE_VALUE = 10000000L;

    private static final int SEQUENCE_SUFFIX_LENGTH = 8;

    @Resource
    private MnAgentSequenceDao mnAgentSequenceDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String generateSingleSequenceId(MnAgentTableEnum tableEnum) {
        MnAgentSequenceDO sequence = mnAgentSequenceDao.lockBySequenceName(tableEnum.getName());
        Long sequenceValue;
        if (sequence == null) {
            sequenceValue = INIT_SEQUENCE_VALUE;
            MnAgentSequenceDO newSequence = new MnAgentSequenceDO();
            newSequence.setSequenceName(tableEnum.getName());
            newSequence.setCurrentValue(sequenceValue);
            mnAgentSequenceDao.insert(newSequence);
        } else {
            sequenceValue = sequence.getCurrentValue() + 1;
            mnAgentSequenceDao.updateCurrentValue(tableEnum.getName(), sequenceValue);
        }

        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        return df.format(new Date()) + getLastEightDigits(sequenceValue);
    }

    private String getLastEightDigits(Long sequenceValue) {
        String sequenceValueString = sequenceValue.toString();
        if (sequenceValueString.length() <= SEQUENCE_SUFFIX_LENGTH) {
            return sequenceValueString;
        }
        return sequenceValueString.substring(sequenceValueString.length() - SEQUENCE_SUFFIX_LENGTH);
    }
}
