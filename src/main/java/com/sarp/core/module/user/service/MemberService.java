package com.sarp.core.module.user.service;

import cn.hutool.core.util.ObjectUtil;
import com.sarp.core.exception.BizException;
import com.sarp.core.module.common.enums.HttpResultCode;
import com.sarp.core.module.user.dao.MemberMapper;
import com.sarp.core.module.user.model.entity.Member;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @date 2024/1/26 16:26
 */

@Service
@Slf4j
@AllArgsConstructor
public class MemberService {

    private MemberMapper memberMapper;

    public Member getByIdWithExp(String id) {
        Member member = memberMapper.selectById(id);
        if (ObjectUtil.isNull(member)) {
            throw new BizException(HttpResultCode.DATA_NOT_EXISTED);
        }
        return member;
    }

}
