package com.sarp.core.module.notice.manager;

import cn.hutool.core.util.ObjectUtil;
import com.sarp.core.exception.BizException;
import com.sarp.core.module.common.enums.HttpResultCode;
import com.sarp.core.module.notice.dao.NoticeArticleMapper;
import com.sarp.core.module.notice.model.entity.NoticeArticle;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @date 2024/4/21 4:50
 */

@Component
@AllArgsConstructor
public class NoticeArticleManager {

    private NoticeArticleMapper noticeArticleMapper;

    public NoticeArticle getById(String id) {
        return noticeArticleMapper.selectById(id);
    }

    public NoticeArticle getByIdWithExp(String id) {
        NoticeArticle noticeArticle = noticeArticleMapper.selectById(id);
        if (ObjectUtil.isNull(noticeArticle)) {
            throw new BizException(HttpResultCode.DATA_NOT_EXISTED);
        }
        return noticeArticle;
    }

}
