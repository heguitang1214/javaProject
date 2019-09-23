package algorithm.strmatching;

/**
 * 字典树实现字符串的匹配
 *
 * @author Tang
 */
public class TireTree {

    /**
     * 存储无意义字符
     */
    private TireNode root = new TireNode('/');

    /**
     * 往字典树中插入一个字符串
     *
     * @param text 字符串
     */
    private void insert(char[] text) {
        TireNode p = root;

        for (int i = 0; i < text.length; i++) {
            int index = text[i] - 'a';
            if (p.children[index] == null) {
                TireNode newNode = new TireNode(text[i]);
                p.children[index] = newNode;
            }
            // 更新p指针，继续循环
            p = p.children[index];
        }
        p.isEndingChar = true;
    }


    /**
     * 在 Trie 树中查找一个字符串
     *
     * @param pattern 待查找的字符串
     * @return 是否存在
     */
    private boolean find(char[] pattern) {
        TireNode p = root;
        for (int i = 0; i < pattern.length; i++) {
            int index = pattern[i] - 'a';
            if (p.children[index] == null) {
                // 不存在 pattern
                return false;
            }
            p = p.children[index];
        }
        // p.isEndingChar == false
        if (!p.isEndingChar) {
            // 不能完全匹配，只是前缀
            return false;
        } else {
            // 找到 pattern
            return true;
        }

    }

    /**
     * 字典树节点
     */
    private static class TireNode {

        private char data;

        private TireNode[] children = new TireNode[26];

        /**
         * 是否是结束字符
         */
        private boolean isEndingChar = false;

        public TireNode(char data) {
            this.data = data;
        }
    }


    public static void main(String[] args) {
        TireTree tireTree = new TireTree();
        tireTree.insert("how".toCharArray());
        tireTree.insert("hi".toCharArray());
        tireTree.insert("her".toCharArray());
        tireTree.insert("hello".toCharArray());
        tireTree.insert("so".toCharArray());
        tireTree.insert("see".toCharArray());

        boolean b1 = tireTree.find("hello".toCharArray());
        boolean b2 = tireTree.find("her".toCharArray());
        boolean b3 = tireTree.find("here".toCharArray());
        System.out.println("b1=" + b1 + "\nb2=" + b2 + "\nb3=" + b3);

    }

}
