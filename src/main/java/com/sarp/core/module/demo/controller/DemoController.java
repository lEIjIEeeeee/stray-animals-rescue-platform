package com.sarp.core.module.demo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sarp.core.module.common.model.HttpResult;
import com.sarp.core.module.common.model.convert.CommonConvert;
import com.sarp.core.module.common.model.request.BaseQueryRequest;
import com.sarp.core.module.common.model.vo.PageVO;
import com.sarp.core.module.demo.model.entity.Demo;
import com.sarp.core.module.demo.model.request.DemoDeleteRequest;
import com.sarp.core.module.demo.model.request.DemoQueryRequest;
import com.sarp.core.module.demo.model.request.DemoRequest;
import com.sarp.core.module.demo.model.response.DemoResponse;
import com.sarp.core.module.demo.service.DemoService;
import com.sarp.core.util.JavaBeanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

/**
 * @date 2024/1/22 13:59
 */

@Api(tags = "测试模块-数据管理相关Demo接口")
@Validated
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/demoModule/demo")
public class DemoController {

    private DemoService demoService;

    @ApiOperation(value = "分页查询数据列表Demo接口")
    @GetMapping("/listPage")
    public HttpResult<PageVO<DemoResponse>> listPage(@Validated(BaseQueryRequest.ListPage.class)
                                                             DemoQueryRequest request) {
        Page<Demo> demoPage = demoService.listPage(request);
        PageVO<DemoResponse> demoResponsePageVO = CommonConvert.convertPageToPageVo(demoPage, DemoResponse.class);
        return HttpResult.success(demoResponsePageVO);
    }

    @ApiOperation(value = "查询数据详情Demo接口")
    @GetMapping("/get")
    public HttpResult<DemoResponse> get(@RequestParam @NotBlank String id) {
        return HttpResult.success(JavaBeanUtils.map(demoService.get(id), DemoResponse.class));
    }

    @ApiOperation(value = "新增数据Demo接口")
    @PostMapping("/add")
    public HttpResult<Void> add(@RequestBody @Validated(DemoRequest.Add.class)
                                        DemoRequest request) {
        demoService.add(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "修改数据Demo接口")
    @PostMapping("/edit")
    public HttpResult<Void> edit(@RequestBody @Validated(DemoRequest.Edit.class)
                                         DemoRequest request) {
        demoService.edit(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "删除数据Demo接口")
    @PostMapping("/delete")
    public HttpResult<Void> delete(@RequestBody @Validated DemoDeleteRequest request) {
        demoService.delete(request);
        return HttpResult.success();
    }

}
