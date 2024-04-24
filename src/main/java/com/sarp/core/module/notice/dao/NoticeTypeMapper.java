package com.sarp.core.module.notice.dao;

import com.sarp.core.module.common.dao.MyBaseMapper;
import com.sarp.core.module.notice.model.entity.NoticeType;
import org.apache.ibatis.annotations.Mapper;

/**
 * @date 2024/4/20 18:52
 *
*/

@Mapper
public interface NoticeTypeMapper extends MyBaseMapper<NoticeType> {
}