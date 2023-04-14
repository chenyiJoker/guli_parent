package com.cy.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cy.eduservice.entity.EduCourse;
import com.cy.eduservice.entity.EduCourseDescription;
import com.cy.eduservice.entity.EduTeacher;
import com.cy.eduservice.entity.EduVideo;
import com.cy.eduservice.entity.front.CourseQueryVo;
import com.cy.eduservice.entity.front.CourseWebVo;
import com.cy.eduservice.entity.vo.CourseInfoVo;
import com.cy.eduservice.entity.vo.CoursePublishVo;
import com.cy.eduservice.entity.vo.CourseQuery;
import com.cy.eduservice.mapper.EduCourseMapper;
import com.cy.eduservice.service.EduChapterService;
import com.cy.eduservice.service.EduCourseDescriptionService;
import com.cy.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cy.eduservice.service.EduVideoService;
import com.cy.servicebase.exceptionHandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-04-03
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Autowired
    private EduCourseDescriptionService courseDescriptionService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private EduVideoService videoService;

    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        // 保存课程基本信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);

        int insert = baseMapper.insert(eduCourse);

        if (insert == 0) {
            // 添加失败
            throw new GuliException(20001, "课程信息保存失败");
        }
        //保存课程详情信息
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescription.setId(eduCourse.getId());
        courseDescriptionService.save(eduCourseDescription);
        return eduCourse.getId();
    }

    @Override
    public CourseInfoVo getCourseById(String id) {
        EduCourse eduCourse = baseMapper.selectById(id);
        if(eduCourse == null){
            throw new GuliException(20001, "数据不存在");
        }

        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse, courseInfoVo);

        EduCourseDescription courseDescription = courseDescriptionService.getById(id);

        courseInfoVo.setDescription(courseDescription.getDescription());

        return courseInfoVo;
    }

    @Override
    public void updateCourseById(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int insert = baseMapper.updateById(eduCourse);
        if (insert == 0) {
            // 修改失败
            throw new GuliException(20001, "课程信息修改失败");
        }

        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescription.setId(eduCourse.getId());
        courseDescriptionService.updateById(eduCourseDescription);
    }

    @Override
    public CoursePublishVo getCoursePublish(String id) {
        return baseMapper.getCoursePublishVoById(id);
    }

    @Override
    public void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery) {
        LambdaQueryWrapper<EduCourse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(EduCourse::getGmtCreate);

        if (courseQuery == null) {
            baseMapper.selectPage(pageParam, queryWrapper);
            return;
        }

        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();

        queryWrapper.like(!StringUtils.isEmpty(title), EduCourse::getTitle, title);

        queryWrapper.eq(!StringUtils.isEmpty(teacherId), EduCourse::getTeacherId, teacherId);

        queryWrapper.ge(!StringUtils.isEmpty(subjectParentId), EduCourse::getSubjectParentId, subjectParentId);

        queryWrapper.ge(!StringUtils.isEmpty(subjectId), EduCourse::getSubjectId, subjectId);

        baseMapper.selectPage(pageParam, queryWrapper);
    }

    @Override
    public void deleteById(String id) {
        // 根据id删除所有视频
        videoService.deleteByCourseId(id);

        // 根据id删除所有章节
        chapterService.deleteByCourseId(id);

        // 根据id删除描述
        courseDescriptionService.removeById(id);

        // 删除本身
        baseMapper.deleteById(id);
    }

    @Override
    public Map<String, Object> getTeacherFrontList(Page<EduCourse> pageParam, CourseQueryVo courseQueryVo) {
        LambdaQueryWrapper<EduCourse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!StringUtils.isEmpty(courseQueryVo.getSubjectParentId()), EduCourse::getSubjectParentId, courseQueryVo.getSubjectParentId());
        queryWrapper.eq(!StringUtils.isEmpty(courseQueryVo.getSubjectId()), EduCourse::getSubjectId, courseQueryVo.getSubjectId());
        queryWrapper.orderByDesc(!StringUtils.isEmpty(courseQueryVo.getBuyCountSort()), EduCourse::getBuyCount);
        queryWrapper.orderByDesc(!StringUtils.isEmpty(courseQueryVo.getGmtCreateSort()), EduCourse::getGmtCreate);
        queryWrapper.orderByDesc(!StringUtils.isEmpty(courseQueryVo.getPriceSort()), EduCourse::getPrice);

        baseMapper.selectPage(pageParam, queryWrapper);

        List<EduCourse> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();

        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }
}
