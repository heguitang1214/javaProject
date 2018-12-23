package designPatterns.proxy.javaProxy.base;

/**
 * @author he_guitang
 * @version [1.0 , 2018/5/24]
 */

public class UserDao implements IUserDao, IUserInfoDao {

    @Override
    public void save() {
        System.out.println("保存数据操作----");
    }

    @Override
    public void update() {
        System.out.println("用户补充:修改数据操作----");
    }

}


