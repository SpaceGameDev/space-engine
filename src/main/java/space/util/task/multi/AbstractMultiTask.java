package space.util.task.multi;

import org.jetbrains.annotations.NotNull;
import space.util.sync.barrier.Barrier;
import space.util.sync.barrier.BarrierImpl;
import space.util.task.TaskState;
import space.util.task.impl.AbstractTask;

import java.util.function.Function;

import static space.util.task.TaskState.CREATED;

public class AbstractMultiTask extends AbstractTask {
	
	protected final BarrierImpl submitBarrier;
	protected final Barrier endBarrier;
	
	protected volatile @NotNull TaskState state = CREATED;
	
	public AbstractMultiTask(Function<Barrier, Barrier> function) {
		endBarrier = function.apply(submitBarrier = new BarrierImpl());
		endBarrier.addHook(this::executionFinished);
	}
	
	@Override
	protected void submit0() {
		submitBarrier.triggerNow();
	}
}
