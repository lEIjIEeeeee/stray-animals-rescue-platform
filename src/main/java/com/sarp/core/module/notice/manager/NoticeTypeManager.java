package com.sarp.core.module.notice.manager;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.sarp.core.exception.BizException;
import com.sarp.core.module.common.enums.HttpResultCode;
import com.sarp.core.module.notice.dao.NoticeTypeMapper;
import com.sarp.core.module.notice.model.entity.NoticeType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @date 2024/4/20 23:02
 */

@Component
@AllArgsConstructor
public class NoticeTypeManager {

    private NoticeTypeMapper noticeTypeMapper;

    public NoticeType getById(String id) {
        return noticeTypeMapper.selectById(id);
    }

    public NoticeType getByIdWithExp(String id) {
        NoticeType noticeType = noticeTypeMapper.selectById(id);
        if (ObjectUtil.isNull(noticeType)) {
            throw new BizException(HttpResultCode.DATA_NOT_EXISTED);
        }
        return noticeType;
    }

    public Map<String, NoticeType> getNoticeTypeMap(Collection<String> noticeTypeIds) {
        if (CollUtil.isEmpty(noticeTypeIds)) {
            return Collections.emptyMap();
        }
        List<NoticeType> noticeTypeList = noticeTypeMapper.selectBatchIds(noticeTypeIds);
        if (CollUtil.isEmpty(noticeTypeList)) {
            return Collections.emptyMap();
        }
        return noticeTypeList.stream()
                             .collect(Collectors.toMap(NoticeType::getId, noticeType -> noticeType));
    }

}
