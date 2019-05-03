package com.tang.springcloudeurekaproviderdemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO..
 *
 * @author : Five-云析学院
 * @since : 2019年04月24日 21:13
 */
@RestController
public class ProductController {

    // 定义一个库存集合
    private static final Map<String, Integer> productMap = new HashMap<String, Integer>();

    /***
     * 修改库存
     * @param productName
     * @param num
     * @return
     */
    @GetMapping("/updateProduct/{productName}/{num}")
    public String updateProducter(String productName, Integer num){
        if(productName != null && !productName.isEmpty()){
            productMap.put(productName, (productMap.get(productName) == null ? 0: productMap.get(productName))-num);
            System.out.println("修改库存：productName-"+productName+", num-"+num);
        }
        return "修改库存成功, "+productName+" 剩下 "+productMap.get(productName);
    }
}
