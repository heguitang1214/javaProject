package springDemo.aopAnnotation.dao.impl;

import entry.User;
import org.springframework.stereotype.Component;
import springDemo.aopAnnotation.dao.UserDAO;

/**
 * @author he_guitang
 * @version [1.0 , 2018/8/31]
 */
@Component("u2")
public class UserDAOImpl2 implements UserDAO {

    @Override
    public void save(User user) {
        System.out.println("UserDAOImpl2 user saved!");
    }


}
