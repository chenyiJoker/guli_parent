package com.cy.educms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cy.commonutils.R;
import com.cy.educms.entity.CrmBanner;
import com.cy.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-04-08
 */
@RestController
@RequestMapping("/educms/banneradmin")
//@CrossOrigin
public class BannerAdminController {
    @Autowired
    private CrmBannerService crmBannerService;

    @GetMapping("{page}/{limit}")
    public R getPage(@PathVariable long page, @PathVariable long limit) {
        Page<CrmBanner> pageParam = new Page<>(page, limit);
        crmBannerService.page(pageParam, null);

        return R.ok().data("items", pageParam.getRecords()).data("total", pageParam.getTotal());
    }

    @GetMapping("{id}")
    public R getById(@PathVariable String id) {
        return R.ok().data("item", crmBannerService.getById(id));
    }

    @PostMapping
    public R save(@RequestBody CrmBanner crmBanner) {
        crmBannerService.save(crmBanner);
        return R.ok();
    }

    @PutMapping
    public R updateById(@RequestBody CrmBanner crmBanner) {
        crmBannerService.updateById(crmBanner);
        return R.ok();
    }

    @DeleteMapping("{id}")
    public R deleteById(@PathVariable String id) {
        crmBannerService.removeById(id);
        return R.ok();
    }
}

