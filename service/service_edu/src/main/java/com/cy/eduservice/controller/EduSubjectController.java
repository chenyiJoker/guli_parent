package com.cy.eduservice.controller;


import com.cy.commonutils.R;
import com.cy.eduservice.entity.subject.OneSuject;
import com.cy.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-04-02
 */
@RestController
@RequestMapping("/eduservice/subject")
//@CrossOrigin
public class EduSubjectController {
    @Autowired
    private EduSubjectService subjectService;

    @PostMapping
    public R saveSubject(MultipartFile file) {
        subjectService.saveSubject(file, subjectService);
        return R.ok();
    }

    @GetMapping
    public R getAllSubject() {
        List<OneSuject> list = subjectService.getAllSubject();
        return R.ok().data("subject", list);
    }
}

