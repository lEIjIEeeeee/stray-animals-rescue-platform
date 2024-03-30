package com.sarp.core.module.adopt.manager;

import cn.hutool.core.util.ObjectUtil;
import com.sarp.core.exception.BizException;
import com.sarp.core.module.adopt.dao.AdoptRecordMapper;
import com.sarp.core.module.adopt.model.entity.AdoptRecord;
import com.sarp.core.module.common.enums.HttpResultCode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @date 2024/3/30 4:35
 */

@Component
@AllArgsConstructor
public class AdoptRecordManager {

    private AdoptRecordMapper adoptRecordMapper;

    public AdoptRecord getByIdWithExp(String id) {
        AdoptRecord adoptRecord = adoptRecordMapper.selectById(id);
        if (ObjectUtil.isNull(adoptRecord)) {
            throw new BizException(HttpResultCode.DATA_NOT_EXISTED);
        }
        return adoptRecord;
    }
}
