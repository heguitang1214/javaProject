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
     * 邻接表
     */
    private LinkedList<Integer>[] adj;

    public TopoSortKahn(int v) {
        this.v = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; ++i) {
            adj[i] = new LinkedList<>();
        }
    }


    /**
     * 添加变
     * s 先于 t，边 s->t
     *
     * @param s
     * @param t
     */
    public void addEdge(int s, int t) {
        adj[s].add(t);
    }


    public void topoSortByKahn() {
        // 统计每个顶点的入度
        int[] inDegree = new int[v];
        for (int i = 0; i < v; ++i) {
            for (int j = 0; j < adj[i].size(); ++j) {
                // i->w
                int w = adj[i].get(j);
                inDegree[w]++;
            }
        }
        LinkedList<Integer> queue = new LinkedList<>();
        for (int i = 0; i < v; ++i) {
            if (inDegree[i] == 0) {
                queue.add(i);
            }
        }
        while (!queue.isEmpty()) {
            int i = queue.remove();
            System.out.print("->" + i);
            for (int j = 0; j < adj[i].size(); ++j) {
                int k = adj[i].get(j);
                inDegree[k]--;
                if (inDegree[k] == 0) {
                    queue.add(k);
                }
            }
        }
    }


    public static void main(String[] args) {

        TopoSortKahn topoSortKahn = new TopoSortKahn(10);

    }

}
