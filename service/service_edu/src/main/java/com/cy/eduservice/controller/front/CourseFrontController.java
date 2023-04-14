package com.cy.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cy.commonutils.JwtUtils;
import com.cy.commonutils.R;
import com.cy.commonutils.vo.CourseWebVoOrder;
import com.cy.eduservice.client.OrderClient;
import com.cy.eduservice.entity.EduCourse;
import com.cy.eduservice.entity.chapter.ChapterVo;
import com.cy.eduservice.entity.front.CourseQueryVo;
import com.cy.eduservice.entity.front.CourseWebVo;
import com.cy.eduservice.service.EduChapterService;
import com.cy.eduservice.service.EduCourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/coursefront")
@CrossOrigin
public class CourseFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;
    @Autowired
    private OrderClient orderClient;


    @PostMapping("{page}/{limit}")
    public R getTeacherFrontList(@PathVariable long page, @PathVariable long limit, @RequestBody CourseQueryVo courseQueryVo) {
        Page<EduCourse> pageParam = new Page<>(page, limit);
        Map<String, Object> map = courseService.getTeacherFrontList(pageParam, courseQueryVo);
        return R.ok().data(map);
    }

    // 根据ID查询课程
    @GetMapping("{courseId}")
    public R getById(@PathVariable String courseId, HttpServletRequest request) {
        //查询课程信息和讲师信息
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);

        //查询当前课程的章节信息
        List<ChapterVo> chapterVoList = chapterService.getChapterVideo(courseId);

        // 远程调用，判断课程是否被购买
        boolean isBuy = orderClient.isBuyCourse(JwtUtils.getMemberIdByJwtToken(request), courseId);

        return R.ok().data("course", courseWebVo).data("chapterVoList", chapterVoList).data("isBuy", isBuy);
    }
    // 根据课程id查询课程信息
    @PostMapping("{courseId}")
    public CourseWebVoOrder getCourseInfo(@PathVariable String courseId) {
        CourseWebVo coureseInfo = courseService.getBaseCourseInfo(courseId);
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(coureseInfo, courseWebVoOrder);
        return courseWebVoOrder;
    }
}