package com.sarp.core.module.comment.model.response;

import com.sarp.core.module.comment.model.dto.ReplayCommentDTO;
import com.sarp.core.module.common.model.response.BaseResponse;
import com.sarp.core.module.common.model.vo.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @date 2024/4/8 15:20
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CommentResponse extends BaseResponse {

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "评论用户id")
    private String createId;

    @ApiModelProperty(value = "评论用户昵称")
    private String nickName;

    @ApiModelProperty(value = "评论用户头像")
    private String avatar;

    @ApiModelProperty(value = "回复评论列表")
    private PageVO<ReplayCommentDTO> replayCommentList;

}
