package space.engine.task.impl;

import org.jetbrains.annotations.NotNull;
import space.engine.sync.barrier.Barrier;

public class DelayTaskException extends Exception {
	
	public final @NotNull Barrier barrier;
	
	public DelayTaskException(@NotNull Barrier barrier) {
		super(null, null, false, false);
		this.barrier = barrier;
	}
}
