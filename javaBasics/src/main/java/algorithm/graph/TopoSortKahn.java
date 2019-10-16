package algorithm.graph;

import java.util.LinkedList;

/**
 * 拓扑排序
 *
 * @author Tang
 */
public class TopoSortKahn {

    /**
     * 顶点的个数
     */
    private int v;

    /**
     * 邻接表，是一个数组，数组中的每个元素是链表 LinkedList<Integer>
     */
    private LinkedList<Integer>[] adj;

    private TopoSortKahn(int v) {
        this.v = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; ++i) {
            adj[i] = new LinkedList<>();
        }
    }

    /**
     * 添加边
     * s 先于 t，边 s->t
     *
     * @param s 顶点1
     * @param t 顶点2
     */
    public void addEdge(int s, int t) {
        adj[s].add(t);
    }


    /**
     * kahn进行拓扑排序
     */
    private void topoSortByKahn() {
        // 统计每个顶点的入度，邻接表统计节点的入度
        int[] inDegree = new int[v];
        for (int i = 0; i < v; ++i) {
            for (int j = 0; j < adj[i].size(); ++j) {
                // i -> w
                int w = adj[i].get(j);
                inDegree[w]++;
            }
        }
        // 将入度为0的元素，放入到队列中
        LinkedList<Integer> queue = new LinkedList<>();
        for (int i = 0; i < v; ++i) {
            if (inDegree[i] == 0) {
                queue.add(i);
            }
        }

        int outputNum = 0;
        // 从队列中移除入度为0的元素
        while (!queue.isEmpty()) {
            int i = queue.remove();
            System.out.print(" --> " + i);
            outputNum++;
            for (int j = 0; j < adj[i].size(); ++j) {
                int k = adj[i].get(j);
                inDegree[k]--;
                // 入度为0后，再添加到队列中，先进先出
                if (inDegree[k] == 0) {
                    queue.add(k);
                }
            }
        }
        System.out.println();

        // 判断有环无环，
        if (outputNum < v) {
            System.out.println("图中有环");
        } else {
            System.out.println("图中无环");
        }
    }


    public static void main(String[] args) {

        TopoSortKahn topoSortKahn = new TopoSortKahn(6);
        topoSortKahn.addEdge(0, 1);
        topoSortKahn.addEdge(1, 4);
        topoSortKahn.addEdge(1, 2);
        topoSortKahn.addEdge(2, 4);
        topoSortKahn.addEdge(2, 3);
        topoSortKahn.addEdge(3, 5);
        topoSortKahn.addEdge(4, 3);
        topoSortKahn.addEdge(4, 5);
        topoSortKahn.topoSortByKahn();

        System.out.println("============测试2：多路径=============");

        TopoSortKahn topoSortKahn1 = new TopoSortKahn(4);
        topoSortKahn1.addEdge(0, 2);
        topoSortKahn1.addEdge(0, 3);
        topoSortKahn1.addEdge(1, 2);
        topoSortKahn1.addEdge(2, 3);
        topoSortKahn1.topoSortByKahn();
        // 输出结果为：--> 0 --> 1 --> 2 --> 3
        // 0 没有指向 1，输出是因为使用的是【队列】，保存的是入度为0的元素

        System.out.println("============测试3：环检测=============");
        TopoSortKahn topoSortKahn2 = new TopoSortKahn(4);
        topoSortKahn2.addEdge(0, 2);
        topoSortKahn2.addEdge(1, 3);
        topoSortKahn2.addEdge(2, 1);
        topoSortKahn2.addEdge(3, 2);
        topoSortKahn2.topoSortByKahn();
        System.out.println();

    }

}
