package space.util.logger.prefix;

import space.util.logger.LogMessage;

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
		logMessage.prefix.append(startChar).append(dateFormat.format(new Date())).append(endChar);
	}
}
