package com.cy.staservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cy.staservice.client.UcenterClient;
import com.cy.staservice.entity.StatisticsDaily;
import com.cy.staservice.mapper.StatisticsDailyMapper;
import com.cy.staservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-04-11
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;
    @Override
    public void registerCount(String day) {
        LambdaQueryWrapper<StatisticsDaily> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StatisticsDaily::getDateCalculated, day);
        baseMapper.delete(queryWrapper);

        // 获取统计信息
        Integer countRegister = (Integer) ucenterClient.registerCount(day).getData().get("countRegister");
        Integer loginNum = RandomUtils.nextInt(100, 200);//TODO
        Integer videoViewNum = RandomUtils.nextInt(100, 200);//TODO
        Integer courseNum = RandomUtils.nextInt(100, 200);//TODO

        // 创建统计对象
        StatisticsDaily sta = new StatisticsDaily();
        sta.setRegisterNum(countRegister);
        sta.setLoginNum(loginNum);
        sta.setVideoViewNum(videoViewNum);
        sta.setCourseNum(courseNum);
        sta.setDateCalculated(day);

        baseMapper.insert(sta);
    }

    @Override
    public Map<String, Object> getChartData(String begin, String end, String type) {
        QueryWrapper<StatisticsDaily> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("date_calculated", begin, end);
        queryWrapper.select("date_calculated", type);

        List<StatisticsDaily> dayList = baseMapper.selectList(queryWrapper);


        List<Integer> numList = new ArrayList<>();
        List<String> dateList = new ArrayList<>();

        for (StatisticsDaily daily : dayList) {
            dateList.add(daily.getDateCalculated());
            switch (type) {
                case "register_num":
                    numList.add(daily.getRegisterNum());
                    break;
                case "login_num":
                    numList.add(daily.getLoginNum());
                    break;
                case "video_view_num":
                    numList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    numList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("numList", numList);
        map.put("dateList", dateList);

        return map;
    }
}
