package utilsTest;

import org.junit.Test;
import utils.DESUtils;

public class DESUtilTest {

    @Test
    public void test1() throws Exception {

        String key = "123456";   //key
//        String cleartext = "123456";   //加密字符
        String cleartext = "cuckoo123";  //c15588e588dc756cd15e44a4552c2050
        System.out.println("key---" + key + "\ncleartext---" + cleartext);
        String s1 = DESUtils.encrypt(key, cleartext);
//        logger.info("加密后---" + s1);
        System.out.println("加密后---" + s1);
        String s2 = DESUtils.decrypt(key, s1);
//        logger.info("解密后---" + s2);
        System.out.println("解密后---" + s2);

        System.out.println("===========================================================================");
        System.out.println("buguniao加密之后：" + DESUtils.encrypt("123456","buguniao"));

//        String sn = "3905ef4e11ae1aa94f1d1f4603f8b5b6";
        String sn = "f88731a6469359a8729ff166f5f3d014";
        String sn_1 = DESUtils.decrypt(key,sn);
        System.out.println("解密 {"+sn+"}  --> {" +sn_1+"} ");
    }

}
