package space.engine.logger;

import org.jetbrains.annotations.NotNull;
import space.engine.string.CharSequence2D;
import space.engine.sync.barrier.Barrier;

/**
 * all_msg > /dev/null
 */
public class NullLogger implements Logger {
	
	public static final NullLogger NULL_LOGGER = new NullLogger();
	
	private NullLogger() {
	}
	
	//subLogger
	@NotNull
	@Override
	public String name() {
		return "null";
	}
	
	@Override
	public Logger parentLogger() {
		return null;
	}
	
	@NotNull
	@Override
	public Logger subLogger(String name) {
		return this;
	}
	
	//log
	@Override
	public void log(@NotNull LogLevel level, @NotNull String str) {
	
	}
	
	@Override
	public void log(@NotNull LogLevel level, @NotNull CharSequence2D str) {
	
	}
	
	@Override
	public Barrier logDirect(@NotNull LogMessage msg) {
		return Barrier.ALWAYS_TRIGGERED_BARRIER;
	}
}
