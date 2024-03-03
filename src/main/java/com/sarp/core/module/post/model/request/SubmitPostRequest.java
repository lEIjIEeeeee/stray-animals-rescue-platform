package com.sarp.core.module.post.model.request;

import com.sarp.core.module.common.enums.BizTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @date 2024/1/30 11:58
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubmitPostRequest {

    @ApiModelProperty(value = "帖子id")
    @NotBlank(message = "帖子id不能为空", groups = { SubmitPostRequest.Edit.class })
    private String id;

    @ApiModelProperty(value = "标题")
    @NotBlank(message = "标题不能为空", groups = { SubmitPostRequest.Add.class, SubmitPostRequest.Edit.class })
    private String title;

    @ApiModelProperty(value = "动物类目id")
    @NotBlank(message = "动物类目不能为空", groups = { SubmitPostRequest.Add.class, SubmitPostRequest.Edit.class })
    private String categoryId;

    @ApiModelProperty(value = "流浪动物id")
    private String animalId;

    @ApiModelProperty(value = "动物名称")
    @NotBlank(message = "名称不能为空", groups = { SubmitPostRequest.Add.class, SubmitPostRequest.Edit.class })
    private String animalName;

    @ApiModelProperty(value = "业务类型")
    @NotNull(message = "业务类型不能为空", groups = { SubmitPostRequest.Add.class, SubmitPostRequest.Edit.class })
    private BizTypeEnum bizType;

    @ApiModelProperty(value = "摘要")
    @NotBlank(message = "摘要不能为空", groups = { SubmitPostRequest.Add.class, SubmitPostRequest.Edit.class })
    private String postAbstract;

    @ApiModelProperty(value = "内容")
    @NotBlank(message = "内容不能为空", groups = { SubmitPostRequest.Add.class, SubmitPostRequest.Edit.class })
    private String content;

//    @ApiModelProperty(value = "动物性别")
//    @NotNull(message = "性别不能为空", groups = { SubmitPostRequest.Add.class, SubmitPostRequest.Edit.class })
//    private GenderEnum animalGender;

//    @ApiModelProperty(value = "动物描述信息")
//    private String animalDesc;

//    @ApiModelProperty(value = "上传照片")
//    @NotEmpty(message = "上传照片不能为空", groups = { SubmitPostRequest.Add.class, SubmitPostRequest.Edit.class })
//    @Size(min = 1, max = 5, message = "请上传1~5张照片")
//    private List<String> mediaList;

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
