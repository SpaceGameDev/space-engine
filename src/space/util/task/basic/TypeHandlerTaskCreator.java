package space.util.task.basic;

import space.util.task.ITask;
import space.util.task.basic.runnable.AbstractRunnableTask;
import space.util.task.creator.IFunctionTaskCreator;
import space.util.task.typehandler.ITypeHandler;

public class TypeHandlerTaskCreator<FUNCTION> implements IFunctionTaskCreator<FUNCTION> {
	
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
