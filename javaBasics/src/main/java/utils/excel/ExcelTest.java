package utils.excel;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;

public class ExcelTest {

    public static void main(String[] args) throws Exception {
//        testHSSF();
//        System.out.println("完成");
//        Excel2007AboveOperate("D:/workbook.xlsx");

//        long start = System.currentTimeMillis();
//        testXHSSF();
//        testSXSSF();
//        System.out.println("耗时：["+(System.currentTimeMillis() - start)+"]");//4437   1317   4558

        writerPostil1();

    }


    public static void testSS() throws IOException {
        Workbook[] wbs = {new HSSFWorkbook(), new XSSFWorkbook()};
        for (int i = 0; i < wbs.length; i++) {
            Workbook wb = wbs[i];
            CreationHelper creationHelper = wb.getCreationHelper();
            Sheet sheet = wb.createSheet();
            for (int j = 0; j < 10; j++) {
                Row row = sheet.createRow(j);
                Cell cell = row.createCell(0);
                cell.setCellValue(creationHelper.createRichTextString("ABC"));
            }
            String filename = "D:/workbook.xls";
            if (wb instanceof XSSFWorkbook) {
                filename = filename + "x";
            }
            wb.write(new FileOutputStream(filename));
//            wb.close();
        }
    }

    private static void  testXHSSF() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("第一个");
        for (int i = 0; i < 200000; i++) {
            XSSFRow row = sheet.createRow(i);
            for (int j = 0; j < 10; j = j + 2) {
                XSSFCell cell = row.createCell(j);
                cell.setCellValue("Spring");

                XSSFCell cell2 = row.createCell(j+1);
                cell2.setCellValue("aaa");
            }
        }
        FileOutputStream fos = new FileOutputStream("D:/wb1.xls");
        workbook.write(fos);
    }


    private static void  testSXSSF() throws IOException {
//        SXSSFWorkbook wb = new SXSSFWorkbook(100);
//        SXSSFSheet sheet = wb.createSheet("kao");
//
//        for (int i = 0; i < 200000; i++) {
//            SXSSFRow row = sheet.createRow(i);
//            for (int j = 0; j < 10; j = j + 2) {
//                SXSSFCell cell = row.createCell(j);
//                cell.setCellValue("Spring");
//                SXSSFCell cell2 = row.createCell(j+1);
//                cell2.setCellValue("aaa");
//            }
//        }
//        FileOutputStream fos = new FileOutputStream("D:/wb1.xls");
//        wb.write(fos);
    }

    public static void testHSSF() throws Exception {
        //  创建一个工作簿
        HSSFWorkbook wb = new HSSFWorkbook();

        HSSFSheet sheet1 = wb.createSheet("什么啊");//多次调用就创建多个sheet页

        //  创建一个工作表
        HSSFSheet sheet = wb.createSheet();

        //  创建字体
        HSSFFont font1 = wb.createFont();
        HSSFFont font2 = wb.createFont();
        font1.setFontHeightInPoints((short) 14);
//        font1.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
        font2.setFontHeightInPoints((short) 12);
//        font2.setColor(HSSFColor.HSSFColorPredefined.BLUE.getIndex());
        //  创建单元格样式
        HSSFCellStyle css1 = wb.createCellStyle();
        HSSFCellStyle css2 = wb.createCellStyle();
        HSSFDataFormat df = wb.createDataFormat();
        //  设置单元格字体及格式
        css1.setFont(font1);
        css1.setDataFormat(df.getFormat("#,##0.0"));
        css2.setFont(font2);
        css2.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));

        //  创建行
        for (int i = 0; i < 20000; i++) {
            HSSFRow row = sheet.createRow(i);
            for (int j = 0; j < 10; j = j + 2) {
                HSSFCell cell = row.createCell(j);
                cell.setCellValue("Spring");
                cell.setCellStyle(css1);

                HSSFCell cell2 = row.createCell(j+1);
                cell2.setCellValue(new HSSFRichTextString("Hello! " + j));
                cell2.setCellStyle(css2);
            }
        }

        //  写文件
        FileOutputStream fos = new FileOutputStream("D:/wb.xls");
        wb.write(fos);
        fos.close();
    }



    public static void Excel2007AboveOperate(String filePath) throws IOException {
        XSSFWorkbook workbook1 = new XSSFWorkbook(new FileInputStream(new File(filePath)));
        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(workbook1,100);
//            Workbook workbook = WorkbookFactory.create(new FileInputStream(new File(filePath)));
        Sheet first = sxssfWorkbook.getSheetAt(0);
        for (int i = 0; i < 100000; i++) {
            Row row = first.createRow(i);
            for (int j = 0; j < 11; j++) {
                if(i == 0) {
                    // 首行
                    row.createCell(j).setCellValue("column" + j);
                } else {
                    // 数据
                    if (j == 0) {
                        CellUtil.createCell(row, j, String.valueOf(i));
                    } else
                        CellUtil.createCell(row, j, String.valueOf(Math.random()));
                }
            }
        }
        FileOutputStream out = new FileOutputStream("workbook.xlsx");
        sxssfWorkbook.write(out);
        out.close();
    }


    /**
     * Excel批注
     */
    private static void writerPostil() throws IOException {
        //创建工作簿对象
        HSSFWorkbook wb=new HSSFWorkbook();
        //创建工作表对象
        HSSFSheet sheet=wb.createSheet("我的工作表");
        //创建绘图对象
        HSSFPatriarch p=sheet.createDrawingPatriarch();
        //创建单元格对象,批注插入到4行,1列,B5单元格
        HSSFCell cell=sheet.createRow(4).createCell(1);
        //插入单元格内容
        cell.setCellValue(new HSSFRichTextString("批注"));

        //获取批注对象
        //(int dx1, int dy1, int dx2, int dy2, short col1, int row1, short col2, int row2)
        //前四个参数是坐标点,后四个参数是编辑和显示批注时的大小.
        HSSFComment comment=p.createComment(new HSSFClientAnchor(0,0,0,0,(short)3,3,(short)5,6));
        //输入批注信息
        comment.setString(new HSSFRichTextString("插件批注成功!插件批注成功!"));
        //添加作者,选中B5单元格,看状态栏
        comment.setAuthor("toad");
        //将批注添加到单元格对象中
        cell.setCellComment(comment);

        //创建输出流
        FileOutputStream out=new FileOutputStream("D:/writerPostil.xls");

        wb.write(out);
        //关闭流对象
        out.close();
    }


    private static void writerPostil1() throws IOException {
        //创建工作簿对象
        SXSSFWorkbook workbook=new SXSSFWorkbook();
        //创建工作表对象
        Sheet sheet=workbook.createSheet("我的工作表");

//        Row headerRow = sheet.createRow(6);


        int i = 0;

//        for (int i = 0; i < 10; i++){
            Cell cell = sheet.createRow(5).createCell(6);

            cell.setCellValue("数据" + i);
            Comment comment = sheet.createDrawingPatriarch().createCellComment(
                    new XSSFClientAnchor(0, 0, 0, 0, (short) 3, 3, (short) 5, 6));
            comment.setString(new XSSFRichTextString("批注" + i));
            cell.setCellComment(comment);


//            Drawing draw1 = sheet.createDrawingPatriarch();
//            Comment ct1 = draw1.createCellComment(new XSSFClientAnchor(0, 0, 0,
//                    0, (short) 3, 3, (short) 9, 10));
//            ct1.setString(new XSSFRichTextString("批注" + i));
//            cell.setCellComment(ct1);

//        }



        FileOutputStream out=new FileOutputStream("D:/writerPostil.xls");
        workbook.write(out);
        //关闭流对象
        out.close();
    }

}
