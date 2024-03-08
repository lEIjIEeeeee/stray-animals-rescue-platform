package com.sarp.core.module.comment.controller;

import com.sarp.core.module.comment.service.CommentService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @date 2024/2/23 16:30
 */

@Api(tags = "用户端评论模块-用户评论相关接口")
@Validated
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/commentModule/comment")
public class CommentController {

    private CommentService commentService;

}
