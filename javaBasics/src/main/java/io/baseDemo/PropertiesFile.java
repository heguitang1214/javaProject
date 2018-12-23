package io.baseDemo;

import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.*;
import java.util.Enumeration;
import java.util.Properties;

public class PropertiesFile {

    /**
     * 输出所有配置信息
     */
    private static void printAllProperty(Properties props) {
        @SuppressWarnings("rawtypes")
        Enumeration en = props.propertyNames();
        while (en.hasMoreElements()) {
            String key = (String) en.nextElement();
            String value = props.getProperty(key);
            System.out.println(key + " : " + value);
        }
    }

    /**
     * 读取配置文件所有信息
     * 第一种方式：根据文件名使用Spring中的工具类进行解析
     * filePath是相对路劲，文件需在classpath目录下
     * 比如：config.properties在包com.test.config下，
     * 路径就是com/test/config/config.properties
     */
    public static void getProperties_1(String filePath) {
        Properties prop;
        try {
            // 通过Spring中的PropertiesLoaderUtils工具类进行获取
            prop = PropertiesLoaderUtils.loadAllProperties(filePath);
            printAllProperty(prop);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 读取配置文件所有信息
     * 第二种方式：使用缓冲输入流读取配置文件，然后将其加载，再按需操作
     * 绝对路径或相对路径， 如果是相对路径，则从当前项目下的目录开始计算，
     * 如：当前项目路径/config/config.properties,
     * 相对路径就是config/config.properties
     */
    public static void getProperties_2(String filePath) {
        Properties prop = new Properties();
        try {
            // 通过输入缓冲流进行读取配置文件
            InputStream InputStream = new BufferedInputStream(new FileInputStream(new File(filePath)));
            // 加载输入流
            prop.load(InputStream);
            printAllProperty(prop);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取配置文件所有信息
     * 第三种方式：
     * 相对路径， properties文件需在classpath目录下，
     * 比如：config.properties在包com.test.config下，
     * 路径就是/com/test/config/config.properties
     */
    public static void getProperties_3(String filePath) {
        Properties prop = new Properties();
        try {
            InputStream inputStream = PropertiesFile.class.getResourceAsStream(filePath);
            prop.load(inputStream);
            printAllProperty(prop);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    ============================================================================================================

    /**
     * 1.根据key读取value
     * <p>
     * 第一种方式：根据文件名使用spring中的工具类进行解析
     * filePath是相对路径，文件需在classpath目录下
     * 比如：config.properties在包com.test.config下，
     * 路径就是com/test/config/config.properties
     */
    public static String getProperties_1(String filePath, String keyWord) {
        Properties prop;
        String value = null;
        try {
            // 通过Spring中的PropertiesLoaderUtils工具类进行获取
            prop = PropertiesLoaderUtils.loadAllProperties(filePath);
            // 根据关键字查询相应的值
            value = prop.getProperty(keyWord);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 2.根据key读取value
     * 第三种方式：使用缓冲输入流读取配置文件，然后将其加载，再按需操作
     * 绝对路径或相对路径， 如果是相对路径，则从当前项目下的目录开始计算，
     * 如：当前项目路径/config/config.properties,
     * 相对路径就是config/config.properties
     */
    private static String getProperties_2(String filePath, String keyWord) {
        Properties prop = new Properties();
        String value = null;
        try {
            // 通过输入缓冲流进行读取配置文件
            InputStream InputStream = new BufferedInputStream(new FileInputStream(new File(filePath)));
            // 加载输入流
            prop.load(InputStream);
            // 根据关键字获取value值
            value = prop.getProperty(keyWord);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 3.根据key读取value
     * 第三种方式：
     * 相对路径， properties文件需在classpath目录下，
     * 比如：config.properties在包com.test.config下，
     * 路径就是/com/test/config/config.properties
     */
    public static String getProperties_3(String filePath, String keyWord) {
        Properties prop = new Properties();
        String value = null;
        try {
            InputStream inputStream = PropertiesFile.class.getResourceAsStream(filePath);
            prop.load(inputStream);
            value = prop.getProperty(keyWord);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }


    public static void main(String[] args) {
        System.out.println(getProperties_2("src/main/resources/json.properties", "name"));
    }

}
