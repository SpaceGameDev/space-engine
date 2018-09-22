package space.util.task.impl;

import org.jetbrains.annotations.NotNull;
import space.util.barrier.Barrier;
import space.util.barrier.BarrierImpl;
import space.util.task.Task;
import space.util.task.TaskState;

import static space.util.task.TaskState.*;

public abstract class AbstractTask extends BarrierImpl implements Task {
	
	@SuppressWarnings("NullableProblems") //value is assigned when state is > CREATED / after submit() call
	protected @NotNull TaskState state = CREATED;
	
	//change state
	@NotNull
	@Override
	public Task submit() {
		if (state != CREATED)
			throw new IllegalStateException("Can only submit() in State " + CREATED + ", was in State " + state);
		state = SUBMITTED;
		
		submit0();
		return this;
	}
	
	@NotNull
	@Override
	public synchronized Task submit(Barrier... barriers) {
		if (state != CREATED)
			throw new IllegalStateException("Can only submit() in State " + CREATED + ", was in State " + state);
		state = AWAITING_EVENTS;
		
		Barrier.awaitAll(() -> {
			if (state != AWAITING_EVENTS)
				throw new IllegalStateException("Can only have Barrier callback in State " + AWAITING_EVENTS + ", was in State " + state);
			state = SUBMITTED;
			submit0();
		}, barriers);
		return this;
	}
	
	protected abstract void submit0();
	
	//getter state
	
	@NotNull
	@Override
	public synchronized TaskState getState() {
		return state;
	}
}
