package algorithm.demo.stack;

import algorithm.stack.LinkedStack;
import algorithm.stack.SeqStack;

/**
 * 中缀转后缀,然后计算后缀表达式的值
 * 将包含提升运算优先级的圆括号"()"转换成从左往右计算的后缀表达式
 * 示例：
 * 1+3*(9-2)+9        转化前的中缀表达式
 * 1 3 9 2 - * + 9 +  转化后的后缀表达式
 */
public class CalculateExpression {

    /**
     * 中缀转后缀
     * <p>
     * 1）如果遇到操作数，我们就直接将其放入【数组B】中。
     * 2）如果遇到运算符，则我们将其放入到【栈A】中，遇到左括号时我们也将其放入栈A中。
     * 3）如果遇到一个右括号，则将栈元素弹出，将弹出的运算符输出并存入数组B中直到遇到左括号为止。注意，【左括号只弹出并不存入数组。】
     * 4）如果遇到任何其他的操作符，如（“+”， “*”，“（”）等，从栈中弹出元素存入数组B直到遇到发现更低优先级的元素(或者栈为空)为止。
     * 弹出完这些元素后，才将遇到的操作符压入到栈中。有一点需要注意，只有在遇到” ) “的情况下我们才弹出” ( “，其他情况我们都不会弹出” ( “。
     * 5）如果我们读到了输入的末尾，则将栈中所有元素依次弹出存入到数组B中。
     * 6）到此中缀表达式转化为后缀表达式完成，数组存储的元素顺序就代表转化后的后缀表达式。
     *
     * @param expstr 中缀表达式字符串
     * @return 后缀表达式
     */
    private static String toPostfix(String expstr) {
        //创建栈,用于存储运算符，也就是栈A
        SeqStack<String> stack = new SeqStack<>(expstr.length());

        //存储后缀表达式的字符串, 也就是数组B
        StringBuilder postfix = new StringBuilder();
        int i = 0;
        while (i < expstr.length()) {
            char ch = expstr.charAt(i);
            switch (ch) {
                case '+':
                case '-':
                    //当栈不为空或者栈顶元素不是左括号时,直接出栈,因此此时只有可能是*/+-四种运算符(根据规则4),否则入栈
                    while (!stack.isEmpty() && !"(".equals(stack.peek())) {
                        postfix.append(stack.pop());
                    }
                    // 操作符入栈
                    stack.push(ch + "");
                    i++;
                    break;
                case '*':
                case '/':
                    //遇到运算符*/
                    while (!stack.isEmpty() && ("*".equals(stack.peek()) || "/".equals(stack.peek()))) {
                        postfix.append(stack.pop());
                    }
                    stack.push(ch + "");
                    i++;
                    break;
                case '(':
                    //左括号直接入栈
                    stack.push(ch + "");
                    i++;
                    break;
                case ')':
                    //遇到右括号(规则3)
                    String out = stack.pop();
                    while (out != null && !"(".equals(out)) {
                        postfix.append(out);
                        out = stack.pop();
                    }
                    i++;
                    break;
                default:
                    //操作数直接入栈
                    while (ch >= '0' && ch <= '9') {
                        postfix.append(ch);
                        i++;
                        if (i < expstr.length()) {
                            ch = expstr.charAt(i);
                        } else {
                            ch = '=';
                        }
                    }
                    //分隔符
                    postfix.append(" ");
                    break;
            }
        }
        //最后把所有运算符出栈(规则5)
        while (!stack.isEmpty()) {
            postfix.append(stack.pop());
        }
        return postfix.toString();
    }

    /**
     * 计算后缀表达式的值
     *
     * @param postfix 传入后缀表达式
     * @return 结果
     */
    private static int calculatePostfixValue(String postfix) {
        //栈用于存储操作数,协助运算
        LinkedStack<Integer> stack = new LinkedStack<>();
        int i = 0, result = 0;
        while (i < postfix.length()) {
            char ch = postfix.charAt(i);
            if (ch >= '0' && ch <= '9') {
                result = 0;
                while (ch != ' ') {
                    //将整数字符转为整数值ch=90
                    result = result * 10 + Integer.parseInt(ch + "");
                    i++;
                    ch = postfix.charAt(i);
                }
                i++;
                //操作数入栈
                stack.push(result);
                //ch 是运算符,出栈栈顶的前两个元素
            } else {
                int y = stack.pop();
                int x = stack.pop();
                //根据情况进行计算
                switch (ch) {
                    case '+':
                        result = x + y;
                        break;
                    case '-':
                        result = x - y;
                        break;
                    case '*':
                        result = x * y;
                        break;
                    case '/':
                        //注意这里并没去判断除数是否为0的情况
                        result = x / y;
                        break;
                    default:
                        throw new RuntimeException();
                }
                //将运算结果入栈
                stack.push(result);
                i++;
            }
        }
        //将最后的结果出栈并返回
        return stack.pop();
    }

    //测试
    public static void main(String[] args) {
        String expstr = "1+3*(9-2)+90";
        String postfix = toPostfix(expstr);
        System.out.println("中缀表达式->expstr=  " + expstr);
        System.out.println("后缀表达式->postfix= " + postfix);
        System.out.println("计算结果->value= " + calculatePostfixValue(postfix));
    }

}
