package algorithm.graph;

import java.util.LinkedList;

/**
 * 拓扑排序
 *
 * @author Tang
 */
public class TopoSortDfs {

    /**
     * 顶点的个数
     */
    private int v;

    /**
     * 邻接表
     */
    private LinkedList<Integer>[] adj;

    private TopoSortDfs(int v) {
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
     * DFS 排序
     */
    private void topoSortByDfs() {
        // 先构建逆邻接表，边 s->t 表示，s 依赖于 t，t 先于 s
        LinkedList<Integer>[] inverseAdj = new LinkedList[v];
        // 申请空间
        for (int i = 0; i < v; ++i) {
            inverseAdj[i] = new LinkedList<>();
        }
        // 通过邻接表生成逆邻接表
        for (int i = 0; i < v; ++i) {
            for (int j = 0; j < adj[i].size(); ++j) {
                // i -> ws
                int w = adj[i].get(j);
                // w -> i
                inverseAdj[w].add(i);
            }
        }

        // 记录图中的顶点是否被考察
        boolean[] visited = new boolean[v];
        // 深度优先遍历图
        for (int i = 0; i < v; ++i) {
            if (!visited[i]) {
                visited[i] = true;
                dfs(i, inverseAdj, visited);
            }
        }
    }

    /**
     * DFS深度优先遍历
     *
     * @param vertex     图的顶点
     * @param inverseAdj 逆邻接表
     * @param visited    记录顶点是否被考察的数组
     */
    private void dfs(int vertex, LinkedList<Integer>[] inverseAdj, boolean[] visited) {
        for (int i = 0; i < inverseAdj[vertex].size(); ++i) {
            // 从逆邻接表中获取【当前顶点vertex】依赖的顶点【w】
            int w = inverseAdj[vertex].get(i);
            if (visited[w]) {
                continue;
            }
            visited[w] = true;
            dfs(w, inverseAdj, visited);
        }
        // 递归【归】的时候，打印顶点
        // 先把 vertex 这个顶点可达的所有顶点都打印出来之后，再打印它自己
        System.out.print(" -> " + vertex);
    }


    public static void main(String[] args) {

        TopoSortDfs topoSortKahn = new TopoSortDfs(6);
        topoSortKahn.addEdge(0, 1);
        topoSortKahn.addEdge(1, 4);
        topoSortKahn.addEdge(1, 2);
        topoSortKahn.addEdge(2, 4);
        topoSortKahn.addEdge(2, 3);
        topoSortKahn.addEdge(3, 5);
        topoSortKahn.addEdge(4, 3);
        topoSortKahn.addEdge(4, 5);
        topoSortKahn.topoSortByDfs();
        System.out.println();

        System.out.println("============测试2：多路径=============");

        TopoSortDfs topoSortKahn1 = new TopoSortDfs(4);
        topoSortKahn1.addEdge(0, 2);
        topoSortKahn1.addEdge(0, 3);
        topoSortKahn1.addEdge(1, 2);
        topoSortKahn1.addEdge(2, 3);
        topoSortKahn1.topoSortByDfs();
        // 输出结果为：--> 0 --> 1 --> 2 --> 3
        // 0 没有指向 1，输出是因为使用的是递归输出，依次考察【顶点】数组，递归的寻找当前顶点依赖的数据，
        // 如果发现依赖的数据已经输出，就直接输出自己。如果发现有依赖的数据，就继续递归，输出依赖的数据
        System.out.println();

    }

}
