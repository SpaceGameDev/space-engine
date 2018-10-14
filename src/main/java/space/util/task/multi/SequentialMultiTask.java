package space.util.task.multi;

import space.util.task.Task;

import java.util.Collection;
import java.util.Iterator;

public class SequentialMultiTask extends AbstractMultiTask {
	
	public SequentialMultiTask(Collection<? extends Task> tasks) {
		super(barrier -> {
			Iterator<? extends Task> iter = tasks.iterator();
			
			//0 tasks
			if (!iter.hasNext()) {
				return barrier;
			}
			
			//first task
			Task current = iter.next();
			barrier.addHook(current::submit);
			
			//middle tasks
			while (iter.hasNext()) {
				Task next = iter.next();
				current.addHook(next::submit);
				current = next;
			}
			
			//last task
			return current;
		});
	}
}
