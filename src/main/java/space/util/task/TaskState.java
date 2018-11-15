package space.util.task;

import space.util.sync.barrier.Barrier;

public enum TaskState {
	
	/**
	 * A newly created {@link Task} will be in this state.<
	 * <p>
	 * Next possible states:
	 * <ul>
	 * <li>{@link #AWAITING_EVENTS}: if the {@link Task} should await any {@link Barrier Events} before executing</li>
	 * <li>{@link #SUBMITTED}: if the {@link Task} was submitted directly, without awaiting any events, or all events are finished</li>
	 * </ul>
	 */
	CREATED,
	
	/**
	 * When the {@link Task#submit(Barrier[])} Method is called, the {@link Task} will go in this State.
	 * <p>
	 * Next States:
	 * <ul>
	 * <li>{@link #SUBMITTED} As soon as all Events in the Array are finished</li>
	 * </ul>
	 */
	AWAITING_EVENTS,
	
	/**
	 * The {@link Task} was submitted to a Queue and awaits execution.
	 * <p>
	 * Next States:
	 * <ul>
	 * <li>{@link #RUNNING} the {@link Task} is getting executed (may be unsupported)</li>
	 * <li>{@link #FINISHED} It may skip {@link #RUNNING} and go to {@link #FINISHED},
	 * if such tracking is unsupported</li>
	 * </ul>
	 */
	SUBMITTED,
	
	/**
	 * The Task is currently getting executed by a {@link Thread}. <b>May be unsupported, causing the Task to skip this phase and go to {@link #FINISHED}</b>
	 * <p>
	 * Next States:
	 * <ul>
	 * <li>{@link #FINISHED} when the Task is done executing.</li>
	 * </ul>
	 */
	RUNNING,
	
	/**
	 * The Task is finished in it's execution.
	 * <p>
	 * Next States
	 * <ul>
	 * <li>NONE. This is a terminating State.</li>
	 * </ul>
	 */
	FINISHED
	
}
