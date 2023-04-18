package com.cy.educms.controller;

import com.cy.commonutils.R;
import com.cy.educms.entity.CrmBanner;
import com.cy.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/educms/bannerfront")
//@CrossOrigin
public class BannerFromtController {
    @Autowired
    private CrmBannerService crmBannerService;

    @GetMapping
    public R getAll() {
        List<CrmBanner> list = crmBannerService.getAllBanner();
        return R.ok().data("list", list);
    }
}
