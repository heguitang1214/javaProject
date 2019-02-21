package codeOptimization.impl;

import codeOptimization.Pay;
import codeOptimization.Strategy;

import java.math.BigDecimal;

/**
 * @Author heguitang
 * @Date 2019/2/18 15:40
 * @Version 1.0
 * @Desc
 */
@Pay(channlId = 2)
public class CCBBankImpl implements Strategy {


    @Override
    public BigDecimal calRecharge(Integer channelId, Integer goodsId) {

        //......
        return new BigDecimal(channelId + goodsId);
    }
}
