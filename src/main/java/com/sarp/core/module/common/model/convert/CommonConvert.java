package com.sarp.core.module.common.model.convert;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sarp.core.module.common.model.request.BaseQueryRequest;
import com.sarp.core.module.common.model.vo.PageVO;
import com.sarp.core.util.JavaBeanUtils;

import java.util.List;

/**
 * @date 2024/1/22 16:49
 */

public class CommonConvert {

    public static <S, T> PageVO<T> convertPageToPageVo(Page<S> page, Class<T> responseClass) {
        return PageVO.<T>builder()
                     .pageNo(page.getCurrent())
                     .pageSize(page.getSize())
                     .total(page.getTotal())
                     .dataList(JavaBeanUtils.mapList(page.getRecords(), responseClass))
                     .build();
    }

    public static <S, T> PageVO<T> convertListToPageVO(BaseQueryRequest queryRequest, Long total, List<S> list, Class<T> responseClass) {
        return PageVO.<T>builder()
                     .pageNo(queryRequest.getPageNo())
                     .pageSize(queryRequest.getPageSize())
                     .total(total)
                     .dataList(JavaBeanUtils.mapList(list, responseClass))
                     .build();
    }

}
