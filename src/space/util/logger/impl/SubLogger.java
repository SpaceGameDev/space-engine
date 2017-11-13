package space.util.logger.impl;

import space.util.logger.Logger;

public class SubLogger extends LoggerImpl {
	
	public LoggerImpl parent;
	public String name;
	
	public SubLogger(LoggerImpl parent, String name) {
		this.parent = parent;
		this.name = name;
	}
	
	@Override
	public void logDirect(LogMessage msg) {
		parent.logDirect(msg);
	}
	
	@Override
	public Logger subLogger(LoggerImpl parent, String name) {
		return parent.subLogger(parent, name);
	}
}
