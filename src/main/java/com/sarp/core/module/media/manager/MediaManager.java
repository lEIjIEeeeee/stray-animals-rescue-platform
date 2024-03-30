package com.sarp.core.module.media.manager;

import cn.hutool.core.util.ObjectUtil;
import com.sarp.core.exception.BizException;
import com.sarp.core.module.common.enums.HttpResultCode;
import com.sarp.core.module.media.dao.MediaMapper;
import com.sarp.core.module.media.model.entity.Media;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @date 2024/3/27 22:19
 */

@Component
@AllArgsConstructor
public class MediaManager {

    private MediaMapper mediaMapper;

    public Media getById(String id) {
        return mediaMapper.selectById(id);
    }

    public Media getByIdWithExp(String id) {
        Media media = mediaMapper.selectById(id);
        if (ObjectUtil.isNull(media)) {
            throw new BizException(HttpResultCode.DATA_NOT_EXISTED);
        }
        return media;
    }

}
