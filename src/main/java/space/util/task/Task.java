package space.util.task;

import org.jetbrains.annotations.NotNull;
import space.util.awaitable.Awaitable;
import space.util.event.basic.BasicEvent;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A {@link Task} is something which is created to be executed by some thread in a protected environment,
 * signaling back it's execution, completion and error states. It also allows for Hooks to be added and to be awaited on.
 */
public interface Task extends BasicEvent<Consumer<Task>>, Awaitable {
	
	//run
	
	/**
	 * Submits the tasks work to the executor.
	 * Should only be called once. Calling it multiple times may lead to undefined behavior.
	 * An implementation can choose to submit multiple {@link Runnable}s.
	 *
	 * @param executor to submit any tasks to
	 */
	void submit(@NotNull Executor executor);
	
	/**
	 * Cancels the execution of the task.
	 *
	 * @param mayInterrupt if it should set the interrupt flags on all threads executing any work for this task
	 * @return true if the task was canceled early enough to not have finished, meant for informational purpose
	 */
	boolean cancel(boolean mayInterrupt);
	
	//state
	
	/**
	 * Check whether the execution of this Task has started.
	 *
	 * @return true if it has started
	 */
	boolean executionStarted();
	
	/**
	 * Check whether the execution of this thread is done.
	 *
	 * @return true if execution is done
	 */
	boolean isDone();
	
	@Override
	default boolean isSignaled() {
		return isDone();
	}
	
	//result
	
	/**
	 * Gets the {@link TaskResult} of this {@link Task}.
	 * <p>This Method is NOT marked with @Nullable, as it will ONLY return null when the Task is not finished. When it IS finished it will always be NotNull.</p>
	 *
	 * @return the {@link TaskResult} of the {@link Task} or null, if not already finished
	 */
	TaskResult getResult();
	
	//await
	
	/**
	 * Waits until the {@link Task} is complete.
	 */
	@Override
	void await() throws InterruptedException;
	
	/**
	 * Waits until the {@link Task} is complete with a timeout.
	 */
	@Override
	void await(long time, TimeUnit unit) throws InterruptedException;
	
	//exception handler default
	
	/**
	 * Sets the default {@link TaskExceptionHandler}.
	 *
	 * @param handler the new {@link TaskExceptionHandler}.
	 */
	static void setDefaultExceptionHandler(TaskExceptionHandler handler) {
		setDefaultExceptionHandler(task -> handler);
	}
	
	/**
	 * Sets the default {@link TaskExceptionHandler}.
	 *
	 * @param handler the new default {@link TaskExceptionHandler}.
	 */
	static void setDefaultExceptionHandler(Function<Task, TaskExceptionHandler> handler) {
		DefaultTaskExceptionHandlerStorage.defaultHandler = handler;
	}
	
	/**
	 * Gets the default {@link TaskExceptionHandler}
	 *
	 * @return the default {@link TaskExceptionHandler}
	 */
	static Function<Task, TaskExceptionHandler> getDefaultExceptionHandler() {
		return DefaultTaskExceptionHandlerStorage.defaultHandler;
	}
	
	class DefaultTaskExceptionHandlerStorage {
		
		private static Function<Task, TaskExceptionHandler> defaultHandler;
		
		static {
			setDefaultExceptionHandler((task, thread, e) -> Thread.currentThread().getUncaughtExceptionHandler().uncaughtException(thread, e));
		}
	}
}
