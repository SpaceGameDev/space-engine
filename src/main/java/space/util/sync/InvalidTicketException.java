package space.util.sync;

public class InvalidTicketException extends RuntimeException {
	
	Object ticket;
	
	public InvalidTicketException(Object ticket) {
		super("Invalid ticket: " + ticket);
		this.ticket = ticket;
	}
}
