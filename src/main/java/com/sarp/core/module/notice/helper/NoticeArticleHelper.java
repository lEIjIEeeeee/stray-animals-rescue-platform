package com.sarp.core.module.notice.helper;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.sarp.core.module.notice.manager.NoticeTypeManager;
import com.sarp.core.module.notice.model.entity.NoticeType;
import com.sarp.core.module.notice.model.response.PlatformNoticeArticleResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @date 2024/4/21 4:39
 */

@Component
@AllArgsConstructor
public class NoticeArticleHelper {

    private NoticeTypeManager noticeTypeManager;

    public void fillNoticeArticleListData(List<PlatformNoticeArticleResponse> dataList) {
        if (CollUtil.isEmpty(dataList)) {
            return;
        }
        Set<String> noticeTypeIds = dataList.stream()
                                            .map(PlatformNoticeArticleResponse::getNoticeTypeId)
                                            .collect(Collectors.toSet());
        Map<String, NoticeType> noticeTypeMap = noticeTypeManager.getNoticeTypeMap(noticeTypeIds);

        for (PlatformNoticeArticleResponse response : dataList) {
            NoticeType noticeType = noticeTypeMap.get(response.getNoticeTypeId());
            if (ObjectUtil.isNotNull(noticeType)) {
                response.setNoticeTypeName(noticeType.getName());
            }
        }
    }

}
