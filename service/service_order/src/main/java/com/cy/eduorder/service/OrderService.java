package com.cy.eduorder.service;

import com.cy.eduorder.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-04-10
 */
public interface OrderService extends IService<Order> {

    String saveOrder(String courseId, String memberId);
}
