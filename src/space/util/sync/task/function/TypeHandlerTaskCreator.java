package space.util.sync.task.function;

import space.util.sync.task.basic.AbstractRunnableTask;
import space.util.sync.task.basic.ITask;
import space.util.sync.task.function.creator.IFunctionTaskCreator;
import space.util.sync.task.function.typehandler.ITypeHandler;

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
