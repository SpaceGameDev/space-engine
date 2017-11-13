package space.util.logger.impl.prefix;

import space.util.logger.impl.LogMessage;

import java.text.DateFormat;
import java.util.Date;

public class TimePrefix extends AbstractPrefix {
	
	public DateFormat dateFormat;
	
	public TimePrefix(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}
	
	public TimePrefix(char start, char end, DateFormat dateFormat) {
		super(start, end);
		this.dateFormat = dateFormat;
	}
	
	@Override
	public void accept(LogMessage logMessage) {
		logMessage.prefix.append(start).append(dateFormat.format(new Date())).append(end);
	}
}
