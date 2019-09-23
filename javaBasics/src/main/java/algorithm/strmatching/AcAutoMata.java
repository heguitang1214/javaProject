package algorithm.strmatching;

import java.util.*;

/**
 * AC自动机
 *
 * @author Tang
 */
public class AcAutoMata {

    private AcNode root = new AcNode('/');


    /**
     * 往字典树中插入一个字符串
     *
     * @param text 字符串
     */
    private void insert(char[] text) {
        AcNode p = root;

        for (int i = 0; i < text.length; i++) {
            int index = text[i] - 'a';
            if (p.children[index] == null) {
                AcNode newNode = new AcNode(text[i]);
                p.children[index] = newNode;
            }
            // 更新p指针，继续循环
            p = p.children[index];
        }
        p.isEndingChar = true;
    }


    /**
     * 构建失败指针
     */
    public void buildFailurePointer() {
        Queue<AcNode> queue = new LinkedList<>();
        root.fail = null;
        queue.add(root);
        while (!queue.isEmpty()) {
            AcNode p = queue.remove();
            for (int i = 0; i < 26; ++i) {
                AcNode pc = p.children[i];
                if (pc == null) {
                    continue;
                }
                if (p == root) {
                    pc.fail = root;
                } else {
                    AcNode q = p.fail;
                    while (q != null) {
                        AcNode qc = q.children[pc.data - 'a'];
                        if (qc != null) {
                            pc.fail = qc;
                            break;
                        }
                        q = q.fail;
                    }
                    if (q == null) {
                        pc.fail = root;
                    }
                }
                queue.add(pc);
            }
        }
    }

    /**
     * 在 AC 自动机上匹配主串
     *
     * @param text 主串
     */
    private void match(char[] text) {
        int n = text.length;
        AcNode p = root;
        for (int i = 0; i < n; ++i) {
            int idx = text[i] - 'a';
            while (p.children[idx] == null && p != root) {
                // 失败指针发挥作用的地方
                p = p.fail;
            }
            p = p.children[idx];
            // 如果没有匹配的，从 root 开始重新匹配
            if (p == null) {
                p = root;
            }
            AcNode tmp = p;
            // 打印出可以匹配的模式串
            while (tmp != root) {
                if (tmp.isEndingChar == true) {
                    int pos = i - tmp.length + 1;
                    System.out.println(" 匹配起始下标 " + pos + "; 长度 " + tmp.length);
                }
                tmp = tmp.fail;
            }
        }
    }

    /**
     * AC自动机节点
     */
    private static class AcNode {
        public char data;

        /**
         * 字符集只包含 a~z 这 26 个字符
         */
        public AcNode[] children = new AcNode[26];

        /**
         * 结尾字符为 true
         */
        public boolean isEndingChar = false;

        /**
         * 当 isEndingChar=true 时，记录模式串长度
         */
        public int length = -1;

        /**
         * 失败指针:相当于 KMP 中的失效函数 next 数组
         */
        public AcNode fail;

        public AcNode(char data) {
            this.data = data;
        }

    }

    public static void main(String[] args) {
        char[] chars = {'c', 'a', 'o'};
        AcAutoMata acAutoMata = new AcAutoMata();
        acAutoMata.insert(chars);

        acAutoMata.buildFailurePointer();

        System.out.println("===================================开始匹配========================");
        char[] chars1 = {'e','f'};
        acAutoMata.match(chars1);


        char[] chars2 = {'a','f'};
        acAutoMata.match(chars2);
        char[] chars3 = {'c','f'};
        acAutoMata.match(chars3);
        char[] chars4 = {'a','o'};
        acAutoMata.match(chars4);
    }

}
