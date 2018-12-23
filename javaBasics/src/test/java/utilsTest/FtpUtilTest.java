package utilsTest;

import org.junit.Test;
import utils.FtpUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FtpUtilTest {

    @Test
    public void imageUploadFile(){
        try {
            FileInputStream in = new FileInputStream(new File("D:/imageQRcode.jpg"));
            boolean flag = FtpUtil.uploadFile("47.93.194.11", 21, "image", "image", "/home/image", "/2018/12/07", "201812071.jpg", in);
            System.out.println(flag);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void imageDownloadFile(){
        try {
            boolean b = FtpUtil.downloadFile("47.93.194.11", 21, "image", "image", "/home/image/2018/12/07", "201812071.jpg", "D:");
            System.out.println(b);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
