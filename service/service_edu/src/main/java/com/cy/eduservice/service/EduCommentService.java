package com.cy.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cy.eduservice.entity.EduComment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-04-10
 */
public interface EduCommentService extends IService<EduComment> {

    Map<String, Object> getPage(Page<EduComment> pageParam, String courseId);
}
