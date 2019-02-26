package ai.yunxi.sharding.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ItemGenerator {
    private static final String[] brand = {"耐克", "阿迪达斯", "NEW BALANCE", "亚瑟士", "美津浓",
            "李宁", "安踏", "特步", "鸿星尔克", "乔丹", "361°", "匹克", "彪马", "双星", "骆驼",
            "锐步", "圣康尼", "斯凯奇", "多威", "回力", "斐乐"};

    private static final String[] product = {"慢跑鞋", "滑板鞋", "涉水鞋", "三叶草", "透气跑鞋",
            "帆布鞋", "休闲鞋", "板鞋", "篮球鞋", "足球鞋", "运动包", "紧身裤", "休闲套装",
            "卫衣/套头衫", "夹克/风衣", "运动裤", "棉服", "羽绒服", "运动套装", "运动配饰"};

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final int[] discountArray = {50, 60, 70, 80, 85, 90, 100};

    public static OrderItem generate() {
        OrderItem orderItem = new OrderItem();
        int orderId = random(1000000, 900000);
        int totalPrice = random(200, 2000);
        int discount = discountArray[random(0, 6)];
        int paymentPrice = discount * totalPrice / 100;
        int idxBrand = random(0, brand.length - 1);
        int idxProduct = random(0, product.length - 1);
        orderItem.setOrderId(orderId);
        orderItem.setBrandName(brand[idxBrand]);
        orderItem.setProductName(product[idxProduct]);
        orderItem.setOrderDate(sdf.format(new Date()));
        orderItem.setPayDate(sdf.format(new Date()));
        orderItem.setTotalPrice(Float.valueOf(totalPrice));
        orderItem.setPayDiscount(discount);
        orderItem.setPayPrice(Float.valueOf(paymentPrice));
        return orderItem;
    }

    private static int random(int min, int max) {
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }
}
