package space.util.concurrent.task.creator;

import space.util.concurrent.task.Task;
import space.util.concurrent.task.impl.AbstractRunnableTask;
import space.util.concurrent.task.typehandler.TypeHandler;

public class SimpleTaskCreator<FUNCTION> implements TaskCreator<FUNCTION> {
	
	public FUNCTION func;
	
	public SimpleTaskCreator() {
	}
	
	public SimpleTaskCreator(FUNCTION func) {
		this.func = func;
	}
	
	@Override
	public Task create(TypeHandler<FUNCTION> handler) {
		return new AbstractRunnableTask() {
			@Override
			protected void run0() {
				handler.accept(func);
			}
		};
	}
}
