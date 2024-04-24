package com.sarp.core.module.notice.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @date 2024/4/21 5:02
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticeArticleRequest {

    @ApiModelProperty(value = "公告文章id")
    @NotBlank(message = "id不能为空", groups = { NoticeArticleRequest.Edit.class })
    private String id;

    @ApiModelProperty(value = "公告类型id")
    @NotBlank(message = "公告类型id不能为空", groups = { NoticeArticleRequest.Add.class, NoticeArticleRequest.Edit.class })
    private String noticeTypeId;

    @ApiModelProperty(value = "公告文章名称")
    @NotBlank(message = "公告文章名称不能为空", groups = { NoticeArticleRequest.Add.class, NoticeArticleRequest.Edit.class })
    private String name;

    @ApiModelProperty(value = "公告文章内容")
    @NotBlank(message = "公告文章内容不能为空", groups = { NoticeArticleRequest.Add.class, NoticeArticleRequest.Edit.class })
    private String content;

    @ApiModelProperty(value = "公告文章封面")
    private String picUrl;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "描述说明")
    private String desc;

    /**
     * 新增校验组
     */
    public interface Add {
    }

    /**
     * 修改校验组
     */
    public interface Edit {
    }

}
