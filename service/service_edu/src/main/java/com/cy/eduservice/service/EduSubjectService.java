package com.cy.eduservice.service;

import com.cy.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cy.eduservice.entity.subject.OneSuject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-04-02
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file, EduSubjectService subjectService);

    List<OneSuject> getAllSubject();
}
