package com.sarp.core.module.post.manager;

import cn.hutool.core.util.ObjectUtil;
import com.sarp.core.exception.BizException;
import com.sarp.core.module.common.enums.HttpResultCode;
import com.sarp.core.module.post.dao.PostMapper;
import com.sarp.core.module.post.model.entity.Post;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

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

}
