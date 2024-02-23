package com.sarp.core.module.post.dao;

import com.sarp.core.module.common.dao.MyBaseMapper;
import com.sarp.core.module.post.model.entity.Post;
import org.apache.ibatis.annotations.Mapper;

/**
 * @date 2024/1/31 10:22
 *
*/

@Mapper
public interface PostMapper extends MyBaseMapper<Post> {
}