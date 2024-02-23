package com.sarp.core.module.comment.dao;

import com.sarp.core.module.comment.model.entity.Comment;
import com.sarp.core.module.common.dao.MyBaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @date 2024/2/23 16:29
 *
*/

@Mapper
public interface CommentMapper extends MyBaseMapper<Comment> {
}