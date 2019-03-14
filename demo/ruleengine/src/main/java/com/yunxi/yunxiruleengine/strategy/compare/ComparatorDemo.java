package com.yunxi.yunxiruleengine.strategy.compare;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author: 无双老师【云析学院】
 * @Date: 2019-02-17
 * @Description: 策略模式之比较器排序场景
 */
public class ComparatorDemo {
    public static void main(String[] args) {
        List<Resume> resumeList = init();
        System.out.println("--------------------------ID比较器--------------------------");
        Collections.sort(resumeList, new IdComparator());
        print(resumeList);
        System.out.println("--------------------------年龄比较器--------------------------");
        Collections.sort(resumeList, new AgeComparator());
        print(resumeList);
        System.out.println("--------------------------工资比较器--------------------------");
        Collections.sort(resumeList, new SalaryComparator());
        print(resumeList);
        System.out.println("--------------------------工作年限比较器--------------------------");
        Collections.sort(resumeList, new WorkYearComparator());
        print(resumeList);
    }

    public static void print(List<Resume> resumeList) {
        for (Resume resume : resumeList) {
            System.out.println(resume);
        }
    }

    private static List<Resume> init() {
        Resume resume1 = new Resume(10, "赵三", 25, 16000, 2);
        Resume resume2 = new Resume(12, "钱四", 24, 16000, 1);
        Resume resume3 = new Resume(1, "孙五", 22, 8000, 0);
        Resume resume4 = new Resume(13, "李六", 26, 20000, 4);
        Resume resume5 = new Resume(20, "周七", 30, 30000, 8);
        Resume resume6 = new Resume(8, "吴八", 33, 35000, 10);
        Resume resume7 = new Resume(33, "郑九", 22, 9000, 3);
        Resume resume8 = new Resume(21, "王十", 40, 50000, 15);
        List<Resume> resumeList = new ArrayList<Resume>();
        resumeList.add(resume1);
        resumeList.add(resume2);
        resumeList.add(resume3);
        resumeList.add(resume4);
        resumeList.add(resume5);
        resumeList.add(resume6);
        resumeList.add(resume7);
        resumeList.add(resume8);
        return resumeList;
    }
}
