package com.cy.educenter.mapper;

import com.cy.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2023-04-09
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    Integer getRegisterCount(String day);
}
