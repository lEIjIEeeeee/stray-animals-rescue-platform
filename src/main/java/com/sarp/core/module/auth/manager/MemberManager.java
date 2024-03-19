package com.sarp.core.module.auth.manager;

import cn.hutool.core.util.ObjectUtil;
import com.sarp.core.exception.BizException;
import com.sarp.core.module.common.enums.HttpResultCode;
import com.sarp.core.module.user.dao.MemberMapper;
import com.sarp.core.module.user.model.entity.Member;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @date 2024/3/19 10:29
 */

@Component
@AllArgsConstructor
public class MemberManager {

    private MemberMapper memberMapper;

    public Member getByIdWithExp(String id) {
        Member member = memberMapper.selectById(id);
        if (ObjectUtil.isNull(member)) {
            throw new BizException(HttpResultCode.DATA_NOT_EXISTED);
        }
        return member;
    }

}
