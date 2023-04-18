package com.cy.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cy.commonutils.R;
import com.cy.eduservice.entity.EduTeacher;
import com.cy.eduservice.entity.vo.TeacherQuery;
import com.cy.eduservice.service.EduTeacherService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-03-28
 */
@RestController
@RequestMapping("/eduservice/teacher")
//@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    /**
     * 查询所有
     *
     * @return
     */
    @GetMapping
    public R findAllTeacher() {
        return R.ok().data("items", teacherService.list(null));
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public R removeById(@PathVariable String id) {
        return teacherService.removeById(id) ? R.ok() : R.error();
    }

    /**
     * 分页条件查询
     *
     * @param currentPage
     * @param pageSize
     * @param teacherQuery
     * @return
     */
    @ApiOperation(value = "分页讲师列表")
    @PostMapping("{currentPage}/{pageSize}")
    public R page(
            @ApiParam(name = "currentPage", value = "当前页码", required = true)
            @PathVariable Long currentPage,

            @ApiParam(name = "pageSize", value = "每页记录数", required = true)
            @PathVariable Long pageSize,

            @ApiParam(name = "teacherQuery", value = "查询对象", required = false)
            @RequestBody TeacherQuery teacherQuery) {
        Page<EduTeacher> page = new Page<>(currentPage, pageSize);
        teacherService.pageQuery(page, teacherQuery);

        List<EduTeacher> records = page.getRecords();
        long total = page.getTotal();

        return R.ok().data("total", total).data("rows", records);
    }

    /**
     * 新增讲师
     *
     * @param teacher
     * @return
     */
    @PostMapping
    public R save(@RequestBody EduTeacher teacher) {
        return teacherService.save(teacher)? R.ok() : R.error();
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public R getById(@PathVariable String id) {
        return R.ok().data("item",teacherService.getById(id));
    }

    /**
     * 根据id修改
     * @param teacher
     * @return
     */
    @PutMapping
    public R updateById(@RequestBody EduTeacher teacher) {
        teacherService.updateById(teacher);
        return R.ok();
    }
}

