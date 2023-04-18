package com.cy.educenter.controller;


import com.cy.commonutils.JwtUtils;
import com.cy.commonutils.R;
import com.cy.commonutils.vo.UcenterMemberOrder;
import com.cy.educenter.entity.UcenterMember;
import com.cy.educenter.entity.vo.RegisterVo;
import com.cy.educenter.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-04-09
 */
@RestController
@RequestMapping("/educenter/member")
//@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;

    @PostMapping("login")
    public R login(@RequestBody UcenterMember ucenterMember) {
        String token = memberService.login(ucenterMember);
        return R.ok().data("token", token);
    }

    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo) {
        memberService.register(registerVo);
        return R.ok();
    }

    // 根据token获取登录信息
    @GetMapping
    public R getMemberInfo(HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember member = memberService.getById(memberId);
        return R.ok().data("memberInfo", member);
    }

    // 根据token字符串获取用户信息
    @PostMapping("{id}")
    public UcenterMemberOrder getInfo(@PathVariable String id) {
        // 根据用户id获取用户信息
        UcenterMember ucenterMember = memberService.getById(id);
        UcenterMemberOrder member = new UcenterMemberOrder();

        BeanUtils.copyProperties(ucenterMember, member);
        return member;
    }

    // 查询某一天的注册人数
    @GetMapping("{day}")
    public R registerCount(@PathVariable String day) {
        Integer count = memberService.getRegisterCount(day);
        return R.ok().data("countRegister", count);
    }
}

