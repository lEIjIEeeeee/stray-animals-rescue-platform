package com.sarp.core.module.comment.service;

import com.sarp.core.module.comment.dao.CommentMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @date 2024/2/23 16:32
 */

@Service
@Slf4j
@AllArgsConstructor
public class CommentService {

    private CommentMapper commentMapper;

}
