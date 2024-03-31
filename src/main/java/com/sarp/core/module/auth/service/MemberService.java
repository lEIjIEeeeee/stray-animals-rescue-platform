package com.sarp.core.module.auth.service;

import cn.hutool.core.collection.CollUtil;
import com.sarp.core.module.user.dao.MemberMapper;
import com.sarp.core.module.user.model.entity.Member;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @date 2024/3/31 12:10
 */

@Service
@Slf4j
@AllArgsConstructor
public class MemberService {

    private MemberMapper memberMapper;

    public Map<String, Member> getMemberMap(Collection<String> userIds) {
        if (CollUtil.isEmpty(userIds)) {
            return Collections.emptyMap();
        }
        List<Member> memberList = memberMapper.selectBatchIds(userIds);
        if (CollUtil.isEmpty(memberList)) {
            return Collections.emptyMap();
        }
        return memberList.stream()
                         .collect(Collectors.toMap(Member::getId, member -> member));
    }

}
