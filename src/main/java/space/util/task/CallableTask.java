package space.util.task;

import org.jetbrains.annotations.NotNull;
import space.util.future.Future;
import space.util.sync.barrier.Barrier;
import space.util.task.impl.CallableTaskImpl;

import java.util.function.Supplier;

public interface CallableTask<R> extends Task, Future<R> {
	
	static <R> CallableTaskImpl<R> create(Supplier<R> function) {
		return new CallableTaskImpl<>() {
			@Override
			protected R execute0() {
				return function.get();
			}
		};
	}
	
	@Override
	@NotNull CallableTask<R> submit();
	
	@Override
	@NotNull CallableTask<R> submit(@NotNull Barrier... barriers);
}
