package algorithm.thought.toflashback;

/**
 * 正则表达式
 * 假设正表达式中只包含“*”和“?”这两种通配符，并且对这两个通配符的语义稍微做些改变，
 * 其中，“*”匹配任意多个(大于等于 0 个)任意字符，“?”匹配零个或者一个任意字符。
 *
 * @author Tang
 */
public class Pattern {

    /**
     * 是否匹配
     */
    private boolean matched = false;

    /**
     * 正则表达式
     */
    private char[] pattern;

    /**
     * 正则表达式长度
     */
    private int plen;


    private Pattern(char[] pattern, int plen) {
        this.pattern = pattern;
        this.plen = plen;
    }

    /**
     * 是否匹配
     *
     * @param text 文本串
     * @param tlen 文本串长度
     * @return 是否匹配
     */
    public boolean match(char[] text, int tlen) {
        matched = false;
        rmatch(0, 0, text, tlen);
        return matched;
    }


    /**
     * 字符串的匹配
     *
     * @param ti   文本串下标
     * @param pj   正则表达式下标
     * @param text 文本串
     * @param tlen 文本串长度
     */
    private void rmatch(int ti, int pj, char[] text, int tlen) {
        // 如果已经匹配了，就不要继续递归了
        if (matched) {
            return;
        }
        // 正则表达式到结尾了
        if (pj == plen) {
            // 文本串也到结尾了
            if (ti == tlen) {
                matched = true;
            }
            return;
        }
        // * 匹配任意个字符
        if (pattern[pj] == '*') {
            for (int k = 0; k <= tlen - ti; ++k) {
                rmatch(ti + k, pj + 1, text, tlen);
            }
        } else if (pattern[pj] == '?') {
            // ? 匹配 0 个或者 1 个字符
            rmatch(ti, pj + 1, text, tlen);
            rmatch(ti + 1, pj + 1, text, tlen);
        } else if (ti < tlen && pattern[pj] == text[ti]) {
            // 纯字符匹配才行
            rmatch(ti + 1, pj + 1, text, tlen);
        }
    }

    public static void main(String[] args) {
        char[] pattern = {'s', '*', 't'};
        Pattern pattern1 = new Pattern(pattern, pattern.length);
        String text = "sxxxt";
        pattern1.match(text.toCharArray(), text.length());
        System.out.println("是否匹配：" + pattern1.matched);

    }

}
