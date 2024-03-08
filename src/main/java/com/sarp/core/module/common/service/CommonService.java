package com.sarp.core.module.common.service;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sarp.core.module.category.dao.CategoryMapper;
import com.sarp.core.module.category.model.entity.Category;
import com.sarp.core.module.common.constant.NumberConstants;
import com.sarp.core.module.common.model.dto.CategoryTreeDTO;
import com.sarp.core.module.user.dao.UserMapper;
import com.sarp.core.module.user.enums.UserStatusEnum;
import com.sarp.core.module.user.enums.UserTypeEnum;
import com.sarp.core.module.user.model.entity.User;
import com.sarp.core.util.JavaBeanUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @date 2024/3/8 21:18
 */

@Service
@Slf4j
@AllArgsConstructor
public class CommonService {

    private CategoryMapper categoryMapper;
    private UserMapper userMapper;

    public CategoryTreeDTO getCategoryTree() {
        CategoryTreeDTO categoryTree = getCategoryEmptyTree();

        List<Category> categoryList = categoryMapper.selectList(Wrappers.lambdaQuery(Category.class));
        if (CollUtil.isEmpty(categoryList)) {
            return categoryTree;
        }

        List<CategoryTreeDTO> categoryTreeDTOList = JavaBeanUtils.mapList(categoryList, CategoryTreeDTO.class);
        List<CategoryTreeDTO> resultTree = categoryTreeDTOList.stream()
                                                              .filter(categoryTreeDTO -> Category.TERR_ROOT_ID.equals(categoryTreeDTO.getPid()))
                                                              .peek(categoryTreeDTO -> categoryTreeDTO.setChildren(getChildrenList(categoryTreeDTO, categoryTreeDTOList)))
                                                              .sorted(Comparator.comparing(CategoryTreeDTO::getSort))
                                                              .collect(Collectors.toList());

        if (CollUtil.isEmpty(resultTree)) {
            return categoryTree;
        }
        categoryTree.setChildren(resultTree);
        return categoryTree;
    }

    private CategoryTreeDTO getCategoryEmptyTree() {
        return CategoryTreeDTO.builder()
                              .id(Category.TERR_ROOT_ID)
                              .pid(Category.TERR_ROOT_PID)
                              .name(Category.TERR_ROOT_NAME)
                              .level(NumberConstants.ZERO)
                              .sort(NumberConstants.ONE)
                              .build();
    }

    private List<CategoryTreeDTO> getChildrenList(CategoryTreeDTO pCategoryTreeDTO, List<CategoryTreeDTO> childrenList) {
        return childrenList.stream()
                           .filter(o -> pCategoryTreeDTO.getId().equals(o.getPid()))
                           .peek(o -> o.setChildren(getChildrenList(o, childrenList)))
                           .sorted(Comparator.comparing(CategoryTreeDTO::getSort))
                           .collect(Collectors.toList());
    }

    public List<User> getAnimalOwnerList() {
        List<User> userList = userMapper.selectList(Wrappers.lambdaQuery(User.class)
                                                            .eq(User::getUserType, UserTypeEnum.NORMAL_USER)
                                                            .eq(User::getStatus, UserStatusEnum.NORMAL.getCode()));
        if (CollUtil.isEmpty(userList)) {
            return Collections.emptyList();
        }
        return userList;
    }

}
