package baseDemo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author he_guitang
 * @version [1.0 , 2018/8/22]
 *          组合集合内中的数据,获取到每一个组合
 *          获取集合内满足相加为50的数据
 */
public class DataCombination {

    public static void main(String[] args) {
        Distribution info40 = new Distribution(40);
        Distribution info8 = new Distribution(8);
        Distribution info9 = new Distribution(9);
        Distribution info58 = new Distribution(58);
        Distribution info1 = new Distribution(1);
        Distribution info25 = new Distribution(25);
        List<Distribution> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            list.add(info40);
            list.add(info8);
            list.add(info9);
            list.add(info8);
            list.add(info58);
            list.add(info1);
            list.add(info25);
            list.add(info1);
        }

        Collections.sort(list);
        long start = System.currentTimeMillis();
        while(list.size() > 0){
            List<Distribution> res = new ArrayList<>();

//            distributionMethod(list.get(0), list, res);
//            distributionMethod1(list.get(0), 0, 50, list, res);
            distributionMethod2(list.get(0), 50, list, res);
            List<Integer> list1 = res.stream().map(Distribution::getNumber).collect(Collectors.toList());
            System.out.println(list1);
        }
        System.out.println("耗时:" + (System.currentTimeMillis() - start));
    }

    /**
     * 返回最佳组合
     */
    private static void distributionMethod(Distribution max, List<Distribution> resource, List<Distribution> result){
        result.add(max);
        resource.remove(max);
        if (max.getNumber() >= 50) return;

        //返回集合中还差多少数据
        Integer sum = result.stream().mapToInt(Distribution::getNumber).sum();
        Integer difference = 50 - sum;

        if (difference == 0) return;
        if (resource.size() == 0) return;

        List<Distribution> infos = resource.stream().filter(e -> e.getNumber() <= difference).collect(Collectors.toList());
        if (infos == null || infos.size() == 0) return;
        distributionMethod(infos.get(0), resource, result);
    }


    /**
     * 返回最佳组合
     */
    private static void distributionMethod1(Distribution max, Integer sum, Integer expectedNumber,
                                            List<Distribution> resource, List<Distribution> result){
        result.add(max);
        sum += max.getNumber();
        resource.remove(max);
        if (max.getNumber() >= expectedNumber) return;
        //返回集合中还差多少数据
        Integer difference = expectedNumber - sum;

        if (difference == 0) return;
        if (resource.size() == 0) return;

        List<Distribution> infos = resource.stream().filter(e -> e.getNumber() <= difference).collect(Collectors.toList());
        if (infos == null || infos.size() == 0) return;
        distributionMethod1(infos.get(0), sum, expectedNumber, resource, result);
    }

    /**
     * 返回最佳组合
     */
    private static void distributionMethod2(Distribution max, Integer expectedNumber,
                                            List<Distribution> resource, List<Distribution> result){
        result.add(max);
        resource.remove(max);
        if (max.getNumber() >= expectedNumber) return;
        //返回集合中还差多少数据
        Integer difference = expectedNumber - max.getNumber();
        if (difference == 0) return;
        if (resource.size() == 0) return;
        List<Distribution> infos = resource.stream().filter(e -> e.getNumber() <= difference).collect(Collectors.toList());
        if (infos == null || infos.size() == 0) return;
        distributionMethod2(infos.get(0), difference, resource, result);
    }


    /**
     * 分配数据实体
     */
    static class Distribution implements Comparable<Distribution> {
        private String purchaseNo;//其他的数据
        private Integer number;

        Distribution(Integer number) {
            this.number = number;
        }

        public String getPurchaseNo() {
            return purchaseNo;
        }

        public void setPurchaseNo(String purchaseNo) {
            this.purchaseNo = purchaseNo;
        }

        public Integer getNumber() {
            return number;
        }

        public void setNumber(Integer number) {
            this.number = number;
        }

        @Override
        public int compareTo(Distribution o) {
            return o.getNumber().compareTo(this.getNumber());
        }
    }


}
