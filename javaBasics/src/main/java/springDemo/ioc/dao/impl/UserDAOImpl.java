package springDemo.ioc.dao.impl;


import springDemo.ioc.dao.UserDAO;
import springDemo.ioc.model.User;

public class UserDAOImpl implements UserDAO {

    public void save(User user) {
        //Hibernate
        //JDBC
        //XML
        //NetWork
        System.out.println("user saved!");
    }

}
