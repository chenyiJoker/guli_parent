package com.cy.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cy.commonutils.R;
import com.cy.eduservice.entity.EduCourse;
import com.cy.eduservice.entity.EduTeacher;
import com.cy.eduservice.service.EduCourseService;
import com.cy.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("eduservice/index")
//@CrossOrigin
public class IndexController {
    @Autowired
    private EduTeacherService eduTeacherService;

    @Autowired
    private EduCourseService eduCourseService;

    @GetMapping
    public R getIndex() {
        LambdaQueryWrapper<EduTeacher> teacherQueryWrapper = new LambdaQueryWrapper<>();
        teacherQueryWrapper.orderByDesc(EduTeacher::getId);
        teacherQueryWrapper.last("limit 4");
        List<EduTeacher> teacherList = eduTeacherService.list(teacherQueryWrapper);

        LambdaQueryWrapper<EduCourse> courseQueryWrapper = new LambdaQueryWrapper<>();
        courseQueryWrapper.orderByDesc(EduCourse::getId);
        courseQueryWrapper.last("limit 8");
        List<EduCourse> courseList = eduCourseService.list(courseQueryWrapper);

        return R.ok().data("teacherList", teacherList).data("courseList", courseList);
    }
}
