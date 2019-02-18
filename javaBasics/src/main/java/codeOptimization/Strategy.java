package codeOptimization;

import java.math.BigDecimal;

/**
 * @Author heguitang
 * @Date 2019/2/18 15:38
 * @Version 1.0
 * @Desc
 */
public interface Strategy {

    //计算支付金额
    BigDecimal calRecharge(Integer channelId, Integer goodsId);

}
