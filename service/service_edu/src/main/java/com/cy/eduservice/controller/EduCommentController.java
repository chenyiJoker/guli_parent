package com.cy.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cy.commonutils.JwtUtils;
import com.cy.commonutils.R;
import com.cy.commonutils.vo.UcenterMemberOrder;
import com.cy.eduservice.client.VodClient;
import com.cy.eduservice.entity.EduComment;
import com.cy.eduservice.service.EduCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-04-10
 */
@RestController
@RequestMapping("/eduservice/comment")
@CrossOrigin
public class EduCommentController {
    @Autowired
    private EduCommentService commentService;

    @Qualifier("com.cy.eduservice.client.VodClient")
    @Autowired
    private VodClient vodClient;

    @GetMapping ("{page}/{limit}")
    public R getById(@PathVariable long page, @PathVariable long limit, String courseId) {
        Page<EduComment> pageParam = new Page<>(page, limit);
        Map<String, Object> map = commentService.getPage(pageParam, courseId);
        return R.ok().data(map);
    }

    // 添加评论
    @PostMapping
    public R save(@RequestBody EduComment eduComment, HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(memberId)) {
            return R.error().message("请登录");
        }
        eduComment.setMemberId(memberId);

        UcenterMemberOrder ucenterInfo = vodClient.getUcenterPay(memberId);

        eduComment.setNickname(ucenterInfo.getNickname());
        eduComment.setAvatar(ucenterInfo.getAvatar());

        commentService.save(eduComment);
        return R.ok();
    }
}

