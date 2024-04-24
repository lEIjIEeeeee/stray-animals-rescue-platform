package com.sarp.core.module.notice.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sarp.core.module.common.model.entity.BaseDO;
import com.sarp.core.module.notice.dao.NoticeTypeMapper;
import com.sarp.core.module.notice.manager.NoticeTypeManager;
import com.sarp.core.module.notice.model.dto.NoticeTypeDTO;
import com.sarp.core.module.notice.model.entity.NoticeType;
import com.sarp.core.module.notice.model.request.NoticeTypeDeleteRequest;
import com.sarp.core.module.notice.model.request.PlatformNoticeTypeQueryRequest;
import com.sarp.core.module.notice.model.request.NoticeTypeRequest;
import com.sarp.core.util.JavaBeanUtils;
import com.sarp.core.util.PageUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * @date 2024/4/20 18:35
 */

@Service
@Slf4j
@AllArgsConstructor
public class NoticeTypeService {

    private NoticeTypeMapper noticeTypeMapper;

    private NoticeTypeManager noticeTypeManager;

    public Page<NoticeType> listPage(PlatformNoticeTypeQueryRequest request) {
        LambdaQueryWrapper<NoticeType> queryWrapper = Wrappers.lambdaQuery(NoticeType.class)
                                                              .like(StrUtil.isNotBlank(request.getSearchContent()), NoticeType::getName, request.getSearchContent())
                                                              .orderByAsc(NoticeType::getSort)
                                                              .orderByDesc(NoticeType::getUpdateTime);
        if (request.getStatus() != null) {
            queryWrapper.eq(request.getStatus() != null, NoticeType::getStatus, request.getStatus().getCode());
        }
        return noticeTypeMapper.selectPage(PageUtils.createPage(request), queryWrapper);
    }

    public NoticeType get(String id) {
        return noticeTypeManager.getByIdWithExp(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void add(NoticeTypeRequest request) {
        NoticeType noticeType = JavaBeanUtils.map(request, NoticeType.class, BaseDO.ID);
        noticeType.setStatus(request.getStatus().getCode());
        noticeTypeMapper.insert(noticeType);
    }

    @Transactional(rollbackFor = Exception.class)
    public void edit(NoticeTypeRequest request) {
        NoticeType noticeType = noticeTypeManager.getByIdWithExp(request.getId());
        JavaBeanUtils.map(request, noticeType);
        noticeType.setStatus(request.getStatus().getCode());
        noticeTypeMapper.updateById(noticeType);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(NoticeTypeDeleteRequest request) {
        NoticeType noticeType = noticeTypeManager.getByIdWithExp(request.getId());
        noticeTypeMapper.deleteByIdWithFill(noticeType);
    }

    public List<NoticeTypeDTO> dropdownList() {
        List<NoticeType> noticeTypeList = noticeTypeMapper.selectList(Wrappers.lambdaQuery(NoticeType.class)
                                                                              .orderByAsc(NoticeType::getSort)
                                                                              .orderByDesc(NoticeType::getUpdateTime));
        if (CollUtil.isEmpty(noticeTypeList)) {
            return Collections.emptyList();
        }
        return JavaBeanUtils.mapList(noticeTypeList, NoticeTypeDTO.class);
    }

}
