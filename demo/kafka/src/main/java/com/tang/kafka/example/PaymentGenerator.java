package com.tang.kafka.example;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class PaymentGenerator {
    private static final String[] brand = {"耐克", "阿迪达斯", "NEW BALANCE", "亚瑟士", "美津浓", "李宁", "安踏", "特步", "鸿星尔克", "乔丹",
            "361°", "匹克", "彪马", "双星", "骆驼", "锐步", "圣康尼", "斯凯奇", "多威", "回力", "斐乐"};

    private static final String[] product = {"慢跑鞋", "滑板鞋", "涉水鞋", "三叶草", "透气跑鞋", "帆布鞋", "休闲鞋", "板鞋", "篮球鞋", "足球鞋",
            "运动包", "紧身裤", "休闲套装", "卫衣/套头衫", "夹克/风衣", "运动裤", "棉服", "羽绒服", "运动套装", "运动配饰"};

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final int[] discountArray = {50, 60, 70, 80, 85, 90, 100};

    public PaymentGenerator() {
    }

    public String generate() {
        int orderId = random(1000000, 900000);
        int totalPrice = random(200, 2000);
        int discount = discountArray[random(0, 6)];
        int paymentPrice = discount * totalPrice / 100;
        int idxBrand = random(0, brand.length - 1);
        int idxProduct = random(0, product.length - 1);

        StringBuffer sb = new StringBuffer();
        sb.append("orderId: %s,");
        sb.append("orderDate: %s,");
        sb.append("paymentDate: %s,");
        sb.append("brand: %s,");
        sb.append("product: %s,");
        sb.append("totalPrice: %s,");
        sb.append("discount: %s,");
        sb.append("paymentPrice: %s");
        String date = sdf.format(new Date());

        String str = String.format(sb.toString(), orderId, date, date, brand[idxBrand], product[idxProduct],
                totalPrice, discount, paymentPrice);
        return str;
    }

    public Payment generatePayment() {
        Payment payment = new Payment();
        int orderId = random(1000000, 900000);
        int totalPrice = random(200, 2000);
        int discount = discountArray[random(0, 6)];
        int paymentPrice = discount * totalPrice / 100;
        int idxBrand = random(0, brand.length - 1);
        int idxProduct = random(0, product.length - 1);
        payment.setOrderId(orderId);
        payment.setBrandName(brand[idxBrand]);
        payment.setProductName(product[idxProduct]);
        payment.setOrderDate(sdf.format(new Date()));
        payment.setPayDate(sdf.format(new Date()));
        payment.setTotalPrice(Float.valueOf(totalPrice));
        payment.setPayDiscount(discount);
        payment.setPayPrice(Float.valueOf(paymentPrice));
        return payment;
    }

    private int random(int min, int max) {
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }
}
