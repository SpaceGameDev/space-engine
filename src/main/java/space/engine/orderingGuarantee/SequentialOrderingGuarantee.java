package space.engine.orderingGuarantee;

import org.jetbrains.annotations.NotNull;
import space.engine.sync.barrier.Barrier;
import space.engine.sync.barrier.BarrierImpl;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.function.Function;

import static space.engine.sync.barrier.Barrier.ALWAYS_TRIGGERED_BARRIER;

public class SequentialOrderingGuarantee {
	
	private static final VarHandle LASTBARRIER;
	
	static {
		try {
			MethodHandles.Lookup l = MethodHandles.lookup();
			LASTBARRIER = l.findVarHandle(SequentialOrderingGuarantee.class, "lastBarrier", Barrier.class);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new ExceptionInInitializerError(e);
		}
	}
	
	private Barrier lastBarrier = ALWAYS_TRIGGERED_BARRIER;
	
	public void next(Function<@NotNull ? super Barrier, @NotNull ? extends Barrier> function) {
		BarrierImpl next = new BarrierImpl();
		Barrier prev = (Barrier) LASTBARRIER.getAndSet(this, next);
		function.apply(prev).addHook(next::triggerNow);
	}
	
	/**
	 * For debugging and highly controlled testing purposes only!
	 */
	@Deprecated
	public Barrier getLatestBarrier() {
		return (Barrier) LASTBARRIER.getVolatile(this);
	}
}
