package com.cy.educenter.service;

import com.cy.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cy.educenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-04-09
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember ucenterMember);

    void register(RegisterVo registerVo);

    UcenterMember getByOpenid(String openid);

    Integer getRegisterCount(String day);
}
