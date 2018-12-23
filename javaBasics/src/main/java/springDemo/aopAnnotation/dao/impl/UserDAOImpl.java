package springDemo.aopAnnotation.dao.impl;

import entry.User;
import org.springframework.stereotype.Component;
import springDemo.aopAnnotation.dao.UserDAO;


@Component("u")
public class UserDAOImpl implements UserDAO {

	public void save(User user) {
		//Hibernate
		//JDBC
		//XML
		//NetWork
		System.out.println("UserDAOImpl user saved!");
		//throw new RuntimeException("exeption!");
	}

}
