package com.sarp.core.module.media.service;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sarp.core.module.common.enums.UploadBizTypeEnum;
import com.sarp.core.module.media.dao.MediaMapper;
import com.sarp.core.module.media.model.entity.Media;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @date 2024/3/27 21:09
 */

@Service
@Slf4j
@AllArgsConstructor
public class MediaService {

    private MediaMapper mediaMapper;

    public List<Media> getMediaList(String serviceId, UploadBizTypeEnum uploadBizType) {
        List<Media> mediaList = mediaMapper.selectList(Wrappers.lambdaQuery(Media.class)
                                                               .eq(Media::getServiceId, serviceId)
                                                               .eq(Media::getServiceType, uploadBizType.name())
                                                               .orderByAsc(Media::getSort));
        if (CollUtil.isEmpty(mediaList)) {
            return Collections.emptyList();
        }
        return mediaList;
    }

    public Map<String, List<Media>> getMediaMap(Collection<String> serviceIds, UploadBizTypeEnum uploadBizType) {
        if (CollUtil.isEmpty(serviceIds)) {
            return Collections.emptyMap();
        }
        List<Media> mediaList = mediaMapper.selectList(Wrappers.lambdaQuery(Media.class)
                                                               .in(Media::getServiceId, serviceIds)
                                                               .eq(Media::getServiceType, uploadBizType.name()));
        if (CollUtil.isEmpty(mediaList)) {
            return Collections.emptyMap();
        }
        return mediaList.stream()
                        .collect(Collectors.groupingBy(Media::getServiceId, Collectors.collectingAndThen(Collectors.toList(),
                                list -> {
                                    list.sort(Comparator.comparing(Media::getSort));
                                    return list;
                                })));
    }
}
