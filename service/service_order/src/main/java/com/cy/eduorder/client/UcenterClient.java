package com.cy.eduorder.client;

import com.cy.commonutils.vo.UcenterMemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("service-ucenter")
public interface UcenterClient {
    //根据课程id查询课程信息
    @PostMapping("/educenter/member/{id}")
    public UcenterMemberOrder getInfo(@PathVariable("id") String id);
}
