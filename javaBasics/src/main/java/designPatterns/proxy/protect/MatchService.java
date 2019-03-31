package designPatterns.proxy.protect;

import java.lang.reflect.Proxy;

public class MatchService {
    public MatchService() {

        PersonBean joe = getPersonInfo("joe", "male", "running");

        PersonBean OwnerProxy = getOwnerProxy(joe);

        System.out.println("名字是：" + OwnerProxy.getName());
        System.out.println("兴趣是：" + OwnerProxy.getInterests());

        OwnerProxy.setInterests("Bowling");
        System.out.println("兴趣是：" + OwnerProxy.getInterests());
        OwnerProxy.setHotOrNotRating(50);
        System.out.println("评分为：" + OwnerProxy.getHotOrNotRating());
        OwnerProxy.setHotOrNotRating(40);
        System.out.println("评分为：" + OwnerProxy.getHotOrNotRating());

        System.out.println("**************");

        PersonBean nonOwnerProxy = getNonOwnerProxy(joe);
        System.out.println("名字是：" + nonOwnerProxy.getName());
        System.out.println("兴趣是：" + nonOwnerProxy.getInterests());
        nonOwnerProxy.setInterests("haha");
        System.out.println("兴趣是：" + nonOwnerProxy.getInterests());
        nonOwnerProxy.setHotOrNotRating(60);
        System.out.println("评分为：" + nonOwnerProxy.getHotOrNotRating());

    }

    PersonBean getPersonInfo(String name, String gender, String interests) {
        PersonBean person = new PersonBeanImpl();
        person.setName(name);
        person.setGender(gender);
        person.setInterests(interests);
        return person;
    }

    PersonBean getOwnerProxy(PersonBean person) {
        return (PersonBean) Proxy.newProxyInstance(person.getClass()
                        .getClassLoader(), person.getClass().getInterfaces(),
                new OwnerInvocationHandler(person));
    }

    PersonBean getNonOwnerProxy(PersonBean person) {
        return (PersonBean) Proxy.newProxyInstance(person.getClass()
                        .getClassLoader(), person.getClass().getInterfaces(),
                new NonOwnerInvocationHandler(person));
    }
}
