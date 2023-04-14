package com.cy.eduservice.client;

import com.cy.commonutils.R;
import com.cy.commonutils.vo.UcenterMemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@FeignClient(name = "service-vod", fallback = VodFileDegradeFeignClient.class)
public interface VodClient {
    @DeleteMapping(value = "/eduvod/video/{videoId}")
    public R deleteById(@PathVariable("videoId") String videoId);

    @DeleteMapping(value = "/eduvod/video")
    public R deleteByIds(@RequestParam("videoList") List<String> videoList);

    //根据用户id获取用户信息
    @GetMapping("/educenter/member/{memberId}")
    public UcenterMemberOrder getUcenterPay(@PathVariable("memberId") String memberId);
}
