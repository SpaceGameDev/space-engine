package space.util.logger.impl.prefix;

public abstract class AbstractPrefix implements Prefix {
	
	public char start;
	public char end;
	
	public AbstractPrefix() {
		this('[', ']');
	}
	
	public AbstractPrefix(char start, char end) {
		this.start = start;
		this.end = end;
	}
}
