package com.sarp.core.module.comment.manager;

import cn.hutool.core.util.ObjectUtil;
import com.sarp.core.exception.BizException;
import com.sarp.core.module.comment.dao.CommentMapper;
import com.sarp.core.module.comment.model.entity.Comment;
import com.sarp.core.module.common.enums.HttpResultCode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @date 2024/4/8 11:54
 */

@Component
@AllArgsConstructor
public class CommentManager {

    private CommentMapper commentMapper;

    public Comment getByIdWithExp(String id) {
        Comment comment = commentMapper.selectById(id);
        if (ObjectUtil.isNull(comment)) {
            throw new BizException(HttpResultCode.DATA_NOT_EXISTED);
        }
        return comment;
    }

}
