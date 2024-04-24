package com.sarp.core.module.notice.dao;

import com.sarp.core.module.common.dao.MyBaseMapper;
import com.sarp.core.module.notice.model.entity.NoticeArticle;
import org.apache.ibatis.annotations.Mapper;

/**
 * @date 2024/4/20 18:58
 *
*/

@Mapper
public interface NoticeArticleMapper extends MyBaseMapper<NoticeArticle> {
}