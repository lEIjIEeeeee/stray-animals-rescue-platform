package com.sarp.core.module.post.manager;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.sarp.core.exception.BizException;
import com.sarp.core.module.common.enums.HttpResultCode;
import com.sarp.core.module.post.dao.PostMapper;
import com.sarp.core.module.post.model.entity.Post;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @date 2024/3/31 21:20
 */

@Component
@AllArgsConstructor
public class PostManager {

    private PostMapper postMapper;

    public Post getByIdWithExp(String id) {
        Post post = postMapper.selectById(id);
        if (ObjectUtil.isNull(post)) {
            throw new BizException(HttpResultCode.DATA_NOT_EXISTED);
        }
        return post;
    }

    public Map<String, Post> getPostMap(Collection<String> postIds) {
        if (CollUtil.isEmpty(postIds)) {
            return Collections.emptyMap();
        }
        List<Post> postList = postMapper.selectBatchIds(postIds);
        if (CollUtil.isEmpty(postList)) {
            return Collections.emptyMap();
        }
        return postList.stream()
                       .collect(Collectors.toMap(Post::getId, post -> post));
    }

}
