package com.cy.eduservice.client;

import com.cy.commonutils.R;
import com.cy.commonutils.vo.UcenterMemberOrder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodFileDegradeFeignClient implements VodClient{
    @Override
    public R deleteById(String videoId) {
        return R.error().message("time out");
    }

    @Override
    public R deleteByIds(List<String> videoList) {
        return R.error().message("time out");
    }

}
