package space.engine.logger;

import org.jetbrains.annotations.NotNull;
import space.engine.sync.barrier.Barrier;

public abstract class AbstractLogger implements Logger {
	
	public LogLevel minLevel = LogLevel.INFO;
	
	public void setMinLevel(LogLevel minLevel) {
		this.minLevel = minLevel;
	}
	
	public final Barrier logDirect(@NotNull LogMessage msg) {
		if (minLevel.allowLog(msg.level))
			return logDirect0(msg);
		return Barrier.ALWAYS_TRIGGERED_BARRIER;
	}
	
	public abstract Barrier logDirect0(LogMessage msg);
}
