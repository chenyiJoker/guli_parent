package com.cy.eduservice.service;

import com.cy.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-04-03
 */
public interface EduVideoService extends IService<EduVideo> {

    void deleteByCourseId(String id);

    void removeById(String id);
}
