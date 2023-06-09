package com.cy.vodtest;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import java.util.List;

import static com.cy.vodtest.IninObject.initVodClient;

public class TestVod {
    public static void main(String[] args) throws Exception {
//        String accessKeyId = "keyid";
//        String accessKeySecret = "keysecret";
//        String title = "6 - What If I Want to Move Faster- upload by sdk";
//        String fileName = "D:/6 - What If I Want to Move Faster.mp4";
//        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
//        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
//        request.setPartSize(2 * 1024 * 1024L);
//        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
//        request.setStorageLocation("outin-d75100b9d4e611edaffa00163e00b174.oss-cn-shanghai.aliyuncs.com");
//        request.setApiRegionId("cn-shanghai");
//
//        request.setTaskNum(1);
//        UploadVideoImpl uploader = new UploadVideoImpl();
//        UploadVideoResponse response = uploader.uploadVideo(request);
//        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
//        if (response.isSuccess()) {
//            System.out.print("VideoId=" + response.getVideoId() + "\n");
//        } else {
//            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
//            System.out.print("VideoId=" + response.getVideoId() + "\n");
//            System.out.print("ErrorCode=" + response.getCode() + "\n");
//            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
//        }
        getPlayAuth();
    }


    public static void getPlayAuth() throws Exception {
        DefaultAcsClient client = initVodClient("keyid", "keysecret");

        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

        request.setEndpoint("vod.cn-shanghai.aliyuncs.com");
        request.setVideoId("645fd910d60f71ed80530764a0fd0102");

        response = client.getAcsResponse(request);

        System.out.println("playauth:" + response.getPlayAuth());
    }

    public static void getPlayUrl() throws Exception {
        DefaultAcsClient client = initVodClient("keyid", "keysecret");
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        GetPlayInfoRequest request = new GetPlayInfoRequest();

        request.setEndpoint("vod.cn-beijing.aliyuncs.com");
        request.setVideoId("39ddb150d49171edb7f935a6ecca0102");

        response = client.getAcsResponse(request);

        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        //Base信息
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
    }
}
