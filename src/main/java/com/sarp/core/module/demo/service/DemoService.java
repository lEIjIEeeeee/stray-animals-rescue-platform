package com.sarp.core.module.demo.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sarp.core.exception.BizException;
import com.sarp.core.module.common.enums.HttpResultCode;
import com.sarp.core.module.common.model.entity.BaseDO;
import com.sarp.core.module.demo.dao.DemoMapper;
import com.sarp.core.module.demo.model.entity.Demo;
import com.sarp.core.module.demo.model.request.DemoDeleteRequest;
import com.sarp.core.module.demo.model.request.DemoQueryRequest;
import com.sarp.core.module.demo.model.request.DemoRequest;
import com.sarp.core.util.JavaBeanUtils;
import com.sarp.core.util.PageUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @date 2024/1/22 14:00
 */

@Service
@Slf4j
@AllArgsConstructor
public class DemoService {

    private DemoMapper demoMapper;

    public Page<Demo> listPage(DemoQueryRequest request) {
        return demoMapper.selectPage(PageUtils.createPage(request), Wrappers.lambdaQuery(Demo.class)
                                                                            .like(StrUtil.isNotBlank(request.getName()), Demo::getName, request.getName())
                                                                            .like(StrUtil.isNotBlank(request.getPhone()), Demo::getPhone, request.getPhone())
                                                                            .orderByDesc(Demo::getUpdateTime));
    }

    public Demo get(String id) {
        return getByIdWithExp(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void add(DemoRequest request) {
        Demo demo = JavaBeanUtils.map(request, Demo.class, BaseDO.ID);
        demoMapper.insert(demo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void edit(DemoRequest request) {
        Demo demo = getByIdWithExp(request.getId());
        JavaBeanUtils.map(request, demo);
        demoMapper.updateById(demo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(DemoDeleteRequest request) {
        Demo demo = getByIdWithExp(request.getId());
        demoMapper.deleteByIdWithFill(demo);
    }

    private Demo getById(String id) {
        return demoMapper.selectById(id);
    }

    private Demo getByIdWithExp(String id) {
        Demo demo = demoMapper.selectById(id);
        if (demo == null) {
            throw new BizException(HttpResultCode.DATA_NOT_EXISTED);
        }
        return demo;
    }

}
