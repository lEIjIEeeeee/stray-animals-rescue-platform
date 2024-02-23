package com.sarp.core.handle;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.sarp.core.context.ContextUtils;
import com.sarp.core.module.auth.model.dto.LoginUser;
import com.sarp.core.module.common.enums.YesOrNoEnum;
import com.sarp.core.module.common.model.entity.BaseDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @date 2024/1/22 15:26
 */

@Component
@Slf4j
public class SetBasicInfoHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Object o = metaObject.getOriginalObject();
        if (o instanceof BaseDO) {
            BaseDO baseDO = (BaseDO) o;
            LoginUser currentUser = ContextUtils.getCurrentUser();
            if (currentUser != null) {
                if (StrUtil.isBlank(baseDO.getCreateId())) {
                    baseDO.setCreateId(currentUser.getId());
                }
                if (StrUtil.isBlank(baseDO.getUpdateId())) {
                    baseDO.setUpdateId(currentUser.getId());
                }
            }
            if (baseDO.getCreateTime() == null) {
                baseDO.setCreateTime(DateUtil.date());
            }
            if (baseDO.getUpdateTime() == null) {
                baseDO.setUpdateTime(DateUtil.date());
            }
            if (baseDO.getIsDel() == null) {
                baseDO.setIsDel(YesOrNoEnum.N.getCode());
            }
        } else {
            strictInsertFill(metaObject, BaseDO.CREATE_TIME, Date.class, DateUtil.date());
            strictInsertFill(metaObject, BaseDO.UPDATE_TIME, Date.class, DateUtil.date());
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Object o = metaObject.getOriginalObject();
        if (o instanceof BaseDO) {
            BaseDO baseDO = (BaseDO) o;
            LoginUser currentUser = ContextUtils.getCurrentUser();
            if (currentUser != null) {
                baseDO.setUpdateId(currentUser.getId());
            }
            baseDO.setUpdateTime(DateUtil.date());
        } else {
            strictUpdateFill(metaObject, BaseDO.UPDATE_TIME, Date.class, DateUtil.date());
        }
    }

}
