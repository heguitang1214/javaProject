package springDemo.aop.service;

import springDemo.ioc.dao.impl.UserDAOImpl;
import springDemo.ioc.model.User;

/**
 * 实现在UserDAOImpl的save()前添加逻辑
 * 		方式一:继承,其缺点是耦合太强
 */
public class UserDAOImpl2 extends UserDAOImpl {

	@Override
	public void save(User user) {
		System.out.println("save start...");
		super.save(user);
	}
}
