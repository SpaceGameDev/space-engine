package space.util.logger;

public class SubLogger extends AbstractLogger {
	
	public AbstractLogger parent;
	public String name;
	
	public SubLogger(AbstractLogger parent, String name) {
		this.parent = parent;
		this.name = name;
	}
	
	//subLogger
	@Override
	public String name() {
		return name;
	}
	
	@Override
	public Logger parentLogger() {
		return parent;
	}
	
	@Override
	public Logger subLogger(String name) {
		return new SubLogger(this, name);
	}
	
	//log
	@Override
	public void logDirect0(LogMessage msg) {
		parent.logDirect(msg);
	}
}
