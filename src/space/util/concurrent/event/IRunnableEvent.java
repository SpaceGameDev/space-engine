package space.util.concurrent.event;

import space.util.concurrent.task.typehandler.ITypeHandler;

/**
 * An {@link IRunnableEvent} is something you can {@link IEvent#addHook(Object)} and {@link IEvent#removeHook(Object)} Hooks,
 * which called when the {@link IEvent} is triggered. <b>It is triggered by the {@link IRunnableEvent#run(ITypeHandler)} method in contrast to {@link IEvent}.</b>
 * It is expected that the Method {@link IEvent#removeHook(Object)} will be called very rarely and mustn't be optimized via hashing or other techniques.
 */
public interface IRunnableEvent<FUNCTION> extends IEvent<FUNCTION> {
	
	void run(ITypeHandler<FUNCTION> type);
}
