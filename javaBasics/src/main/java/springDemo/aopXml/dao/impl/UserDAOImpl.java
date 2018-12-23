package springDemo.aopXml.dao.impl;

import org.springframework.stereotype.Component;
import springDemo.aopXml.dao.UserDAO;
import springDemo.aopXml.model.User;


@Component("u") 
public class UserDAOImpl implements UserDAO {

	public void save(User user) {
		//Hibernate
		//JDBC
		//XML
		//NetWork
		System.out.println("user saved!");
		//throw new RuntimeException("exeption!");
	}

}
