package algorithm.graph;

import java.util.LinkedList;

/**
 * 启发式搜索：A*算法
 *
 * @author Tang
 */
public class AStarGraph {

    /**
     * 顶点个数
     */
    private int v;

    /**
     * 有向有权图的邻接表表示:邻接表
     */
    private LinkedList<Edge>[] adj;

    /**
     * Graph 类的成员变量，在构造函数中初始化
     */
    private Vertex[] vertexes = new Vertex[this.v];


    /**
     * 新增一个方法，添加顶点的坐标
     *
     * @param id 顶点编号
     * @param x  顶点横坐标
     * @param y  顶点纵坐标
     */
    private void addVetex(int id, int x, int y) {
        vertexes[id] = new Vertex(id, x, y);
    }

    private AStarGraph(int v) {
        this.v = v;
        this.adj = new LinkedList[v];
        for (int i = 0; i < v; ++i) {
            this.adj[i] = new LinkedList<>();
        }
    }

    /**
     * 添加一条边
     *
     * @param s 起始顶点编号
     * @param t 终止顶点编号
     * @param w 权重
     */
    public void addEdge(int s, int t, int w) {
        this.adj[s].add(new Edge(s, t, w));
        this.adj[t].add(new Edge(t, s, w));
    }


    /**
     * A*寻址算法：从顶点 s 到顶点 t 的路径
     *
     * @param s 顶点 s
     * @param t 顶点 t
     */
    private void astar(int s, int t) {
        // 用来还原路径
        int[] predecessor = new int[this.v];
        // 按照 vertex 的 f 值构建的小顶堆，而不是按照 dist
        PriorityQueue queue = new PriorityQueue(this.v);
        // 标记是否进入过队列
        boolean[] inqueue = new boolean[this.v];
        vertexes[s].dist = 0;
        vertexes[s].f = 0;
        queue.add(vertexes[s]);
        inqueue[s] = true;
        while (!queue.isEmpty()) {
            // 取堆顶元素并删除
            Vertex minVertex = queue.poll();
            for (int i = 0; i < adj[minVertex.id].size(); ++i) {
                // 取出一条 minVetex 相连的边
                Edge e = adj[minVertex.id].get(i);
                // minVertex --> nextVertex
                Vertex nextVertex = vertexes[e.tid];
                // 更新 next 的 dist,f
                if (minVertex.dist + e.w < nextVertex.dist) {
                    nextVertex.dist = minVertex.dist + e.w;
                    // f(i)=g(i)+h(i)
                    nextVertex.f = nextVertex.dist + hManhattan(nextVertex, vertexes[t]);
                    predecessor[nextVertex.id] = minVertex.id;
                    if (inqueue[nextVertex.id]) {
                        queue.update(nextVertex);
                    } else {
                        queue.add(nextVertex);
                        inqueue[nextVertex.id] = true;
                    }
                }
                // 只要到达 t 就可以结束 while 了
                if (nextVertex.id == t) {
                    // 清空 queue，才能推出 while 循环
                    queue.clear();
                    break;
                }
            }
        }
        // 输出路径
        System.out.print(s);
        // print 函数请参看 Dijkstra 算法的实现
        print(s, t, predecessor);
    }

    /**
     * Vertex 表示顶点，后面有定义
     *
     * @param v1 顶点1
     * @param v2 顶点2
     * @return 求差
     */
    private int hManhattan(Vertex v1, Vertex v2) {
        return Math.abs(v1.x - v2.x) + Math.abs(v1.y - v2.y);
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
     * 图的顶点
     */
    private static class Vertex {
        /**
         * 顶点编号 ID
         */
        public int id;

        /**
         * 从起始顶点，到这个顶点的距离，也就是 g(i)
         */
        public int dist;

        /**
         * 新增：f(i)=g(i)+h(i)
         */
        public int f;

        /**
         * 新增：顶点在地图中的坐标（x, y）
         */
        public int x, y;

        public Vertex(int id, int x, int y) {
            this.id = id;
            this.x = x;
            this.y = y;
            this.f = Integer.MAX_VALUE;
            this.dist = Integer.MAX_VALUE;
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

        public void clear() {

        }
    }

    public static void main(String[] args) {
        AStarGraph aStarGraph = new AStarGraph(11);

        aStarGraph.addVetex(0, 320, 630);
        aStarGraph.addVetex(1, 300, 630);
        aStarGraph.addVetex(2, 280, 625);
        aStarGraph.addVetex(3, 270, 630);
        aStarGraph.addVetex(4, 320, 700);
        aStarGraph.addVetex(5, 360, 620);
        aStarGraph.addVetex(6, 320, 590);
        aStarGraph.addVetex(7, 370, 580);
        aStarGraph.addVetex(8, 350, 730);
        aStarGraph.addVetex(9, 390, 690);
        aStarGraph.addVetex(10, 400, 620);

        aStarGraph.addEdge(0, 1, 20);

        aStarGraph.astar(0, 10);

//        Vertex vertex0 = new Vertex(0, 320, 630);
//        Vertex vertex1 = new Vertex(1, 300, 630);
//        Vertex vertex2 = new Vertex(2, 280, 625);
//        Vertex vertex3 = new Vertex(3, 270, 630);
//        Vertex vertex4 = new Vertex(4, 320, 700);
//        Vertex vertex5 = new Vertex(5, 360, 620);
//        Vertex vertex6 = new Vertex(6, 320, 590);
//        Vertex vertex7 = new Vertex(7, 370, 580);
//        Vertex vertex8 = new Vertex(8, 350, 730);
//        Vertex vertex9 = new Vertex(9, 390, 690);
//        Vertex vertex10 = new Vertex(10, 400, 620);

    }

}
