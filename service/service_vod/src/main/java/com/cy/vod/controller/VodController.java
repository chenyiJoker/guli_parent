package com.cy.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.cy.commonutils.R;
import com.cy.servicebase.exceptionHandler.GuliException;
import com.cy.vod.service.VodService;
import com.cy.vod.utils.ConstantVideoUtils;
import com.cy.vod.utils.IninObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    @PostMapping
    public R uploadVideo(MultipartFile file) {
        String videoId = vodService.uploadVideo(file);
        return R.ok().data("videoId", videoId);
    }

    @DeleteMapping("{id}")
    public R deleteById(@PathVariable String id) {
        try {
            DefaultAcsClient client = IninObject.initVodClient(ConstantVideoUtils.ACCESS_KEY_ID, ConstantVideoUtils.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(id);
            client.getAcsResponse(request);

            return R.ok();
        } catch (Exception e) {
            throw new GuliException(20001, "删除失败");
        }
    }

    @DeleteMapping
    public R deleteByIds(@RequestParam("videoList") List<String> videoList) {
        vodService.deleteByIds(videoList);
        return R.ok();
    }

    @GetMapping("{id}")
    public R getPlayAuth(@PathVariable String id) {
        try {
            // 初始化
            DefaultAcsClient client = IninObject.initVodClient(ConstantVideoUtils.ACCESS_KEY_ID, ConstantVideoUtils.ACCESS_KEY_SECRET);

            // 请求
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setEndpoint("vod.cn-shanghai.aliyuncs.com");
            request.setVideoId(id);
            // 响应
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);

            // 得到播放凭证
            String playAuth = response.getPlayAuth();

            //返回结果
            return R.ok().data("playAuth", playAuth);
        } catch (Exception e) {
            throw new GuliException(20001, "获取凭证失败");
        }
    }
}
