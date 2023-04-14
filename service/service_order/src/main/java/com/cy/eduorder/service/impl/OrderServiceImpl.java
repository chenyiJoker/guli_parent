package com.cy.eduorder.service.impl;

import com.cy.commonutils.vo.CourseWebVoOrder;
import com.cy.commonutils.vo.UcenterMemberOrder;
import com.cy.eduorder.client.EduClient;
import com.cy.eduorder.client.UcenterClient;
import com.cy.eduorder.entity.Order;
import com.cy.eduorder.mapper.OrderMapper;
import com.cy.eduorder.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cy.eduorder.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-04-10
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    private EduClient eduClient;

    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public String saveOrder(String courseId, String memberId) {
        // 远程调用课程服务，根据课程id获取课程信息
        CourseWebVoOrder courseInfo = eduClient.getCourseInfo(courseId);

        // 远程调用用户服务，根据用户id获取用户信息
        UcenterMemberOrder ucenterMember = ucenterClient.getInfo(memberId);
        // 创建订单
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseInfo.getTitle());
        order.setCourseCover(courseInfo.getCover());
        order.setTeacherName(courseInfo.getTeacherName());
        order.setTotalFee(courseInfo.getPrice());
        order.setMemberId(memberId);
        order.setMobile(ucenterMember.getMobile());
        order.setNickname(ucenterMember.getNickname());
        order.setStatus(0);
        order.setPayType(1);
        baseMapper.insert(order);

        // 返回订单号
        return order.getOrderNo();
    }
}
