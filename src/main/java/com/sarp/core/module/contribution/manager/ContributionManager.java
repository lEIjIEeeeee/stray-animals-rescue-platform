package com.sarp.core.module.contribution.manager;

import cn.hutool.core.util.ObjectUtil;
import com.sarp.core.exception.BizException;
import com.sarp.core.module.common.enums.HttpResultCode;
import com.sarp.core.module.contribution.dao.ContributionRecordMapper;
import com.sarp.core.module.contribution.model.entity.ContributionRecord;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @date 2024/3/31 1:47
 */

@Component
@AllArgsConstructor
public class ContributionManager {

    private ContributionRecordMapper contributionRecordMapper;

    public ContributionRecord getByIdWithExp(String id) {
        ContributionRecord contributionRecord = contributionRecordMapper.selectById(id);
        if (ObjectUtil.isNull(contributionRecord)) {
            throw new BizException(HttpResultCode.DATA_NOT_EXISTED);
        }
        return contributionRecord;
    }

}
