package com.yunxi.yunxiruleengine.xiaoming;


import com.yunxi.yunxiruleengine.entity.Order;
import com.yunxi.yunxiruleengine.entity.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 无双老师【云析学院】
 * @Date: 2019-02-13
 * @Description: 小明的代码
 */
public class JavaScoreExample {
      
    public static void main(String[] args) throws Exception {
//        version1();
        version2();
          
    }

    /**
     * 小明的第1版代码
     */
    public static void version1() throws Exception {
        List<Order> orderList = getInitData();
        for (int i=0; i<orderList.size(); i++){
            Order order = orderList.get(i);
            if (order.getAmout() <= 100){
                order.setScore(0);
                addScore(order);
            }else if(order.getAmout() > 100 && order.getAmout() <= 500){
                order.setScore(100);
                addScore(order);
            }else if(order.getAmout() > 500 && order.getAmout() <= 1000){
                order.setScore(500);
                addScore(order);
            }else{
                order.setScore(1000);
                addScore(order);
            }
        }
    }

    /**
     * 小明的第2版代码
     */
    public static void version2() throws Exception {
        List<Order> orderList = getInitData();
        for (int i=0; i<orderList.size(); i++){
            Order order = orderList.get(i);
            if (order.getAmout() <= 200){
                order.setScore(0);
                addScore(order);
            }else if(order.getAmout() > 200 && order.getAmout() <= 1000){
                order.setScore(100);
                addScore(order);
            }else if(order.getAmout() > 1000 && order.getAmout() <= 2000){
                order.setScore(500);
                addScore(order);
            }else{
                order.setScore(1000);
                addScore(order);
            }
        }
    }

    public static void addScore(Order o){
        System.out.println("用户" + o.getUser().getName() + "享受额外增加积分: " + o.getScore());  
    }  
      
    public static List<Order> getInitData() throws Exception {
        List<Order> orderList = new ArrayList<Order>();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        {
            Order order = new Order();  
            order.setAmout(80);
            order.setBookingDate(df.parse("2019-02-14"));
            User user = new User();
            user.setLevel(1);  
            user.setName("张三");
            order.setUser(user);  
            orderList.add(order);  
        }
        {
            Order order = new Order();  
            order.setAmout(200);  
            order.setBookingDate(df.parse("2019-02-15"));
            User user = new User();
            user.setLevel(2);  
            user.setName("李四");
            order.setUser(user);  
            orderList.add(order);  
        }  
        {  
            Order order = new Order();  
            order.setAmout(800);  
            order.setBookingDate(df.parse("2019-02-16"));
            User user = new User();  
            user.setLevel(3);  
            user.setName("王五");
            order.setUser(user);  
            orderList.add(order);  
        }  
        {  
            Order order = new Order();  
            order.setAmout(1500);  
            order.setBookingDate(df.parse("2019-02-17"));
            User user = new User();  
            user.setLevel(4);  
            user.setName("赵六");
            order.setUser(user);  
            orderList.add(order);  
        }  
        return orderList;  
    }  
}  