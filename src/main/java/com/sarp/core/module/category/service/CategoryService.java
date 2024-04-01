package com.sarp.core.module.category.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sarp.core.exception.BizException;
import com.sarp.core.module.animal.dao.AnimalMapper;
import com.sarp.core.module.animal.model.entity.Animal;
import com.sarp.core.module.category.dao.CategoryMapper;
import com.sarp.core.module.category.model.entity.Category;
import com.sarp.core.module.category.model.request.CategoryChangeStatusRequest;
import com.sarp.core.module.category.model.request.CategoryDeleteRequest;
import com.sarp.core.module.category.model.request.CategoryQueryRequest;
import com.sarp.core.module.category.model.request.CategoryRequest;
import com.sarp.core.module.common.enums.EnableStatusEnum;
import com.sarp.core.module.common.enums.HttpResultCode;
import com.sarp.core.module.common.model.entity.BaseDO;
import com.sarp.core.util.JavaBeanUtils;
import com.sarp.core.util.PageUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @date 2024/1/31 16:46
 */

@Service
@Slf4j
@AllArgsConstructor
public class CategoryService extends ServiceImpl<CategoryMapper, Category> {

    private CategoryMapper categoryMapper;
    private AnimalMapper animalMapper;

    public Page<Category> listPage(CategoryQueryRequest request) {
        LambdaQueryWrapper<Category> lqw = Wrappers.lambdaQuery(Category.class)
                                                   .eq(Category::getPid, request.getPid())
                                                   .orderByAsc(Category::getSort)
                                                   .orderByAsc(Category::getCreateTime);
        return categoryMapper.selectPage(PageUtils.createPage(request), lqw);
    }

    public Category get(String id) {
        return getByIdWithExp(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void add(CategoryRequest request) {
        Category category = JavaBeanUtils.map(request, Category.class, BaseDO.ID);
        fillCategoryLevel(category);
        category.setStatus(request.getStatus().getCode());
        categoryMapper.insert(category);
    }

    @Transactional(rollbackFor = Exception.class)
    public void edit(CategoryRequest request) {
        Category category = getByIdWithExp(request.getId());
        JavaBeanUtils.map(request, category);
        fillCategoryLevel(category);
        category.setStatus(request.getStatus().getCode());
        categoryMapper.updateById(category);
    }

    private void fillCategoryLevel(Category category) {
        Category pCategory = getByIdWithExp(category.getPid());

        Set<String> recursiveDownCategoryIds = categoryMapper.recursiveDownCategoryId(category.getId());
        if (CollUtil.isNotEmpty(recursiveDownCategoryIds)) {
            if (recursiveDownCategoryIds.contains(category.getPid())) {
                throw new BizException(HttpResultCode.BIZ_EXCEPTION, "不能将当前节点放置在自身或者其子节点下");
            }
        }

        if (Category.TERR_ROOT_ID.equals(pCategory.getId())) {
            category.setLevel(1);
        } else {
            category.setLevel(pCategory.getLevel() + 1);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void changeStatus(CategoryChangeStatusRequest request) {
        Set<String> categoryIds = CollUtil.newHashSet();
        if (EnableStatusEnum.ENABLE.equals(request.getStatus())) {
            categoryIds.addAll(categoryMapper.recursiveUpCategoryId(request.getId()));
        } else {
            categoryIds.addAll(categoryMapper.recursiveDownCategoryId(request.getId()));
        }

        if (CollUtil.isEmpty(categoryIds)) {
            throw new BizException(HttpResultCode.BIZ_DATA_EXCEPTION);
        }

        List<Category> categoryList = categoryMapper.selectBatchIds(categoryIds);
        if (CollUtil.isEmpty(categoryList)) {
            throw new BizException(HttpResultCode.DATA_NOT_EXISTED);
        }

        categoryList.forEach(category ->
                category.setStatus(request.getStatus().getCode()));
        updateBatchById(categoryList);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(CategoryDeleteRequest request) {
        Category category = getByIdWithExp(request.getId());
        List<Animal> animalList = animalMapper.selectList(Wrappers.lambdaQuery(Animal.class)
                                                                  .eq(Animal::getCategoryId, category.getId()));
        if (CollUtil.isNotEmpty(animalList)) {
            throw new BizException(HttpResultCode.BIZ_EXCEPTION, "该动物分类下有记录在案的流浪动物，无法进行删除操作");
        }
        categoryMapper.deleteByIdWithFill(category);
    }

    private Category getByIdWithExp(String id) {
        Category category = categoryMapper.selectById(id);
        if (ObjectUtil.isNull(category)) {
            throw new BizException(HttpResultCode.DATA_NOT_EXISTED);
        }
        return category;
    }

    public Set<String> getRecursiveCategoryIds(Collection<String> categoryIds) {
        Set<String> recursiveDownCategoryIds = CollUtil.newHashSet();

        if (CollUtil.isEmpty(categoryIds)) {
            return Collections.emptySet();
        }

        for (String id : categoryIds) {
            Set<String> childIds = categoryMapper.recursiveDownCategoryId(id);
            if (CollUtil.isNotEmpty(childIds)) {
                recursiveDownCategoryIds.addAll(childIds);
            }
        }

        return CollUtil.isNotEmpty(recursiveDownCategoryIds)
                ? recursiveDownCategoryIds : Collections.emptySet();
    }

    public Map<String, Category> getCategoryMap(Collection<String> categoryIds) {
        if (CollUtil.isEmpty(categoryIds)) {
            return Collections.emptyMap();
        }
        List<Category> categoryList = categoryMapper.selectBatchIds(categoryIds);
        if (CollUtil.isEmpty(categoryList)) {
            return Collections.emptyMap();
        }
        return categoryList.stream()
                           .collect(Collectors.toMap(Category::getId, category -> category));
    }

}
