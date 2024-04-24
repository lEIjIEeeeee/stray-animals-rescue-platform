package com.sarp.core.module.comment.dao;

import com.sarp.core.module.comment.model.dto.ReplayCountDTO;
import com.sarp.core.module.comment.model.dto.ReplayCommentDTO;
import com.sarp.core.module.comment.model.entity.Comment;
import com.sarp.core.module.common.dao.MyBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @date 2024/4/8 10:31
 */

@Mapper
public interface CommentMapper extends MyBaseMapper<Comment> {

    List<ReplayCountDTO> getReplayCountList(@Param("postId") String postId, @Param("rootIds") Collection<String> rootIds);

    List<ReplayCommentDTO> getReplayList(@Param("pageSize") Long pageSize, @Param("postId") String postId, @Param("rootIds") Collection<String> rootIds);

}