package com.tang.sharding.advance;

import io.shardingsphere.core.parsing.lexer.dialect.mysql.MySQLLexer;

/**
 * 测试MySQL的解析引擎
 *
 * @author Tang
 */
public class TestLexer {
    public static void main(String[] args) {
        String sql = "SELECT i.* FROM t_order o" +
                " JOIN t_order_item i ON o.order_id=i.order_id" +
                " WHERE o.user_id=? AND o.order_id=?";
        MySQLLexer lexer = new MySQLLexer(sql);
//        lexer.nextToken();
//        while (!Assist.END.name().equals(lexer.getCurrentToken().getType())){
//
//            System.out.println(lexer.getCurrentToken().getType() + " | " + lexer.getCurrentToken().getLiterals());
//            lexer.nextToken();
//        }
        lexer.nextToken();
        System.out.println(lexer.getCurrentToken().getType() + " | " + lexer.getCurrentToken().getLiterals());
        lexer.nextToken();
        System.out.println(lexer.getCurrentToken().getType() + " | " + lexer.getCurrentToken().getLiterals());
        lexer.nextToken();
        System.out.println(lexer.getCurrentToken().getType() + " | " + lexer.getCurrentToken().getLiterals());
        lexer.nextToken();
        System.out.println(lexer.getCurrentToken().getType() + " | " + lexer.getCurrentToken().getLiterals());
        lexer.nextToken();
        System.out.println(lexer.getCurrentToken().getType() + " | " + lexer.getCurrentToken().getLiterals());
        lexer.nextToken();
        System.out.println(lexer.getCurrentToken().getType() + " | " + lexer.getCurrentToken().getLiterals());
        lexer.nextToken();
        System.out.println(lexer.getCurrentToken().getType() + " | " + lexer.getCurrentToken().getLiterals());
        lexer.nextToken();
        System.out.println(lexer.getCurrentToken().getType() + " | " + lexer.getCurrentToken().getLiterals());
        lexer.nextToken();
        System.out.println(lexer.getCurrentToken().getType() + " | " + lexer.getCurrentToken().getLiterals());
        lexer.nextToken();
        System.out.println(lexer.getCurrentToken().getType() + " | " + lexer.getCurrentToken().getLiterals());
        lexer.nextToken();
        System.out.println(lexer.getCurrentToken().getType() + " | " + lexer.getCurrentToken().getLiterals());
        lexer.nextToken();
        System.out.println(lexer.getCurrentToken().getType() + " | " + lexer.getCurrentToken().getLiterals());
        lexer.nextToken();
        System.out.println(lexer.getCurrentToken().getType() + " | " + lexer.getCurrentToken().getLiterals());
        lexer.nextToken();
        System.out.println(lexer.getCurrentToken().getType() + " | " + lexer.getCurrentToken().getLiterals());
        lexer.nextToken();
        System.out.println(lexer.getCurrentToken().getType() + " | " + lexer.getCurrentToken().getLiterals());
        lexer.nextToken();
        System.out.println(lexer.getCurrentToken().getType() + " | " + lexer.getCurrentToken().getLiterals());
        lexer.nextToken();
        System.out.println(lexer.getCurrentToken().getType() + " | " + lexer.getCurrentToken().getLiterals());
        lexer.nextToken();
        System.out.println(lexer.getCurrentToken().getType() + " | " + lexer.getCurrentToken().getLiterals());
        lexer.nextToken();
        System.out.println(lexer.getCurrentToken().getType() + " | " + lexer.getCurrentToken().getLiterals());
        lexer.nextToken();
        System.out.println(lexer.getCurrentToken().getType() + " | " + lexer.getCurrentToken().getLiterals());
        lexer.nextToken();
        System.out.println(lexer.getCurrentToken().getType() + " | " + lexer.getCurrentToken().getLiterals());
        lexer.nextToken();
        System.out.println(lexer.getCurrentToken().getType() + " | " + lexer.getCurrentToken().getLiterals());
        lexer.nextToken();
        System.out.println(lexer.getCurrentToken().getType() + " | " + lexer.getCurrentToken().getLiterals());
        lexer.nextToken();
        System.out.println(lexer.getCurrentToken().getType() + " | " + lexer.getCurrentToken().getLiterals());
        lexer.nextToken();
        System.out.println(lexer.getCurrentToken().getType() + " | " + lexer.getCurrentToken().getLiterals());
        lexer.nextToken();
        System.out.println(lexer.getCurrentToken().getType() + " | " + lexer.getCurrentToken().getLiterals());
        lexer.nextToken();
        System.out.println(lexer.getCurrentToken().getType() + " | " + lexer.getCurrentToken().getLiterals());
        lexer.nextToken();
        System.out.println(lexer.getCurrentToken().getType() + " | " + lexer.getCurrentToken().getLiterals());
        lexer.nextToken();
        System.out.println(lexer.getCurrentToken().getType() + " | " + lexer.getCurrentToken().getLiterals());
        lexer.nextToken();
        System.out.println(lexer.getCurrentToken().getType() + " | " + lexer.getCurrentToken().getLiterals());
        lexer.nextToken();
        System.out.println(lexer.getCurrentToken().getType() + " | " + lexer.getCurrentToken().getLiterals());
    }
}
