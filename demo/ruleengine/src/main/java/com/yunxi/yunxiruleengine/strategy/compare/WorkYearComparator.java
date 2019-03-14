package com.yunxi.yunxiruleengine.strategy.compare;

import java.util.Comparator;

/**
 * @Author: 无双老师【云析学院】
 * @Date: 2019-02-17
 * @Description:
 */
public class WorkYearComparator implements Comparator<Resume> {
    @Override
    public int compare(Resume o1, Resume o2) {
        return o1.getWorkYears() - o2.getWorkYears();
    }
}
