package com.cy.eduorder.client;

import com.cy.commonutils.vo.CourseWebVoOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("service-edu")
public interface EduClient {
    //根据课程id查询课程信息
    @PostMapping("/eduservice/coursefront/{courseId}")
    public CourseWebVoOrder getCourseInfo(@PathVariable("courseId") String courseId);
}
