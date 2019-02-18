package codeOptimization;

import java.math.BigDecimal;

/**
 * @Author heguitang
 * @Date 2019/2/18 15:46
 * @Version 1.0
 * @Desc
 */
public class Context {

    public BigDecimal calRecharge(Integer channelId, Integer goodsId) throws Exception {
        //1.根据渠道ID，去吧某一个银行的实现类拿出来   工厂
        StrategyFactory factory = StrategyFactory.getInstance();
        Strategy strategy = factory.create(channelId);

        //2.调用你银行具体实现了的calRecharge
        return strategy.calRecharge(channelId, goodsId);
    }
}
