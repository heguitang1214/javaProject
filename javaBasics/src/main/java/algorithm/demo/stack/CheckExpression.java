package algorithm.demo.stack;

import algorithm.stack.LinkedStack;

/**
 * 表达式检测
 * 在编写程序的过程中，我们经常会遇到诸如圆括号“()”与花括号“{}”，这些符号都必须是左右匹配的，这就是我们所说的符合匹配类型，
 * 当然符合不仅需要个数相等，而且需要先左后右的依次出现，否则就不符合匹配规则，如“)(”，明显是错误的匹配，而“()”才是正确的匹配。
 * 有时候符合如括号还会嵌套出现，如“9-(5+(5+1))”,而嵌套的匹配原则是一个右括号与其前面最近的一个括号匹配，
 * 事实上编译器帮我检查语法错误是也是执行一样的匹配原理，而这一系列操作都需要借助栈来完成，接下来我们使用栈来实现括号”()”是否匹配的检测。
 *
 * @author Tang
 */
public class CheckExpression {

    /**
     * 表达式检测
     *
     * @param expstr 表达式
     * @return 是否符合表达式的描述
     */
    private static String isValid(String expstr) {
        //创建栈
        LinkedStack<String> stack = new LinkedStack<>();
        int i = 0;
        while (i < expstr.length()) {
            char ch = expstr.charAt(i);
            i++;

            switch (ch) {
                case '(':
                    // 左括号直接入栈
                    stack.push(ch + "");
                    break;
                case ')':
                    // 遇见右括号左括号直接出栈
                    if (stack.isEmpty() || !"(".equals(stack.pop())) {
                        return "match exception!";
                    }
                default:
                    break;
            }
        }
        //最后检测是否为空,为空则检测通过
        if (stack.isEmpty()) {
            return "check pass!";
        } else {
            return "check exception!";
        }
    }

    public static void main(String[] args) {
        String expstr = "((5-3)*8-2)";
        System.out.println(expstr + "  " + isValid(expstr));
    }
}