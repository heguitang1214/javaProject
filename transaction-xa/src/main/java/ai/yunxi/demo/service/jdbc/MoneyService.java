package ai.yunxi.demo.service.jdbc;

public interface MoneyService {
	public void transfer(int fromUser, int toUser, int money) throws Exception;
}
