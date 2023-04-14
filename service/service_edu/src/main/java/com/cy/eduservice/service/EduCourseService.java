package com.cy.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cy.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cy.eduservice.entity.EduTeacher;
import com.cy.eduservice.entity.front.CourseQueryVo;
import com.cy.eduservice.entity.front.CourseWebVo;
import com.cy.eduservice.entity.vo.CourseInfoVo;
import com.cy.eduservice.entity.vo.CoursePublishVo;
import com.cy.eduservice.entity.vo.CourseQuery;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-04-03
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseById(String id);

    void updateCourseById(CourseInfoVo courseInfoVo);

    CoursePublishVo getCoursePublish(String id);

    void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery);

    void deleteById(String id);

    Map<String, Object> getTeacherFrontList(Page<EduCourse> pageParam, CourseQueryVo courseQueryVo);

    CourseWebVo getBaseCourseInfo(String courseId);
}
