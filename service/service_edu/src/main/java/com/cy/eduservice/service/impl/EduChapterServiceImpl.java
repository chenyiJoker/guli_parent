package com.cy.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cy.eduservice.entity.EduChapter;
import com.cy.eduservice.entity.EduVideo;
import com.cy.eduservice.entity.chapter.ChapterVo;
import com.cy.eduservice.entity.chapter.VideoVo;
import com.cy.eduservice.mapper.EduChapterMapper;
import com.cy.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cy.eduservice.service.EduVideoService;
import com.cy.servicebase.exceptionHandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-04-03
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    private EduVideoService videoService;

    @Override
    public List<ChapterVo> getChapterVideo(String id) {
        List<ChapterVo> finalChapterList = new ArrayList<>();

        LambdaQueryWrapper<EduChapter> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EduChapter::getCourseId, id);
        List<EduChapter> chapterList = baseMapper.selectList(queryWrapper);

        LambdaQueryWrapper<EduVideo> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(EduVideo::getCourseId, id);
        List<EduVideo> videoList = videoService.list(queryWrapper1);

        // 封装数据
        for (EduChapter eduChapter : chapterList) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);

            finalChapterList.add(chapterVo);

            List<VideoVo> finalVideoList = new ArrayList<>();

            for (EduVideo eduVideo : videoList) {
                if (eduChapter.getId().equals(eduVideo.getChapterId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    finalVideoList.add(videoVo);
                }
            }
            chapterVo.setChildren(finalVideoList);
        }
        return finalChapterList;
    }

    @Override
    public boolean deleteById(String id) {
        LambdaQueryWrapper<EduVideo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EduVideo::getChapterId, id);
        if (videoService.count(queryWrapper) > 0) {
            throw new GuliException(20001, "该分章节下存在视频课程，请先删除视频课程");
        }
        int result = baseMapper.deleteById(id);

        return result > 0;
    }

    @Override
    public void deleteByCourseId(String id) {
        LambdaQueryWrapper<EduChapter> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EduChapter::getCourseId, id);
        baseMapper.delete(queryWrapper);
    }
}
