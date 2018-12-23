package springDemo.aopAnnotation.service;

import entry.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import springDemo.aopAnnotation.dao.UserDAO;


@Component("userService")
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    private UserDAO userDAO;

    /**
     * 构造器注入
     * @param userDAO spring容器要先有实现了实现userDAO接口的bean(UserDaoImpl/UserDaoImpl2)，
     *                然后再注入UserDAO,也就是相当于set方式注入进来(给该类的userDAO属性赋值)。
     *
     *   当userDAO接口有多个的时候,需要使用注解@Qualifier来指定具体的实现类
     */
    public UserService(@Qualifier("u") UserDAO userDAO) {
        logger.info("使用构造器的方式注入了UserDao的实现");
        this.userDAO = userDAO;
    }

    public void init() {
        System.out.println("init");
    }

    public void add(User user) {
        userDAO.save(user);
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    /**
     * set方式注入
     *  覆盖构造器的注入
     */
//    @Resource(name = "u")
    @Autowired
    public void setUserDAO1(@Qualifier("u2") UserDAO userDAO) {
        logger.info("使用Set()方式注入了UserDao的实现");
        this.userDAO = userDAO;
    }


    public void destroy() {
        System.out.println("destroy");
    }
}
