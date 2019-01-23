package space.util.baseobject;

import org.jetbrains.annotations.NotNull;
import space.util.string.String2D;

public interface Dumpable {
	
	String2D DUMP_CAP_REACHED = new String2D("Dump cap reached!");
	ThreadLocal<Long> MAX_DUMP = new ThreadLocal<>();
	
	static void setMaxDump(long maxDump) {
		MAX_DUMP.set(maxDump);
	}
	
	static long getMaxDump() {
		return MAX_DUMP.get();
	}
	
	@NotNull String2D dump();
}
