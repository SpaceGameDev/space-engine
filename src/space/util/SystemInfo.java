package space.util;

import java.util.Properties;

public class SystemInfo {
	
	public static volatile SystemInfo SYSTEMINFO = new SystemInfo();
	public final Properties properties;
	public final Runtime runtime;
	public final String os;
	public final String userDir;
	public final int archBits;
	public final int coreCnt;
	public final long memoryMax;
	
	public SystemInfo() {
		properties = System.getProperties();
		runtime = Runtime.getRuntime();
		
		os = properties.getProperty("sun.desktop");
		userDir = properties.getProperty("user.dir");
		archBits = properties.getProperty("os.arch").contains("64") ? 64 : 32;
		coreCnt = runtime.availableProcessors();
		memoryMax = runtime.maxMemory();
	}
	
	public static void reloadSystemInfo() {
		SYSTEMINFO = new SystemInfo();
	}
}
