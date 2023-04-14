package com.cy.eduorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cy.commonutils.JwtUtils;
import com.cy.commonutils.R;
import com.cy.eduorder.entity.Order;
import com.cy.eduorder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-04-10
 */
@RestController
@RequestMapping("/eduorder/order")
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;

    // 根据课程id和用户id创建订单，返回订单id
    @PostMapping("{courseId}")
    public R save(@PathVariable String courseId, HttpServletRequest request) {
        String orderId = orderService.saveOrder(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("orderId", orderId);
    }

    // 查询订单信息
    @GetMapping("{orderId}")
    public R get(@PathVariable String orderId) {
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Order::getOrderNo, orderId);
        Order order = orderService.getOne(queryWrapper);
        return R.ok().data("item", order);
    }

    // 根据用户id和课程id查询订单状态
    @GetMapping("isBuyCourse/{memberId}/{courseId}")
    public boolean isBuyCourse(@PathVariable String memberId, @PathVariable String courseId) {
        // 订单状态是1表示支付成功
        int count = orderService.count(new LambdaQueryWrapper<Order>().eq(Order::getMemberId, memberId).eq(Order::getCourseId, courseId).eq(Order::getStatus, 1));
        return count > 0;
    }

}