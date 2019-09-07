package algorithm.sort.n;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

/**
 * 桶排序
 *
 * @author Tang
 */
public class BucketSort {

    /**
     * 桶排序
     *
     * @param array 数组
     */
    private static double[] bucketSort(double[] array) {
        // 1.得到数列的最大值与最小值，并计算出差值d
        double max = array[0];
        double min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
            if (array[i] < min) {
                min = array[i];
            }
        }
        double d = max - min;

        // 2.初始化桶
        int bucketNumber = array.length;
        ArrayList<LinkedList<Double>> bucketList = new ArrayList<>(bucketNumber);
        for (int i = 0; i < bucketNumber; i++) {
            bucketList.add(new LinkedList<>());
        }

        // 3.遍历原始数组，将每个元素放入桶中
        for (int i = 0; i < array.length; i++) {
            // 区间跨度 = (最大值 - 最小值) / (桶的数量 - 1)
            int num = (int) ((array[i] - min) * (bucketNumber - 1) / d);
            bucketList.get(num).add(array[i]);
        }

        // 4.对每个桶内部进行排序
        for (int i = 0; i < bucketList.size(); i++) {
            // JDK底层采用了归并排序或者归并的优化版本
            Collections.sort(bucketList.get(i));
        }

        // 5.输出全部元素
        double[] sortedArray = new double[array.length];
        int index = 0;
        for (LinkedList<Double> list : bucketList) {
            for (Double elemet : list) {
                sortedArray[index] = elemet;
                index++;
            }
        }

        return sortedArray;
    }


    public static void main(String[] args) {
        double[] array = new double[]{4.12, 6.412, 0.0023, 3.0, 2.123, 8.122, 4.12, 10.09};
        double[] sortedArray = bucketSort(array);
        System.out.println(Arrays.toString(sortedArray));
    }


}
