package utils.office;

import org.apache.poi.xslf.usermodel.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class PPTturnImage {

    public static void main(String[] args) {
        File ppt = new File("D:\\test.pptx");
        File img = new File("D:\\test\\");
        List<String> list = new ArrayList<>();
        System.out.println(doPPT2007toImage(ppt,img,list));
    }

    private static int doPPT2007toImage(File pptFile, File imgFile, List<String> list) {
        FileInputStream is;
        int imgCount = 0;
        try {
            is = new FileInputStream(pptFile);
            XMLSlideShow xmlSlideShow = new XMLSlideShow(is);
            is.close();
            // 获取大小
            Dimension pgsize = xmlSlideShow.getPageSize();
            // 获取幻灯片
            XSLFSlide[] slides = xmlSlideShow.getSlides();
            imgCount = slides.length;
            for (int i = 0 ; i < slides.length ; i++) {
                // 解决乱码问题
                XSLFShape[] shapes = slides[i].getShapes();
                for (XSLFShape shape : shapes) {
                    if (shape instanceof XSLFTextShape) {
                        XSLFTextShape sh = (XSLFTextShape) shape;
                        List<XSLFTextParagraph> textParagraphs = sh.getTextParagraphs();
                        for (XSLFTextParagraph xslfTextParagraph : textParagraphs) {
                            List<XSLFTextRun> textRuns = xslfTextParagraph.getTextRuns();
                            for (XSLFTextRun xslfTextRun : textRuns) {
                                xslfTextRun.setFontFamily("宋体");
                            }
                        }
                    }
                }
                //根据幻灯片大小生成图片
                BufferedImage img = new BufferedImage(pgsize.width,pgsize.height, BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics = img.createGraphics();
                graphics.setPaint(Color.white);
                graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width,pgsize.height));
                // 最核心的代码
                slides[i].draw(graphics);
                //图片将要存放的路径
                String absolutePath = imgFile.getAbsolutePath()+"/"+ (i + 1) + ".jpg";
                File jpegFile = new File(absolutePath);
                // 图片路径存放
                list.add((i + 1) + ".jpg");
                //如果图片存在，则不再生成
                if (jpegFile.exists()) {
                    continue;
                }
                // 这里设置图片的存放路径和图片的格式(jpeg,png,bmp等等),注意生成文件路径
                FileOutputStream out = new FileOutputStream(jpegFile);
                // 写入到图片中去
                ImageIO.write(img, "jpeg", out);
                out.close();
            }
            System.out.print("PPT转换成图片 成功！");
            //log.error("PPT转换成图片 成功！");
            return imgCount;
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.print("PPT转换成图片 发生异常！");
           // log.error("PPT转换成图片 发生异常！", e);
        }
        return imgCount;
    }
}
