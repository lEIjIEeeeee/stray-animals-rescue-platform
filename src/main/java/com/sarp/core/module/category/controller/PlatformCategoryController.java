package com.sarp.core.module.category.controller;

import com.sarp.core.module.category.model.request.CategoryDeleteRequest;
import com.sarp.core.module.category.model.request.CategoryQueryRequest;
import com.sarp.core.module.category.model.request.CategoryRequest;
import com.sarp.core.module.category.model.request.CategoryChangeStatusRequest;
import com.sarp.core.module.category.model.response.CategoryResponse;
import com.sarp.core.module.category.service.CategoryService;
import com.sarp.core.module.common.model.HttpResult;
import com.sarp.core.module.common.model.convert.CommonConvert;
import com.sarp.core.module.common.model.request.BaseQueryRequest;
import com.sarp.core.module.common.model.vo.PageVO;
import com.sarp.core.util.JavaBeanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

/**
 * @date 2024/3/1 1:30
 */

@Api(tags = "平台端类目模块-动物类目信息管理相关接口")
@Validated
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/categoryModule/platform/category")
public class PlatformCategoryController {

    private CategoryService categoryService;

    @ApiOperation(value = "分页查询类目信息列表")
    @GetMapping("/listPage")
    public HttpResult<PageVO<CategoryResponse>> listPage(@Validated(BaseQueryRequest.ListPage.class)
                                                                 CategoryQueryRequest request) {
        return HttpResult.success(CommonConvert.convertPageToPageVo(categoryService.listPage(request), CategoryResponse.class));
    }

    @ApiOperation(value = "查询类目信息详情")
    @GetMapping("/get")
    public HttpResult<CategoryResponse> get(@RequestParam @NotBlank String id) {
        return HttpResult.success(JavaBeanUtils.map(categoryService.get(id), CategoryResponse.class));
    }

    @ApiOperation(value = "新增类目信息")
    @PostMapping("/add")
    public HttpResult<Void> add(@RequestBody @Validated(CategoryRequest.Add.class)
                                        CategoryRequest request) {
        categoryService.add(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "修改类目信息")
    @PostMapping("/edit")
    public HttpResult<Void> edit(@RequestBody @Validated(CategoryRequest.Edit.class)
                                         CategoryRequest request) {
        categoryService.edit(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "启用或禁用动物类目")
    @PostMapping("/changeStatus")
    public HttpResult<Void> changeStatus(@RequestBody @Validated CategoryChangeStatusRequest request) {
        categoryService.changeStatus(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "删除类目信息")
    @PostMapping("/delete")
    public HttpResult<Void> delete(@RequestBody @Validated CategoryDeleteRequest request) {
        categoryService.delete(request);
        return HttpResult.success();
    }

}
