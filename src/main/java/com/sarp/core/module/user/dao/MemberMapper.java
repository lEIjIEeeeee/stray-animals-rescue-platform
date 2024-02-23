package com.sarp.core.module.user.dao;

import com.sarp.core.module.common.dao.MyBaseMapper;
import com.sarp.core.module.user.model.entity.Member;
import org.apache.ibatis.annotations.Mapper;

/**
 * @date 2024/1/26 15:55
 *
*/

@Mapper
public interface MemberMapper extends MyBaseMapper<Member> {
}