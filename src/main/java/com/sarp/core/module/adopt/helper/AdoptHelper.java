package com.sarp.core.module.adopt.helper;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.sarp.core.module.adopt.model.entity.AdoptRecord;
import com.sarp.core.module.adopt.model.response.PlatformAdoptResponse;
import com.sarp.core.module.animal.dao.AnimalMapper;
import com.sarp.core.module.animal.model.entity.Animal;
import com.sarp.core.module.user.dao.MemberMapper;
import com.sarp.core.module.user.model.entity.Member;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @date 2024/3/29 17:23
 */

@Component
@AllArgsConstructor
public class AdoptHelper {

    private AnimalMapper animalMapper;
    private MemberMapper memberMapper;

    public void fillPlatformAdoptListData(List<AdoptRecord> recordList, List<PlatformAdoptResponse> dataList) {
        if (CollUtil.isEmpty(recordList) || CollUtil.isEmpty(dataList)) {
            return;
        }

        Map<String, AdoptRecord> recordMap = recordList.stream()
                                                       .collect(Collectors.toMap(AdoptRecord::getId, adoptRecord -> adoptRecord));
        dataList.forEach(response -> {
            AdoptRecord adoptRecord = recordMap.get(response.getId());
            if (ObjectUtil.isNotNull(adoptRecord)) {
                response.setAdoptUserId(adoptRecord.getCreateId());
                response.setAuditorId(adoptRecord.getAuditId());
            }
        });

        Set<String> animalIds = dataList.stream()
                                        .map(PlatformAdoptResponse::getAnimalId)
                                        .collect(Collectors.toSet());
        Set<String> adoptUserIds = dataList.stream()
                                           .map(PlatformAdoptResponse::getAdoptUserId)
                                           .collect(Collectors.toSet());
        Set<String> auditorIds = dataList.stream()
                                         .map(PlatformAdoptResponse::getAuditorId)
                                         .collect(Collectors.toSet());

        List<Animal> animalList = animalMapper.selectBatchIds(animalIds);
        List<Member> adoptUserList = memberMapper.selectBatchIds(adoptUserIds);
        List<Member> auditorList = memberMapper.selectBatchIds(auditorIds);

        Map<String, Animal> animalMap = Collections.emptyMap();
        if (CollUtil.isNotEmpty(animalList)) {
            animalMap = animalList.stream()
                                  .collect(Collectors.toMap(Animal::getId, animal -> animal));
        }

        Map<String, Member> adoptUserMap = Collections.emptyMap();
        if (CollUtil.isNotEmpty(adoptUserList)) {
            adoptUserMap = adoptUserList.stream()
                                        .collect(Collectors.toMap(Member::getId, member -> member));
        }

        Map<String, Member> auditorMap = Collections.emptyMap();
        if (CollUtil.isNotEmpty(auditorList)) {
            auditorMap = auditorList.stream()
                                    .collect(Collectors.toMap(Member::getId, member -> member));
        }

        for (PlatformAdoptResponse response : dataList) {
            Animal animal = animalMap.get(response.getAnimalId());
            if (ObjectUtil.isNotNull(animal)) {
                response.setAnimalName(animal.getName());
                response.setAnimalNo(animal.getAnimalNo());
                response.setAnimalName(animal.getName());
            }

            Member adoptUser = adoptUserMap.get(response.getAdoptUserId());
            if (ObjectUtil.isNotNull(adoptUser)) {
                response.setAdoptUserName(adoptUser.getNickName());
            }

            Member auditor = auditorMap.get(response.getAuditorId());
            if (ObjectUtil.isNotNull(auditor)) {
                response.setAuditorName(auditor.getNickName());
            }
        }
    }
}
