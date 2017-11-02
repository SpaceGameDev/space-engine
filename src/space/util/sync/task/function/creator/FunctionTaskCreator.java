package space.util.sync.task.function.creator;

import space.util.sync.task.basic.AbstractRunnableTask;
import space.util.sync.task.basic.ITask;
import space.util.sync.task.function.typehandler.ITypeHandler;

public class FunctionTaskCreator<FUNCTION> implements IFunctionTaskCreator<FUNCTION> {
	
	public FUNCTION func;
	
	public FunctionTaskCreator() {
	}
	
	public FunctionTaskCreator(FUNCTION func) {
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
