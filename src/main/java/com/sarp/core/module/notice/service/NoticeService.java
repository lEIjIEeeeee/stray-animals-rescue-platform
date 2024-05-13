package com.sarp.core.module.notice.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sarp.core.module.common.enums.EnableStatusEnum;
import com.sarp.core.module.notice.dao.NoticeArticleMapper;
import com.sarp.core.module.notice.dao.NoticeTypeMapper;
import com.sarp.core.module.notice.manager.NoticeArticleManager;
import com.sarp.core.module.notice.manager.NoticeTypeManager;
import com.sarp.core.module.notice.model.dto.NoticeArticleDetailDTO;
import com.sarp.core.module.notice.model.dto.NoticeTypeDTO;
import com.sarp.core.module.notice.model.entity.NoticeArticle;
import com.sarp.core.module.notice.model.entity.NoticeType;
import com.sarp.core.module.notice.model.request.NoticeQueryRequest;
import com.sarp.core.util.JavaBeanUtils;
import com.sarp.core.util.PageUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @date 2024/4/24 1:16
 */

@Service
@Slf4j
@AllArgsConstructor
public class NoticeService {

    private NoticeArticleMapper noticeArticleMapper;
    private NoticeTypeMapper noticeTypeMapper;

    private NoticeArticleManager noticeArticleManager;
    private NoticeTypeManager noticeTypeManager;

    public Page<NoticeArticle> listPage(NoticeQueryRequest request) {
        LambdaQueryWrapper<NoticeArticle> queryWrapper = Wrappers.lambdaQuery(NoticeArticle.class)
                                                                 .eq(StrUtil.isNotBlank(request.getNoticeTypeId()), NoticeArticle::getNoticeTypeId, request.getNoticeTypeId())
                                                                 .orderByAsc(NoticeArticle::getSort)
                                                                 .orderByDesc(NoticeArticle::getUpdateTime);
        return noticeArticleMapper.selectPage(PageUtils.createPage(request), queryWrapper);
    }

    public NoticeArticleDetailDTO get(String id) {
        NoticeArticle noticeArticle = noticeArticleManager.getByIdWithExp(id);
        NoticeArticleDetailDTO detailDTO = JavaBeanUtils.map(noticeArticle, NoticeArticleDetailDTO.class);
        NoticeType noticeType = noticeTypeManager.getByIdWithExp(detailDTO.getNoticeTypeId());
        detailDTO.setNoticeTypeName(noticeType.getName());
        return detailDTO;
    }

    public List<NoticeTypeDTO> getNoticeTypeDropdownList () {
        List<NoticeType> noticeTypeList = noticeTypeMapper.selectList(Wrappers.lambdaQuery(NoticeType.class)
                                                                              .eq(NoticeType::getStatus, EnableStatusEnum.ENABLE.getCode())
                                                                              .orderByAsc(NoticeType::getSort)
                                                                              .orderByDesc(NoticeType::getUpdateTime));
        if (CollUtil.isEmpty(noticeTypeList)) {
            return Collections.emptyList();
        }
        return JavaBeanUtils.mapList(noticeTypeList, NoticeTypeDTO.class);
    }
}
