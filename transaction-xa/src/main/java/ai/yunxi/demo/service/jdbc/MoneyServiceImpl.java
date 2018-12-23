package ai.yunxi.demo.service.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.yunxi.demo.dao.jdbc.MoneyDao;

@Service
public class MoneyServiceImpl implements MoneyService {

	@Autowired
	private MoneyDao moneyDao;

	// 转账操作
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void transfer(int fromUser, int toUser, int money) throws Exception {
		//减少某个账户上的金额
		moneyDao.decrease(fromUser, money);
		//增加某个账户上的金额
		moneyDao.increase(toUser, money);
		System.out.println("============> 转账成功！");
	}

}
