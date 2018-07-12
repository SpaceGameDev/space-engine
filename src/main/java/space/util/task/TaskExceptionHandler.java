package space.util.task;

@FunctionalInterface
public interface TaskExceptionHandler {
	
	void uncaughtException(Task task, Thread thread, Throwable e);
}
