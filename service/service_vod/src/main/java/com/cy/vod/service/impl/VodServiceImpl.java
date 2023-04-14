package com.cy.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.cy.commonutils.R;
import com.cy.servicebase.exceptionHandler.GuliException;
import com.cy.vod.service.VodService;
import com.cy.vod.utils.ConstantVideoUtils;
import com.cy.vod.utils.IninObject;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Service
public class VodServiceImpl implements VodService {
    @Override
    public String uploadVideo(MultipartFile file) {
        try {
            String accessKeyId = ConstantVideoUtils.ACCESS_KEY_ID;
            String accessKeySecret = ConstantVideoUtils.ACCESS_KEY_SECRET;

            String fileName = file.getOriginalFilename();
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            InputStream inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret, title, fileName, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            String videoId = response.getVideoId();
            if (!response.isSuccess()) {
                String errorMessage = "阿里云上传错误：" + "code：" + response.getCode() + ", message：" + response.getMessage();

                if(StringUtils.isEmpty(videoId)){
                    throw new GuliException(20001, errorMessage);
                }
            }

            return videoId;
        } catch (Exception e) {
            throw new GuliException(20001, "guli vod 服务上传失败");
        }
    }

    @Override
    public void deleteByIds(List<String> videoList) {
        try {
            DefaultAcsClient client = IninObject.initVodClient(ConstantVideoUtils.ACCESS_KEY_ID, ConstantVideoUtils.ACCESS_KEY_SECRET);

            String ids = String.join(",", videoList);
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(ids);
            client.getAcsResponse(request);

        } catch (Exception e) {
            throw new GuliException(20001, "删除失败");
        }
    }
}
