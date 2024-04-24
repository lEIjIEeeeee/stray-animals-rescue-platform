package com.sarp.core.module.auth.manager;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sarp.core.exception.BizException;
import com.sarp.core.module.common.enums.HttpResultCode;
import com.sarp.core.module.user.dao.MemberMapper;
import com.sarp.core.module.user.model.entity.Member;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @date 2024/3/19 10:29
 */

@Component
@AllArgsConstructor
public class MemberManager {

    private MemberMapper memberMapper;

    public Member getById(String id) {
        return memberMapper.selectById(id);
    }

    public Member getByIdWithExp(String id) {
        Member member = memberMapper.selectById(id);
        if (ObjectUtil.isNull(member)) {
            throw new BizException(HttpResultCode.DATA_NOT_EXISTED);
        }
        return member;
    }

    public List<Member> getMemberListByNickName(String nickName) {
        if (StrUtil.isBlank(nickName)) {
            return Collections.emptyList();
        }
        List<Member> memberList = memberMapper.selectList(Wrappers.lambdaQuery(Member.class)
                                                                  .like(Member::getNickName, nickName));
        return CollUtil.isNotEmpty(memberList)
                ? memberList
                : Collections.emptyList();
    }

}
