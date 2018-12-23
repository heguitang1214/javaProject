package io.fileCuttingMerging;

import java.io.File;
import java.io.FilenameFilter;

/**
 * 文件后缀名过滤器
 * @author heguitang
 */
public class SuffixFile implements FilenameFilter {
	private String suffix;
	SuffixFile(String suffix){
		super();
		this.suffix = suffix;
	}
	@Override
	public boolean accept(File dir, String name) {
		return name.endsWith(suffix);
	}

}
