package com.cy.staservice.client;

import com.cy.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-ucenter")
public interface UcenterClient {
    @GetMapping("/educenter/member/{day}")
    public R registerCount(@PathVariable("day") String day);
}
