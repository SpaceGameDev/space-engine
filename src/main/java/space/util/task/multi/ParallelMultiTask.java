package space.util.task.multi;

import space.util.barrier.Barrier;
import space.util.task.Task;

import java.util.Collection;

public class ParallelMultiTask extends AbstractMultiTask {
	
	public ParallelMultiTask(Collection<? extends Task> tasks) {
		super(barrier -> {
			if (tasks.size() == 0) {
				return barrier;
			}
			
			//custom handler for fever object allocation
			barrier.addHook(() -> tasks.forEach(Task::submit));
			return Barrier.awaitAll(tasks);
		});
	}
}
