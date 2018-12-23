package javaEmail;


import java.util.*;

public class JavaMailTest {

    public static void main(String[] args) {
        try {
            Map<String, String> hostMap = new HashMap<>();
//			JavaMialUtil mail = new JavaMialUtil("xx@qq.com", "iqcnrsxxyowkbiah");
//			JavaMialUtil mail = new JavaMialUtil("xx@qq.com", "umbzioxghhhwddib");//qq授权码
            JavaMialUtil mail = new JavaMialUtil("he_guitang@163.com", "hgt11391214abc");


            // 暂时未成功，需要调试
            /*
             * SendMail mail = new SendMail("14789****@sina.cn","***miya");
             * map.put("mail.smtp.host", "smtp.sina.com");
             */
//            hostMap.put("mail.smtp.host", "smtp.qq.com");//qq邮箱
//            hostMap.put("mail.smtp.auth", "true");
//            hostMap.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//            hostMap.put("mail.smtp.port", "465");
//            hostMap.put("mail.smtp.socketFactory.port", "465");


//            hostMap.put("smtp.126", "smtp.126.com");
//            // qq
//            hostMap.put("smtp.qq", "smtp.qq.com");
//
//            // 163
//            hostMap.put("smtp.163", "smtp.163.com");
//
//            // sina
//            hostMap.put("smtp.sina", "smtp.sina.com.cn");
//
//            // tom
//            hostMap.put("smtp.tom", "smtp.tom.com");
//
//            // 263
//            hostMap.put("smtp.263", "smtp.263.net");
//
//            // yahoo
//            hostMap.put("smtp.yahoo", "smtp.mail.yahoo.com");
//
//            // hotmail
//            hostMap.put("smtp.hotmail", "smtp.live.com");
//
//            // gmail
//            hostMap.put("smtp.gmail", "smtp.gmail.com");
//            hostMap.put("smtp.port.gmail", "465");

            hostMap.put("mail.smtp.host", "smtp.qiye.163.com");
            hostMap.put("mail.smtp.auth", "true");


            mail.setPros(hostMap);
            mail.initMessage();
            /*
             * 添加收件人有三种方法： 1,单人添加(单人发送)调用setRecipient(str);发送String类型
             * 2,多人添加(群发)调用setRecipients(list);发送list集合类型
             * 3,多人添加(群发)调用setRecipients(sb);发送StringBuffer类型
             */
            List<String> list = new ArrayList<>();
//            list.add("xx@qq.com");
            list.add("he_guitang@163.com");
            // list.add("***92@sina.cn");
            // list.add("****@163.com");
            mail.setRecipients(list);
            /*
             * String defaultStr =
             * "283942930@qq.com,429353942@qq.com,2355663819@qq.com,381766286@qq
             * .com; StringBuffer sb = new StringBuffer();
             * sb.append(defaultStr); sb.append(",316121113@qq.com");
             * mail.setRecipients(sb);
             */
            mail.setSubject("测试邮箱");
            // mail.setText("谢谢合作");
            mail.setDate(new Date());
            mail.setFrom("MY");
            // mail.setMultipart("D:你你你.txt");
            mail.setContent("谢谢合作,改版", "text/html; charset=UTF-8");

            List<String> fileList = new ArrayList<>();
//			fileList.add("D:1.jpg");
//			fileList.add("D:activation.zip");
//			fileList.add("D:dstz.sql");
//			fileList.add("D:\\java\\test\\bbc.xlsx");
//			mail.setMultiparts(fileList);

            mail.setMultiparts(fileList);
            System.out.println(mail.sendMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
