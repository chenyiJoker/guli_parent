package com.cy.eduservice.controller;


import com.cy.commonutils.R;
import com.cy.eduservice.entity.EduVideo;
import com.cy.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-04-03
 */
@RestController
@RequestMapping("/eduservice/video")
//@CrossOrigin
public class EduVideoController {
    @Autowired
    private EduVideoService eduVideoService;


    // 新增小节
    @PostMapping
    public R save(@RequestBody EduVideo eduVideo) {
        eduVideoService.save(eduVideo);
        return R.ok();
    }

    @GetMapping("{id}")
    public R getById(@PathVariable String id) {
        return R.ok().data("video", eduVideoService.getById(id));
    }

    @PutMapping
    public R updateById(@RequestBody EduVideo eduVideo) {
        return R.ok().data("video", eduVideoService.updateById(eduVideo));
    }

    @DeleteMapping("{id}")
    public R deleteById(@PathVariable String id) {
        eduVideoService.removeById(id);
        return R.ok();
    }
}

