package com.cy.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cy.commonutils.R;
import com.cy.eduservice.client.VodClient;
import com.cy.eduservice.entity.EduCourse;
import com.cy.eduservice.entity.vo.CourseInfoVo;
import com.cy.eduservice.entity.vo.CoursePublishVo;
import com.cy.eduservice.entity.vo.CourseQuery;
import com.cy.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-04-03
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    @Qualifier("com.cy.eduservice.client.VodClient")
    @Autowired
    private VodClient vodClient;

    @PostMapping("{page}/{limit}")
    public R getCoursePageList(@PathVariable Long page, @PathVariable Long limit, @RequestBody CourseQuery courseQuery) {
        Page<EduCourse> pageParam = new Page<>(page, limit);
        courseService.pageQuery(pageParam, courseQuery);

        List<EduCourse> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return R.ok().data("total", total).data("rows", records);
    }

    @PostMapping
    public R saveCourseInfo(@RequestBody CourseInfoVo courseInfoVo){

        String id = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId", id);
    }

    @GetMapping("{id}")
    public R getCourseById(@PathVariable String id) {
        CourseInfoVo courseInfoVo = courseService.getCourseById(id);
        return R.ok().data("courseInfoVo", courseInfoVo);
    }

    @PutMapping("{id}")
    public R updateCourseById(@RequestBody CourseInfoVo courseInfoVo, @PathVariable String id) {
        courseService.updateCourseById(courseInfoVo);
        return R.ok();
    }
    @GetMapping("getCoursePublish/{id}")
    public R getCoursePublishById(@PathVariable String id) {
        CoursePublishVo coursePublishVo = courseService.getCoursePublish(id);
        return R.ok().data("coursePublishVo", coursePublishVo);
    }

    @PostMapping("{id}")
    public R publishCourse(@PathVariable String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setStatus("Normal");
        eduCourse.setId(id);
        courseService.updateById(eduCourse);
        return R.ok();
    }

    @DeleteMapping("{id}")
    public R deleteById(@PathVariable String id) {
        courseService.deleteById(id);
        return R.ok();
    }

}

