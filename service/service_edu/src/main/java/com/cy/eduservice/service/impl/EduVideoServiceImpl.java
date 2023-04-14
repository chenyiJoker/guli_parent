package com.cy.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cy.commonutils.R;
import com.cy.eduservice.client.VodClient;
import com.cy.eduservice.entity.EduVideo;
import com.cy.eduservice.mapper.EduVideoMapper;
import com.cy.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cy.servicebase.exceptionHandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-04-03
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Qualifier("com.cy.eduservice.client.VodClient")
    @Autowired
    private VodClient vodClient;

    @Override
    public void deleteByCourseId(String id) {
        // 根据课程id查询所有视频列表
        LambdaQueryWrapper<EduVideo> queryWrapperVideo = new LambdaQueryWrapper<>();
        queryWrapperVideo.eq(EduVideo::getCourseId, id);
        queryWrapperVideo.select(EduVideo::getVideoSourceId);
        List<EduVideo> eduVideoList = baseMapper.selectList(queryWrapperVideo);

        // 得到所有视频列表的云端原始视频id
        List<String> videoSourceIdList = eduVideoList.stream().map(EduVideo::getVideoSourceId).collect(Collectors.toList());

        //调用vod服务删除远程视频
        if(videoSourceIdList.size() > 0){
            vodClient.deleteByIds(videoSourceIdList);
        }

        LambdaQueryWrapper<EduVideo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EduVideo::getCourseId, id);
        baseMapper.delete(queryWrapper);
    }

    @Override
    public void removeById(String id) {
        //查询云端视频id
        EduVideo eduvideo = baseMapper.selectById(id);
        String videoSourceId = eduvideo.getVideoSourceId();
        //删除视频资源
        if(!StringUtils.isEmpty(videoSourceId)){
            R result = vodClient.deleteById(videoSourceId);
            if (result.getCode() == 20001) {
                throw new GuliException(20001, "熔断");
            }
        }
        baseMapper.deleteById(id);
    }
}
