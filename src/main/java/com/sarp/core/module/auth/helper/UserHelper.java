package com.sarp.core.module.auth.helper;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.sarp.core.module.auth.model.response.UserResponse;
import com.sarp.core.module.user.dao.MemberMapper;
import com.sarp.core.module.user.model.entity.Member;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @date 2024/3/18 11:39
 */

@Component
@AllArgsConstructor
public class UserHelper {

    private MemberMapper memberMapper;

    public void fillUserListData(List<UserResponse> dataList) {
        if (CollUtil.isEmpty(dataList)) {
            return;
        }
        List<String> userIds = dataList.stream()
                                       .map(UserResponse::getId)
                                       .collect(Collectors.toList());
        List<Member> memberList = memberMapper.selectBatchIds(userIds);
        if (CollUtil.isEmpty(memberList)) {
            return;
        }
        Map<String, Member> memberMap = memberList.stream()
                                                  .collect(Collectors.toMap(Member::getId, member -> member));

        for (UserResponse response : dataList) {
            Member member = memberMap.get(response.getId());
            if (ObjectUtil.isNotNull(member)) {
                response.setNickName(member.getNickName());
                response.setLastLoginTime(member.getLastLoginTime());
            }
        }
    }

}
