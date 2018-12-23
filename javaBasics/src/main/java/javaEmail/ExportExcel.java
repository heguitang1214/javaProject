package javaEmail;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
//下面是和数据导出有关的包
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExportExcel {
//	public void Export() {
	public ByteArrayOutputStream Export() {
		// 声明一个工作薄
		HSSFWorkbook wb = new HSSFWorkbook();
		// 声明一个单子并命名
		HSSFSheet sheet = wb.createSheet("学生表");
		// 给单子名称一个长度
		sheet.setDefaultColumnWidth((int) 15);
		// 生成一个样式
		HSSFCellStyle style = wb.createCellStyle();
		// 创建第一行（也可以称为表头）
		HSSFRow row = sheet.createRow(0);
		// 样式字体居中
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 给表头第一行一次创建单元格
		HSSFCell cell = row.createCell((int) 0);
		cell.setCellValue("学生编号");
		cell.setCellStyle(style);
		cell = row.createCell((int) 1);
		cell.setCellValue("学生姓名");
		cell.setCellStyle(style);
		cell = row.createCell((int) 2);
		cell.setCellValue("学生性别");
		cell.setCellStyle(style);

		// 添加一些数据，这里先写死，大家可以换成自己的集合数据
		List<Student> list = new ArrayList<Student>();
		list.add(new Student(111, "张三", "男"));
		list.add(new Student(222, "李四", "男"));
		list.add(new Student(333, "王五", "女"));
		list.add(new Student(444, "赵七", "女"));

		// 向单元格里填充数据
		for (int i = 0; i < list.size(); i++) {
			row = sheet.createRow(i + 1);
			row.createCell(0).setCellValue(list.get(i).getId());
			row.createCell(1).setCellValue(list.get(i).getName());
			row.createCell(2).setCellValue(list.get(i).getSex());
		}

		try {
			// 默认导出到E盘下
//			FileOutputStream out = new FileOutputStream("D:\\java\\test\\学生表.xls");
//			PipedInputStream in = new PipedInputStream();

			//将excel，输出到流中
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			wb.write(out);
			return out;
//			out.close();
//			JOptionPane.showMessageDialog(null, "导出成功!");
		} catch (FileNotFoundException e) {
//			JOptionPane.showMessageDialog(null, "导出失败!");
			e.printStackTrace();
		} catch (IOException e) {
//			JOptionPane.showMessageDialog(null, "导出失败!");
			e.printStackTrace();
		}
		return null;
	}
}
