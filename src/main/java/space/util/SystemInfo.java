package space.util;

import java.util.Properties;

public class SystemInfo {
	
	public static final String os;
	public static final String userDir;
	public static final int archBits;
	public static final boolean is64Bit;
	public static final int coreCnt;
	public static final long memoryMax;
	
	static {
		Properties properties = System.getProperties();
		Runtime runtime = Runtime.getRuntime();
		
		os = properties.getProperty("sun.desktop");
		userDir = properties.getProperty("user.dir");
		is64Bit = properties.getProperty("os.arch").contains("64");
		archBits = is64Bit ? 64 : 32;
		coreCnt = runtime.availableProcessors();
		memoryMax = runtime.maxMemory();
	}
}
