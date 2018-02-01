package space.util.concurrent.task.impl;

import space.util.concurrent.task.ITask;
import space.util.concurrent.task.creator.ITaskCreator;
import space.util.concurrent.task.typehandler.ITypeHandler;

public class TypeHandlerTaskCreator<FUNCTION> implements ITaskCreator<FUNCTION> {
	
	public FUNCTION func;
	
	public TypeHandlerTaskCreator(FUNCTION func) {
		this.func = func;
	}
	
	@Override
	public ITask create(ITypeHandler<FUNCTION> handler) {
		return new TypeHandlerTask(handler);
	}
	
	public class TypeHandlerTask extends AbstractRunnableTask {
		
		public ITypeHandler<FUNCTION> handler;
		
		public TypeHandlerTask(ITypeHandler<FUNCTION> handler) {
			this.handler = handler;
		}
		
		@Override
		protected void run0() {
			handler.accept(func);
		}
	}
}
