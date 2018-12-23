package io.fileCuttingMerging;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Properties;


/**
 * 文件合并
 * @author heguiatng
 */
public class FileMerge {
	/**
	 * 文件合并
	 * 
	 * @param dir 文件
	 * @throws IOException IO异常
	 */
	private static void mergeFile(File dir) throws IOException {
		File[] files = dir.listFiles(new SuffixFile(".properties"));
		if (files == null || files.length != 1) {
			throw new RuntimeException(dir + "目录下没有properties扩展文件或者不唯一");
		}
		File confile = files[0];
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(confile);
		prop.load(fis);
		String filename = prop.getProperty("filename");
		int count = Integer.parseInt(prop.getProperty("partcount"));
		File[] partFiles = dir.listFiles(new SuffixFile(".part"));
		if (partFiles == null || partFiles.length != (count - 1)) {
			throw new RuntimeException("碎片文件个数不符合要求,应该是[" + count + "]个！");
		}
		ArrayList<FileInputStream> arr = new ArrayList<>();
		for (File partFile : partFiles) {
			arr.add(new FileInputStream(partFile));
		}
		Enumeration<FileInputStream> en = Collections.enumeration(arr);
		SequenceInputStream sis = new SequenceInputStream(en);
		FileOutputStream fos = new FileOutputStream(new File(dir, filename));// 合并后的文件路径
		byte[] buf = new byte[1024];
		int len = 0;
		while ((len = sis.read(buf)) != -1) {
			fos.write(buf, 0, len);
		}
		fos.close();
		sis.close();
	}
	
	public static void main(String[] args) throws IOException {
		long start = System.currentTimeMillis();
		mergeFile(new File("D:\\partfiles"));
		long end = System.currentTimeMillis();
		System.out.println("文件合并成功,耗时"+(end - start)+"s");
	}
}
