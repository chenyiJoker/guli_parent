package com.cy.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cy.commonutils.R;
import com.cy.eduservice.entity.EduCourse;
import com.cy.eduservice.entity.EduTeacher;
import com.cy.eduservice.service.EduCourseService;
import com.cy.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/teacherfront")
//@CrossOrigin
public class TeacherFrontController {
    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;
    @GetMapping("{page}/{limit}")
    public R getTeacherFrontList(@PathVariable long page, @PathVariable long limit) {
        Page<EduTeacher> pageParam = new Page<>(page, limit);
        Map<String, Object> map = teacherService.getTeacherFrontList(pageParam);
        return R.ok().data(map);
    }
    @GetMapping("{teacherId}")
    public R getTeacherFrontInfo(@PathVariable String teacherId) {
        // 查询讲师信息
        EduTeacher eduTeacher = teacherService.getById(teacherId);

        // 根据讲师id查询这个讲师的课程列表
        LambdaQueryWrapper<EduCourse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EduCourse::getTeacherId, teacherId);
        List<EduCourse> courseList = courseService.list(queryWrapper);
        return R.ok().data("teacher", eduTeacher).data("courseList", courseList);
    }
}
