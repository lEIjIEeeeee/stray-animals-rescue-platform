package com.sarp.core.module.contribution.helper;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.sarp.core.module.animal.dao.AnimalMapper;
import com.sarp.core.module.animal.model.entity.Animal;
import com.sarp.core.module.contribution.model.response.PlatformContributionResponse;
import com.sarp.core.module.user.dao.MemberMapper;
import com.sarp.core.module.user.model.entity.Member;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @date 2024/3/30 21:52
 */

@Component
@AllArgsConstructor
public class ContributionHelper {

    private AnimalMapper animalMapper;
    private MemberMapper memberMapper;

    public void fillPlatformContributionListData(List<PlatformContributionResponse> dataList) {
        if (CollUtil.isEmpty(dataList)) {
            return;
        }
        Set<String> animalIds = dataList.stream()
                                        .map(PlatformContributionResponse::getAnimalId)
                                        .collect(Collectors.toSet());
        Set<String> applyUserIds = dataList.stream()
                                           .map(PlatformContributionResponse::getCreateId)
                                           .collect(Collectors.toSet());
        Set<String> auditIds = dataList.stream()
                                       .map(PlatformContributionResponse::getAuditId)
                                       .collect(Collectors.toSet());

        List<Animal> animalList = animalMapper.selectBatchIds(animalIds);
        List<Member> applyUserList = memberMapper.selectBatchIds(applyUserIds);
        List<Member> auditorList = memberMapper.selectBatchIds(auditIds);

        Map<String, Animal> animalMap = Collections.emptyMap();
        if (CollUtil.isNotEmpty(animalList)) {
            animalMap = animalList.stream()
                                  .collect(Collectors.toMap(Animal::getId, animal -> animal));
        }

        Map<String, Member> applyUserMap = Collections.emptyMap();
        if (CollUtil.isNotEmpty(applyUserList)) {
            applyUserMap = applyUserList.stream()
                                        .collect(Collectors.toMap(Member::getId, member -> member));
        }

        Map<String, Member> auditorMap = Collections.emptyMap();
        if (CollUtil.isNotEmpty(auditorList)) {
            auditorMap = auditorList.stream()
                                    .collect(Collectors.toMap(Member::getId, member -> member));
        }

        for (PlatformContributionResponse response : dataList) {
            Animal animal = animalMap.get(response.getAnimalId());
            if (ObjectUtil.isNotNull(animal)) {
                response.setAnimalName(animal.getName());
                response.setAnimalNo(animal.getAnimalNo());
            }

            Member applyUser = applyUserMap.get(response.getCreateId());
            if (ObjectUtil.isNotNull(applyUser)) {
                response.setApplyUserName(applyUser.getNickName());
            }

            Member auditor = auditorMap.get(response.getAuditId());
            if (ObjectUtil.isNotNull(auditor)) {
                response.setAuditorName(auditor.getNickName());
            }
        }
    }

}
