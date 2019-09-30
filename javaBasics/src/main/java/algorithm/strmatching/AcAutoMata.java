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
    private void buildFailurePointer() {
        Queue<AcNode> queue = new LinkedList<>();
        root.fail = null;
        queue.add(root);
        while (!queue.isEmpty()) {
            AcNode p = queue.remove();
            for (int i = 0; i < 26; ++i) {
                // 获取队列中的 p节点的子节点 pc
                AcNode pc = p.children[i];
                if (pc == null) {
                    continue;
                }
                if (p == root) {
                    pc.fail = root;
                } else {
                    // 获取父结点 p的失败指针q，来构建子节点pc的失败指针
                    AcNode q = p.fail;
                    // 父结点的失败指针不为空
                    while (q != null) {
                        // 如果p的子节点pc等于q的子节点qc，即：qc不为空
                        AcNode qc = q.children[pc.data - 'a'];
                        if (qc != null) {
                            // pc的失败指针就为q的子节点qc
                            pc.fail = qc;
                            break;
                        }
                        // 更新q节点，继续循环，寻找q的最长匹配后缀子串对应的模式串的前缀的最后一个节点
                        q = q.fail;
                    }
                    if (q == null) {
                        pc.fail = root;
                    }
                }
                // 将子节点放入队列中
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
                // p.children[idx] == null，主串中的当前字符不能匹配
                // 失败指针发挥作用的地方
                p = p.fail;
            }

            // 字符匹配上，修改p指针
            p = p.children[idx];
            // 如果没有匹配的，从 root 开始重新匹配
            if (p == null) {
                p = root;
            }

            // tmp是子节点
            AcNode tmp = p;
            // 打印出可以匹配的模式串
            // 检查当前的元素p，是否存在模式的叶子节点也是p。例如：p是abcd中的c，还存在一个模式串c
            while (tmp != root) {
                // tmp.isEndingChar == true
                if (tmp.isEndingChar) {
                    int pos = i - tmp.length + 1;
                    System.out.println(Arrays.toString(text) + " 匹配起始下标 " + pos + "; 长度 " + tmp.length);
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
        private AcNode[] children = new AcNode[26];

        /**
         * 结尾字符为 true
         */
        private boolean isEndingChar = false;

        /**
         * 当 isEndingChar=true 时，记录模式串长度
         */
        public int length = -1;

        /**
         * 失败指针:相当于 KMP 中的失效函数 next 数组
         */
        private AcNode fail;

        public AcNode(char data) {
            this.data = data;
        }

    }

    public static void main(String[] args) {
        char[] chars = "cao".toCharArray();
        char[] chars1 = "kao".toCharArray();
        AcAutoMata acAutoMata = new AcAutoMata();
        acAutoMata.insert(chars);
        acAutoMata.insert(chars1);

        acAutoMata.buildFailurePointer();

        System.out.println("===================================开始匹配========================");
        String str1 = "ef";
        acAutoMata.match(str1.toCharArray());

        String str2 = "afcao";
        acAutoMata.match(str2.toCharArray());

        String str3 = "nkao";
        acAutoMata.match(str3.toCharArray());
    }

}
