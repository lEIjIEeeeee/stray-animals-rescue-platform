package com.sarp.core.module.common.model.convert;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sarp.core.module.common.model.vo.PageVO;
import com.sarp.core.util.JavaBeanUtils;

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

}
