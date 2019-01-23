package space.util.logger;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.util.string.CharSequence2D;
import space.util.string.String2D;

public interface Logger {
	
	//subLogger
	@NotNull String name();
	
	@NotNull Logger subLogger(String name);
	
	@Nullable Logger parentLogger();
	
	//log
	default void log(@NotNull LogLevel level, @NotNull String str) {
		logDirect(new LogMessage(this, level, new String2D(str)));
	}
	
	default void log(@NotNull LogLevel level, @NotNull CharSequence2D str) {
		logDirect(new LogMessage(this, level, str));
	}
	
	void logDirect(@NotNull LogMessage msg);
}
