package algorithm.strmatching;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;

/**
 * AC自动机
 *
 * @author Tang
 */
public class AcAutoMataV2 {
    private ACNode root;

    private AcAutoMataV2() {
        this.root = new ACNode("/");
    }

    private void insert(String pattern) {
        ACNode node = this.root;
        int len = pattern.length();
        for (int i = 0; i < len; i++) {
            String c = pattern.charAt(i) + "";
            if (Objects.isNull(node.children.get(c))) {
                node.children.put(c, new ACNode(c));
            }
            node = node.children.get(c);
        }

        node.isEndingChar = true;
        node.length = pattern.length();
    }


    /**
     * 构建失败指针
     */
    private void buildFailurePointer() {
        ACNode root = this.root;
        LinkedList<ACNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            ACNode p = queue.pop();

            for (ACNode pc : p.children.values()) {
                if (Objects.isNull(pc)) {
                    continue;
                }

                if (p == root) {
                    pc.fail = root;
                } else {
                    ACNode q = p.fail;
                    while (Objects.nonNull(q)) {
                        ACNode qc = q.children.get(pc.data);
                        if (Objects.nonNull(qc)) {
                            pc.fail = qc;
                            break;
                        }
                        q = q.fail;
                    }
                    if (Objects.isNull(q)) {
                        pc.fail = root;
                    }
                }
                queue.add(pc);
            }
        }
    }

    /**
     * 匹配字符串
     *
     * @param text 文本
     * @return 是否匹配
     */
    private Boolean match(String text) {
        ACNode root = this.root;
        ACNode p = root;

        int n = text.length();
        for (int i = 0; i < n; i++) {
            String c = text.charAt(i) + "";
            while (Objects.isNull(p.children.get(c)) && p != root) {
                p = p.fail;
            }

            p = p.children.get(c);
            if (Objects.isNull(p)) {
                p = root;
            }

            ACNode tmp = p;
            while (tmp != root) {
                // tmp.isEndingChar == true
                if (tmp.isEndingChar) {
                    // 可修改成字符串的替换
                    System.out.println("Start from " + (i - p.length + 1));
                    return true;
                }
                tmp = tmp.fail;
            }
        }
        return false;
    }

    /**
     * 多模式串的匹配
     *
     * @param text     主串
     * @param patterns 模式串
     * @return 是否存在模式串的匹配
     */
    private static boolean match(String text, String[] patterns) {
        AcAutoMataV2 automata = new AcAutoMataV2();
        for (String pattern : patterns) {
            automata.insert(pattern);
        }

        automata.buildFailurePointer();
        return automata.match(text);
    }

    /**
     * AC自动机节点
     */
    private static class ACNode {

        private String data;

        private Map<String, ACNode> children;

        /**
         * 结尾字符为 true
         */
        private Boolean isEndingChar;

        /**
         * 当 isEndingChar=true 时，记录模式串长度
         */
        private Integer length;

        /**
         * 失败指针:相当于 KMP 中的失效函数 next 数组
         */
        private ACNode fail;

        ACNode(String data) {
            this.data = data;
            this.children = new HashMap<>();
            this.isEndingChar = false;
            this.length = 0;
            this.fail = null;
        }
    }

    public static void main(String[] args) {
        String[] patterns = {"at", "art", "oars", "soar"};
        String text = "soarsoars";
        System.out.println(match(text, patterns));

        String[] patterns2 = {"Fxtec Pro1x", "谷歌Pixel"};
        String text2 = "一家总部位于伦敦的公司Fxtex在MWC上就推出了一款名为Fxtec Pro1的手机，该机最大的亮点就是采用了侧滑式全键盘设计。DxOMark年度总榜发布 华为P20 Pro/谷歌Pixel 3争冠";
        System.out.println(match(text2, patterns2));

        String[] patterns3 = {"大中华"};
        System.out.println(match(text2, patterns3));
    }

}
