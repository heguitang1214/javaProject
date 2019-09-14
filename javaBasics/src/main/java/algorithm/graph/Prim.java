package algorithm.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 最小生成树
 *
 * @author heguitang
 */
public class Prim {

    /**
     * 代表节点之间的距离为无穷大
     */
    private final static int INF = Integer.MAX_VALUE;

    /**
     * 最小生成树
     *
     * @param matrix 图
     * @return 最小生成树的路径
     */
    private static int[] prim(int[][] matrix) {
        // 已触达顶点集合
        List<Integer> reachedVertexList = new ArrayList<>();

        // 选择顶点0为初始顶点，放入已触达顶点集合中
        reachedVertexList.add(0);
        // 创建最小生成树数组，首元素设为-1，
        // 最小生成树需要到达每个节点，所以数组长度就为图的长度
        int[] parents = new int[matrix.length];
        parents[0] = -1;

        //边的权重
        int weight;
        //源顶点下标
        int fromIndex = 0;
        //目标顶点下标
        int toIndex = 0;

        // 已触达顶点集合中的数据未满
        while (reachedVertexList.size() < matrix.length) {
            weight = INF;
            // 在已触达的顶点中，寻找到达新顶点的最短边
            // 循环已触达集合，
            for (Integer vertexIndex : reachedVertexList) {
                for (int i = 0; i < matrix.length; i++) {
                    // 如果已触达集合中，不包含元素i(这里数组下标和数组中的元素是对应的)
                    // 这里的 i 表示的是图中的顶点
                    if (!reachedVertexList.contains(i)) {
                        // 图中的元素节点小于边的权重，+ 元素到已触达顶点集合
                        if (matrix[vertexIndex][i] < weight) {
                            fromIndex = vertexIndex;
                            toIndex = i;
                            // 权重重新赋值，是为了比较找到最短的边
                            weight = matrix[vertexIndex][i];
                        }
                    }
                }
            }
            //确定了权值最小的目标顶点，放入已触达顶点集合
            reachedVertexList.add(toIndex);
            //放入最小生成树的数组
            parents[toIndex] = fromIndex;
        }

        return parents;
    }

    public static void main(String[] args) {
        int[][] matrix = new int[][]{
                {0, 4, 3, INF, INF},
                {4, 0, 8, 7, INF},
                {3, 8, 0, INF, 1},
                {INF, 7, INF, 0, 9},
                {INF, INF, 1, 9, 0},
        };
        int[] parents = prim(matrix);
        System.out.println(Arrays.toString(parents));
    }
}
