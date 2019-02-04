package space.engine.task.impl;

import org.jetbrains.annotations.NotNull;
import space.engine.sync.barrier.Barrier;

public final class DelayTask extends RuntimeException {
	
	public final @NotNull Barrier barrier;
	
	public DelayTask(@NotNull Barrier barrier) {
		super(null, null, false, false);
		this.barrier = barrier;
	}
}
