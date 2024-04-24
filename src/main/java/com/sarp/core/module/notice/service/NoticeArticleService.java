package com.sarp.core.module.notice.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sarp.core.module.common.model.entity.BaseDO;
import com.sarp.core.module.notice.dao.NoticeArticleMapper;
import com.sarp.core.module.notice.manager.NoticeArticleManager;
import com.sarp.core.module.notice.manager.NoticeTypeManager;
import com.sarp.core.module.notice.model.dto.NoticeArticleDetailDTO;
import com.sarp.core.module.notice.model.entity.NoticeArticle;
import com.sarp.core.module.notice.model.entity.NoticeType;
import com.sarp.core.module.notice.model.request.NoticeArticleDeleteRequest;
import com.sarp.core.module.notice.model.request.NoticeArticleRequest;
import com.sarp.core.module.notice.model.request.PlatformNoticeArticleQueryRequest;
import com.sarp.core.util.JavaBeanUtils;
import com.sarp.core.util.PageUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @date 2024/4/21 4:34
 */

@Service
@Slf4j
@AllArgsConstructor
public class NoticeArticleService {

    private NoticeArticleMapper noticeArticleMapper;

    private NoticeTypeManager noticeTypeManager;
    private NoticeArticleManager noticeArticleManager;

    public Page<NoticeArticle> listPage(PlatformNoticeArticleQueryRequest request) {
        LambdaQueryWrapper<NoticeArticle> queryWrapper = Wrappers.lambdaQuery(NoticeArticle.class)
                                                                 .like(StrUtil.isNotBlank(request.getSearchContent()), NoticeArticle::getName, request.getSearchContent())
                                                                 .eq(StrUtil.isNotBlank(request.getNoticeTypeId()), NoticeArticle::getNoticeTypeId, request.getNoticeTypeId())
                                                                 .orderByAsc(NoticeArticle::getSort)
                                                                 .orderByDesc(NoticeArticle::getUpdateTime);
        return noticeArticleMapper.selectPage(PageUtils.createPage(request), queryWrapper);
    }

    public NoticeArticleDetailDTO get(String id) {
        NoticeArticle noticeArticle = noticeArticleManager.getByIdWithExp(id);
        NoticeArticleDetailDTO detailDTO = JavaBeanUtils.map(noticeArticle, NoticeArticleDetailDTO.class);
        NoticeType noticeType = noticeTypeManager.getByIdWithExp(noticeArticle.getNoticeTypeId());
        detailDTO.setNoticeTypeName(noticeType.getName());
        return detailDTO;
    }

    @Transactional(rollbackFor = Exception.class)
    public void add(NoticeArticleRequest request) {
        noticeTypeManager.getByIdWithExp(request.getNoticeTypeId());
        NoticeArticle noticeArticle = JavaBeanUtils.map(request, NoticeArticle.class, BaseDO.ID);
        noticeArticleMapper.insert(noticeArticle);
    }

    @Transactional(rollbackFor = Exception.class)
    public void edit(NoticeArticleRequest request) {
        noticeTypeManager.getByIdWithExp(request.getNoticeTypeId());
        NoticeArticle noticeArticle = noticeArticleManager.getByIdWithExp(request.getId());
        JavaBeanUtils.map(request, noticeArticle);
        noticeArticleMapper.updateById(noticeArticle);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(NoticeArticleDeleteRequest request) {
        NoticeArticle noticeArticle = noticeArticleManager.getByIdWithExp(request.getId());
        noticeArticleMapper.deleteByIdWithFill(noticeArticle);
    }

}
