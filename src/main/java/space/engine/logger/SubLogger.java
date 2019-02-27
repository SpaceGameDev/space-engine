package space.engine.logger;

import org.jetbrains.annotations.NotNull;
import space.engine.sync.barrier.Barrier;

public class SubLogger extends AbstractLogger {
	
	public AbstractLogger parent;
	public String name;
	
	public SubLogger(AbstractLogger parent, String name) {
		this.parent = parent;
		this.name = name;
	}
	
	//subLogger
	@NotNull
	@Override
	public String name() {
		return name;
	}
	
	@Override
	public Logger parentLogger() {
		return parent;
	}
	
	@NotNull
	@Override
	public Logger subLogger(String name) {
		return new SubLogger(this, name);
	}
	
	//log
	@Override
	public Barrier logDirect0(LogMessage msg) {
		return parent.logDirect(msg);
	}
}
