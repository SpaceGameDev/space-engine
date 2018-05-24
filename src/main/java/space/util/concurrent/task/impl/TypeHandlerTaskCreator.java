package space.util.concurrent.task.impl;

import org.jetbrains.annotations.NotNull;
import space.util.concurrent.task.Task;
import space.util.concurrent.task.creator.TaskCreator;
import space.util.concurrent.task.typehandler.TypeHandler;

public class TypeHandlerTaskCreator<FUNCTION> implements TaskCreator<FUNCTION> {
	
	public FUNCTION func;
	
	public TypeHandlerTaskCreator(FUNCTION func) {
		this.func = func;
	}
	
	@NotNull
	@Override
	public Task create(@NotNull TypeHandler<FUNCTION> handler) {
		return new TypeHandlerTask(handler);
	}
	
	public class TypeHandlerTask extends AbstractRunnableTask {
		
		public TypeHandler<FUNCTION> handler;
		
		public TypeHandlerTask(TypeHandler<FUNCTION> handler) {
			this.handler = handler;
		}
		
		@Override
		protected void run0() {
			handler.accept(func);
		}
	}
}
