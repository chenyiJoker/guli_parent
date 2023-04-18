package com.cy.eduservice.controller;


import com.cy.commonutils.R;
import com.cy.eduservice.entity.EduChapter;
import com.cy.eduservice.entity.chapter.ChapterVo;
import com.cy.eduservice.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/eduservice/chapter")
//@CrossOrigin
public class EduChapterController {
    @Autowired
    private EduChapterService chapterService;

    @GetMapping("getChapterVideo/{id}")
    public R getChapterVideo(@PathVariable String id) {
        List<ChapterVo> list = chapterService.getChapterVideo(id);
        return R.ok().data("items", list);
    }

    // 新增章节
    @PostMapping
    public R save(@RequestBody EduChapter educhapter) {
        chapterService.save(educhapter);
        return R.ok();
    }
    // 根据ID查询章节
    @GetMapping("{id}")
    public R getById(@PathVariable String id) {
        return R.ok().data("chapter", chapterService.getById(id));
    }

    // 根据ID修改章节
    @PutMapping
    public R update(@RequestBody EduChapter eduChapter) {
        return R.ok().data("chapter", chapterService.updateById(eduChapter));
    }

    // 根据ID删除章节
    @DeleteMapping("{id}")
    public R delete(@PathVariable String id) {
        if (chapterService.deleteById(id)) {
            return R.ok();
        } else {
            return R.error().message("删除失败");
        }
    }

}

