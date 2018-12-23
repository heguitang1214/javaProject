package javaEmail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PipedInputStream;

public class InputStreamUtil {

	public static InputStream getInputStream(){
		//输出流变成输入流
		try{
//			PipedInputStream in2 = new PipedInputStream();
			ExportExcel e = new ExportExcel();
			ByteArrayOutputStream byteOut = e.Export();
			byte[] byt = byteOut.toByteArray();
			ByteArrayInputStream byteIn = new ByteArrayInputStream(byt);
			return byteIn;
//			File f = new File("D:\\java\\test\\bbc.xlsx");
//			in = new PipedInputStream((PipedOutputStream)out);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
