package com.sarp.core.util;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sarp.core.module.common.model.request.BaseQueryRequest;

/**
 * @date 2024/1/22 16:25
 */

public class PageUtils {

    public static <T> Page<T> createEmptyPage() {
        return createEmptyPage(1L, 10L);
    }

    public static <T> Page<T> createEmptyPage(Long pageNo, Long pageSize) {
        return new Page<>(pageNo, pageSize);
    }

    public static <T> Page<T> createPage(BaseQueryRequest baseQueryRequest) {
        return new Page<>(baseQueryRequest.getPageNo(), baseQueryRequest.getPageSize());
    }

}
