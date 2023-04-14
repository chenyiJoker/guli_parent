package com.cy.eduservice.mapper;

import com.cy.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cy.eduservice.entity.front.CourseWebVo;
import com.cy.eduservice.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2023-04-03
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    CoursePublishVo getCoursePublishVoById(String id);

    CourseWebVo getBaseCourseInfo(String courseId);

}
