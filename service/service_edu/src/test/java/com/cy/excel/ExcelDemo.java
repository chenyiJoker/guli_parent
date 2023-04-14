package com.cy.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class ExcelDemo {
    public static void main(String[] args) {
//        String fileName = "D://write.xlsx";
//        EasyExcel.write(fileName, DataDemo.class).sheet("学生列表").doWrite(data());
        String fileName = "D://write.xlsx";
        EasyExcel.read(fileName, DataDemo.class,new ExcelListener()).sheet().doRead();
    }
    //循环设置要添加的数据，最终封装到list集合中
    private static List<DataDemo> data() {
        List<DataDemo> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DataDemo data = new DataDemo();
            data.setSno(i);
            data.setSname("张三"+i);
            list.add(data);
        }
        return list;
    }
}
