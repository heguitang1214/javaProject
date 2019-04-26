import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.math.BigInteger;

/**
 * 对称加密DES算法
 * @author Administrator
 *
 */
public class DesUtil {

    public static void main(String[] args) throws Exception {
        DesUtil desUtil = new DesUtil("L6PfT1qKA3nOyoA1");
        String data = "989300100350";
        //加密
        String hex = desUtil.encryption(data);
        logger.info(hex);

        logger.info("-----------------------------------------");
        // 解密
        String origin =desUtil.decryption(hex);
        logger.info(origin);
    }

    public static final Logger logger = LoggerFactory.getLogger(DesUtil.class);
    /** 算法名称 **/
    static final String ALGORITHM = "DES";

    /** 算法名称/加密模式/填充方式   **/
    static final String CIPHER_ALGORITHM = "DES/ECB/PKCS5Padding";

    /** 密钥，getBytes的长度必须大于等于8 **/
    private String SECRET_KEY = "L6PfT1qKA3nOyoA1".substring(0,8);

    public DesUtil(String secretKey) {
        this.SECRET_KEY = secretKey;
    }

    /**
     * 加密，返回16进制的字符串
     * @param data
     * @return
     * @throws Exception
     */
    public String encryption(String data) throws Exception{
        // 创建密钥
        DESKeySpec desKeySpec = new DESKeySpec(SECRET_KEY.getBytes("UTF-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        // 加密
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] bs = cipher.doFinal(data.getBytes("UTF-8"));
        // 编码成16进制字符串
        BigInteger bi = new BigInteger(1, bs);
        return bi.toString(16);
    }

    /**
     * 解密
     * @param hex
     * @return
     * @throws Exception
     */
    public String decryption(String hex) throws Exception {
        // 解码16进制字符串
        BigInteger bi = new BigInteger(hex, 16);
        byte[] bs = bi.toByteArray();// 该数组包含此 BigInteger 的二进制补码表示形式。
        byte[] originBs = new byte[bs.length - 1];
        byte[] target = bs;
        if (bs[0] == 0) {
            logger.info("去补码...");
            System.arraycopy(bs, 1, originBs, 0, originBs.length); // 去掉补码
            target = originBs;
        }
        // 创建密钥
        DESKeySpec desKeySpec = new DESKeySpec(SECRET_KEY.getBytes("UTF-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        // 解密
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptionBs = cipher.doFinal(target);
        return new String(decryptionBs, "UTF-8");
    }
}