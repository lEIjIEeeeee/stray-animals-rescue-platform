package com.sarp.core.module.common.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sarp.core.context.ContextUtils;
import com.sarp.core.module.animal.dao.AnimalMapper;
import com.sarp.core.module.animal.model.entity.Animal;
import com.sarp.core.module.auth.manager.UserManager;
import com.sarp.core.module.category.dao.CategoryMapper;
import com.sarp.core.module.category.model.entity.Category;
import com.sarp.core.module.common.constant.NumberConstants;
import com.sarp.core.module.common.model.dto.CategoryTreeDTO;
import com.sarp.core.module.common.model.dto.PersonalInfoDTO;
import com.sarp.core.module.common.model.dto.UserBizCountsDTO;
import com.sarp.core.module.common.model.request.PersonalListQueryRequest;
import com.sarp.core.module.post.dao.PostMapper;
import com.sarp.core.module.post.model.entity.Post;
import com.sarp.core.module.user.dao.UserMapper;
import com.sarp.core.module.user.enums.UserStatusEnum;
import com.sarp.core.module.user.enums.UserTypeEnum;
import com.sarp.core.module.user.model.entity.User;
import com.sarp.core.util.JavaBeanUtils;
import com.sarp.core.util.PageUtils;
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
    private AnimalMapper animalMapper;
    private PostMapper postMapper;

    private UserManager userManager;

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

    public PersonalInfoDTO personalInfo() {
        String userId = ContextUtils.getCurrentUserId();
        User user = userManager.getByIdWithExp(userId);
        PersonalInfoDTO personalInfo = PersonalInfoDTO.builder()
                                                      .id(userId)
                                                      .avatar(user.getAvatar())
                                                      .nickName(user.getName())
                                                      .userType(user.getUserType())
                                                      .createTime(user.getCreateTime())
                                                      .build();
        return personalInfo;
    }

    public UserBizCountsDTO getCounts() {
        String userId = ContextUtils.getCurrentUserId();
        List<Animal> animalList = animalMapper.selectList(Wrappers.lambdaQuery(Animal.class)
                                                                  .eq(Animal::getOwnerId, userId));
        List<Post> postList = postMapper.selectList(Wrappers.lambdaQuery(Post.class)
                                                            .eq(Post::getCreateId, userId));
        return UserBizCountsDTO.builder()
                               .accessAmount(NumberConstants.ZERO)
                               .animalAmount(animalList.size())
                               .postAmount(postList.size())
                               .applyAmount(NumberConstants.ZERO)
                               .build();
    }

    public Page<Animal> listPageAnimal(PersonalListQueryRequest request) {
        LambdaQueryWrapper<Animal> queryWrapper = Wrappers.lambdaQuery(Animal.class)
                                                          .eq(Animal::getOwnerId, ContextUtils.getCurrentUserId())
                                                          .and(StrUtil.isNotBlank(request.getSearchContent()),
                                                                  wrapper -> {
                                                                      wrapper.or(wp -> wp.like(Animal::getName, request.getSearchContent()))
                                                                             .or(wp -> wp.like(Animal::getAnimalNo, request.getSearchContent()))
                                                                             .or(wp -> wp.like(Animal::getDesc, request.getSearchContent()));
                                                                  })
                                                          .orderByDesc(Animal::getUpdateTime);
        return animalMapper.selectPage(PageUtils.createPage(request), queryWrapper);
    }

    public Page<Post> listPagePost(PersonalListQueryRequest request) {
        LambdaQueryWrapper<Post> queryWrapper = Wrappers.lambdaQuery(Post.class)
                                                        .eq(Post::getCreateId, ContextUtils.getCurrentUserId())
                                                        .and(StrUtil.isNotBlank(request.getSearchContent()), wrapper -> {
                                                            wrapper.or(wp -> wp.like(Post::getTitle, request.getSearchContent()))
                                                                   .or(wp -> wp.like(Post::getPostAbstract, request.getSearchContent()))
                                                                   .or(wp -> wp.like(Post::getAnimalName, request.getSearchContent()));
                                                        }).orderByDesc(Post::getUpdateTime);
        return postMapper.selectPage(PageUtils.createPage(request), queryWrapper);
    }

}
