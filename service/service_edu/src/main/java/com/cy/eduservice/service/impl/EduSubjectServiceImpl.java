package com.cy.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cy.eduservice.entity.EduSubject;
import com.cy.eduservice.entity.excel.SubjectData;
import com.cy.eduservice.entity.subject.OneSuject;
import com.cy.eduservice.entity.subject.TwoSubject;
import com.cy.eduservice.listener.SubjectListener;
import com.cy.eduservice.mapper.EduSubjectMapper;
import com.cy.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-04-02
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file, EduSubjectService subjectService) {
        try {
            EasyExcel.read(file.getInputStream(), SubjectData.class, new SubjectListener(subjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSuject> getAllSubject() {
        List<OneSuject> finalSubjectList = new ArrayList<>();

        // 获取一级分类数据记录
        LambdaQueryWrapper<EduSubject> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EduSubject::getParentId, "0");

        List<EduSubject> oneSubjectList = baseMapper.selectList(queryWrapper);

        //获取二级分类数据记录
        LambdaQueryWrapper<EduSubject> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.ne(EduSubject::getId, "0");

        List<EduSubject> twoSubjetList = baseMapper.selectList(queryWrapper1);

        // 封装一级分类数据
        for (EduSubject eduSubject : oneSubjectList) {
            OneSuject oneSuject = new OneSuject();
            BeanUtils.copyProperties(eduSubject, oneSuject);

            finalSubjectList.add(oneSuject);

            List<TwoSubject> finalTwoSubjectList = new ArrayList<>();

            // 封装二级分类数据
            for (EduSubject subject : twoSubjetList) {
                if (eduSubject.getId().equals(subject.getParentId())) {
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(subject, twoSubject);
                    finalTwoSubjectList.add(twoSubject);
                }
            }
            oneSuject.setChildren(finalTwoSubjectList);
        }
        return finalSubjectList;
    }
}
