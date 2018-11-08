package space.util.task.multi;

import org.jetbrains.annotations.NotNull;
import space.util.barrier.Barrier;
import space.util.barrier.BarrierImpl;
import space.util.task.Task;
import space.util.task.TaskState;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;

import static space.util.task.TaskState.*;

public class AbstractMultiTask implements Task {
	
	protected final BarrierImpl submitBarrier;
	protected final Barrier endBarrier;
	
	protected volatile @NotNull TaskState state = CREATED;
	
	public AbstractMultiTask(Function<Barrier, Barrier> function) {
		endBarrier = function.apply(submitBarrier = new BarrierImpl());
		endBarrier.addHook(() -> {
			synchronized (AbstractMultiTask.this) {
				if (state != SUBMITTED)
					throw new IllegalStateException("Can only start running in State " + SUBMITTED + ", was in State " + state);
				state = TaskState.FINISHED;
			}
		});
	}
	
	//modified version from AbstractTask
	@NotNull
	@Override
	public synchronized Task submit() {
		if (state != CREATED)
			throw new IllegalStateException("Can only submit() in State " + CREATED + ", was in State " + state);
		state = SUBMITTED;
		
		submitBarrier.triggerNow();
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
			submitBarrier.triggerNow();
		}, barriers);
		return this;
	}
	
	@NotNull
	@Override
	public TaskState getState() {
		return state;
	}
	
	//delegate to endBarrier
	@Override
	public void addHook(@NotNull Runnable run) {
		endBarrier.addHook(run);
	}
	
	@Override
	public void removeHook(@NotNull Runnable run) {
		endBarrier.removeHook(run);
	}
	
	@Override
	public void await() throws InterruptedException {
		endBarrier.await();
	}
	
	@Override
	public void await(long time, TimeUnit unit) throws InterruptedException, TimeoutException {
		endBarrier.await(time, unit);
	}
}
