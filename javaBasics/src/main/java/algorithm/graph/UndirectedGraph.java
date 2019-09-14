package algorithm.graph;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 无向图
 *
 * @author Tang
 */
public class UndirectedGraph {

    /**
     * 顶点的个数
     */
    private int v;

    /**
     * 邻接表
     */
    private LinkedList<Integer>[] adj;


    private UndirectedGraph(int v) {
        this.v = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; ++i) {
            // 链表的每个节点又是链表
            adj[i] = new LinkedList<>();
        }
    }

    /**
     * 添加边
     *
     * @param s 节点1
     * @param t 节点2
     */
    private void addEdge(int s, int t) {
        // 无向图一条边存两次
        adj[s].add(t);
        adj[t].add(s);
    }

    /**
     * 广度优先搜索：Breadth-First-Search
     *
     * @param s 起始顶点
     * @param t 终止顶点
     */
    private void bfs(int s, int t) {
        if (s == t) {
            return;
        }
        // 1.用来记录已经被访问的顶点
        boolean[] visited = new boolean[v];
        visited[s] = true;

        // 2.用来存储已经被访问、但相连的顶点还没有被访问的顶点
        Queue<Integer> queue = new LinkedList<>();
        queue.add(s);

        // 3.用来记录搜索路径
        int[] prev = new int[v];
        for (int i = 0; i < v; ++i) {
            prev[i] = -1;
        }

        // 广度优先遍历
        while (queue.size() != 0) {
            int w = queue.poll();
            // w为当前节点，采用的领接链表法，即可得到【一度】关系节点，w可以到达哪些节点？
            for (int i = 0; i < adj[w].size(); ++i) {
                // 获取当前节点 w 下一层的某个节点 q
                // 这里为了简单实现，节点数据等于节点下标
                int q = adj[w].get(i);
                // int qIndex = q;
                // 当前节点q没有被访问
                if (!visited[q]) {
                    // 记录路径，这个路径是反向存储的。
                    // prev[w] 存储的是，顶点 w 是从哪个前驱顶点遍历过来的。
                    // 比如，我们通过顶点 2 的邻接表访问到顶点 3，那 prev[3] 就等于 2。
                    prev[q] = w;
                    // 找到了最终的节点
                    if (q == t) {
                        print(prev, s, t);
                        return;
                    }
                    visited[q] = true;
                    queue.add(q);
                }
            }
        }
    }

    /**
     * 递归打印 s->t 的路径
     *
     * @param prev 记录的搜索路径
     * @param s    起始顶点
     * @param t    终止顶点
     */
    private void print(int[] prev, int s, int t) {
        if (prev[t] != -1 && t != s) {
            print(prev, s, prev[t]);
        }
        System.out.print(t + " ");
    }

    /**
     * 全局变量或者类成员变量
     * 当我们已经找到终止顶点 t 之后，我们就不再递归地继续查找了
     */
    private boolean found = false;


    /**
     * 深度优先搜索:Depth-First-Search
     *
     * @param s 起始顶点
     * @param t 终止顶点
     */
    public void dfs(int s, int t) {
        found = false;
        // 1.用来记录已经被访问的顶点
        boolean[] visited = new boolean[v];

        // 2.用来记录搜索路径
        int[] prev = new int[v];
        for (int i = 0; i < v; ++i) {
            prev[i] = -1;
        }

        recurDfs(s, t, visited, prev);
        print(prev, s, t);
    }

    /**
     * 递归遍历
     *
     * @param w       起始顶点
     * @param t       终止顶点
     * @param visited 记录已经被访问的顶点
     * @param prev    记录搜索路径
     */
    private void recurDfs(int w, int t, boolean[] visited, int[] prev) {
        if (found) {
            return;
        }
        visited[w] = true;
        if (w == t) {
            found = true;
            return;
        }
        for (int i = 0; i < adj[w].size(); ++i) {
            int q = adj[w].get(i);
            if (!visited[q]) {
                prev[q] = w;
                recurDfs(q, t, visited, prev);
            }
        }
    }

    public static void main(String[] args) {
        UndirectedGraph undirectedGraph = new UndirectedGraph(8);
        undirectedGraph.addEdge(0, 1);
        undirectedGraph.addEdge(0, 3);
        undirectedGraph.addEdge(1, 2);
        undirectedGraph.addEdge(1, 4);
        undirectedGraph.addEdge(2, 5);
        undirectedGraph.addEdge(3, 4);
        undirectedGraph.addEdge(4, 5);
        undirectedGraph.addEdge(4, 6);
        undirectedGraph.addEdge(5, 7);
        undirectedGraph.addEdge(6, 7);
        System.out.println("===================广度优先遍历======================");
        undirectedGraph.bfs(0, 2);
        System.out.println();
        undirectedGraph.bfs(0, 7);

        System.out.println("===================深度优先遍历======================");
        undirectedGraph.dfs(0, 2);
        System.out.println();
        undirectedGraph.dfs(0, 7);

    }

}
