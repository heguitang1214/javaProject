package algorithm.graph;

import java.util.LinkedList;

/**
 * Dijkstra最短路径算法：带权图的最短路径版本2
 *
 * @author Tang
 */
public class DijkstraV2 {

    /**
     * 有向有权图的邻接表表示:邻接表
     */
    private LinkedList<Edge>[] adj;

    /**
     * 顶点个数
     */
    private int v;

    public DijkstraV2(int v) {
        this.v = v;
        this.adj = new LinkedList[v];
        for (int i = 0; i < v; ++i) {
            this.adj[i] = new LinkedList<>();
        }
    }

    /**
     * 添加一条边
     *
     * @param s
     * @param t
     * @param w
     */
    public void addEdge(int s, int t, int w) {
        this.adj[s].add(new Edge(s, t, w));
    }

    /**
     * 从顶点 s 到顶点 t 的最短路径
     *
     * @param s 起始顶点
     * @param t 目标顶点
     */
    public void dijkstra(int s, int t) {
        // 用来还原最短路径
        int[] predecessor = new int[this.v];
        Vertex[] vertexes = new Vertex[this.v];
        for (int i = 0; i < this.v; ++i) {
            vertexes[i] = new Vertex(i, Integer.MAX_VALUE);
        }

        // 小顶堆
        PriorityQueue queue = new PriorityQueue(this.v);
        // 标记是否进入过队列
        boolean[] inqueue = new boolean[this.v];
        vertexes[s].dist = 0;
        queue.add(vertexes[s]);
        inqueue[s] = true;
        while (!queue.isEmpty()) {
            // 取堆顶元素并删除
            Vertex minVertex = queue.poll();
            // 最短路径产生了
            if (minVertex.id == t) {
                break;
            }
            for (int i = 0; i < adj[minVertex.id].size(); ++i) {
                // 取出一条 minVetex 相连的边
                Edge e = adj[minVertex.id].get(i);
                // minVertex-->nextVertex
                Vertex nextVertex = vertexes[e.tid];
                // 更新 next 的 dist
                if (minVertex.dist + e.w < nextVertex.dist) {
                    nextVertex.dist = minVertex.dist + e.w;
                    predecessor[nextVertex.id] = minVertex.id;
                    if (inqueue[nextVertex.id]) {
                        // 更新队列中的 dist 值
                        queue.update(nextVertex);
                    } else {
                        queue.add(nextVertex);
                        inqueue[nextVertex.id] = true;
                    }
                }
            }
        }
        // 输出最短路径
        System.out.print(s);
        print(s, t, predecessor);
    }

    private void print(int s, int t, int[] predecessor) {
        if (s == t) {
            return;
        }
        print(s, predecessor[t], predecessor);
        System.out.print("->" + t);
    }


    /**
     * 图的边
     */
    private static class Edge {
        /**
         * 边的起始顶点编号
         */
        int sid;

        /**
         * 边的终止顶点编号
         */
        int tid;

        /**
         * 权重
         */
        public int w;

        Edge(int sid, int tid, int w) {
            this.sid = sid;
            this.tid = tid;
            this.w = w;
        }
    }

    /**
     * 图的顶点类
     * 下面这个类是为了 dijkstra 实现用的
     */
    private static class Vertex {
        /**
         * 顶点编号 ID
         */
        public int id;

        /**
         * 从起始顶点到这个顶点的距离
         */
        public int dist;

        public Vertex(int id, int dist) {
            this.id = id;
            this.dist = dist;
        }
    }


    /**
     * 自定义优先级队列
     * 因为 Java 提供的优先级队列，没有暴露更新数据的接口，所以我们需要重新实现一个
     * 根据 vertex.dist 构建小顶堆
     */
    private static class PriorityQueue {
        private Vertex[] nodes;
        private int count;

        public PriorityQueue(int v) {
            this.nodes = new Vertex[v + 1];
            this.count = v;
        }

        public Vertex poll() {
            // TODO: 待实现
            return null;
        }

        public void add(Vertex vertex) {
            // TODO: 待实现
        }

        /**
         * 更新结点的值，并且从下往上堆化，重新符合堆的定义。时间复杂度 O(logn)。
         *
         * @param vertex 节点
         */
        public void update(Vertex vertex) {
            // TODO: 待实现
        }

        public boolean isEmpty() {
            // TODO: 待实现
            return false;
        }
    }

    public static void main(String[] args) {

    }

}
