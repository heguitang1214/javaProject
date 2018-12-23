package springDemo.mySpring;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 11256 on 2018/8/24.
 * Spring容器
 * 解析Spring配置文件
 */
public class ClassPathXmlApplicationContext implements BeanFactory {

    private Map<String, Object> beans = new HashMap<>();

    //IOC Inverse of Control DI Dependency Injection
    public ClassPathXmlApplicationContext() throws Exception {
        SAXBuilder sb = new SAXBuilder();

        Document doc = sb.build(this.getClass().getClassLoader().getResourceAsStream("springConf/my_spring_beans.xml")); //构造文档对象
        Element root = doc.getRootElement(); //获取根元素HD
        List list = root.getChildren("bean");//取名字为disk的所有元素
        for (int i = 0; i < list.size(); i++) {
            Element element = (Element) list.get(i);
            String id = element.getAttributeValue("id");
            String clazz = element.getAttributeValue("class");
            Object o = Class.forName(clazz).newInstance();
            System.out.println(id);
            System.out.println(clazz);
            beans.put(id, o);

            for (Element propertyElement : (List<Element>) element.getChildren("property")) {
                String name = propertyElement.getAttributeValue("name"); //userDAO
                String bean = propertyElement.getAttributeValue("bean"); //u
                Object beanObject = beans.get(bean);//UserDAOImpl instance

                String methodName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
                System.out.println("method name = " + methodName);

                Method m = o.getClass().getMethod(methodName, beanObject.getClass().getInterfaces()[0]);
                m.invoke(o, beanObject);
            }
        }
    }


    @Override
    public Object getBean(String id) {
        return beans.get(id);
    }


}
