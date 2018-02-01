package space.util.concurrent.task.creator;

import space.util.concurrent.task.ITask;
import space.util.concurrent.task.impl.AbstractRunnableTask;
import space.util.concurrent.task.typehandler.ITypeHandler;

public class SimpleTaskCreator<FUNCTION> implements ITaskCreator<FUNCTION> {
	
	public FUNCTION func;
	
	public SimpleTaskCreator() {
	}
	
	public SimpleTaskCreator(FUNCTION func) {
		this.func = func;
	}
	
	@Override
	public ITask create(ITypeHandler<FUNCTION> handler) {
		return new AbstractRunnableTask() {
			@Override
			protected void run0() {
				handler.accept(func);
			}
		};
	}
}
