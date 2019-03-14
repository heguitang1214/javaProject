package com.yunxi.yunxiruleengine.template;

import com.yunxi.yunxiruleengine.entity.Order;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: 无双老师【云析学院】
 * @Date: 2019-02-16
 * @Description:
 */
@Component
public class RuleTemplate implements Rule{
    @Resource
    private KieContainer kieContainer;
    @Override
    public Order execute(Order order) {
        KieSession ksession = kieContainer.newKieSession();
        ksession.insert(order);
        ksession.fireAllRules();
        return order;

    }
}
