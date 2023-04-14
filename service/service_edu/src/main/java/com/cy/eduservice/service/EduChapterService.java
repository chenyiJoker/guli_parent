package com.cy.eduservice.service;

import com.cy.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cy.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-04-03
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideo(String id);

    boolean deleteById(String id);

    void deleteByCourseId(String id);
}
