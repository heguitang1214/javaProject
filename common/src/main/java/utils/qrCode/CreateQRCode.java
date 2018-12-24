package utils.qrCode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * 使用zxing生成普通的二维码
 */
public class CreateQRCode {

    public static void main(String[] args) {
        createQRCode();
    }

    private static void createQRCode(){
        //二维码图片的宽度
        int width = 300;
        //二维码图片的高度
        int height = 300;
        //二维码图片的格式
        String format = "jpg";
        //二维码内容（支持中文），使用微信扫描后可直接跳转到百度
//        String content = "https://www.baidu.com/";
        String content = "这是一个普通的二维码";

        //定义二维码内容参数
        Map<EncodeHintType, Object> hints = new HashMap<>();
        //设置字符集编码格式
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        //设置容错等级，在这里我们使用M级别
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        //设置边框距
        hints.put(EncodeHintType.MARGIN, 2);

        //生成二维码
        try {
            //生成二维码的内容
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            //指定生成图片的保存路径
            Path file = new File("D:\\qrcode.jpg").toPath();
            //生成二维码
            MatrixToImageWriter.writeToPath(bitMatrix, format, file);
            System.out.println("二维码生成成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
