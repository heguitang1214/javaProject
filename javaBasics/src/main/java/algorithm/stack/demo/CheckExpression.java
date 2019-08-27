package algorithm.stack.demo;

import algorithm.stack.LinkedStack;

/**
 * 表达式检测
 */
public class CheckExpression {

    public static String isValid(String expstr) {
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
                        return "(";
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

    public static void main(String args[]) {
        String expstr = "((5-3)*8-2)";
        System.out.println(expstr + "  " + isValid(expstr));
    }
}
