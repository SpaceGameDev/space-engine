package space.util.ref.freeable;

import space.util.baseobject.additional.Freeable;
import space.util.concurrent.event.SimpleEvent;
import space.util.concurrent.task.typehandler.TypeRunnable;
import space.util.logger.LogLevel;
import space.util.logger.Logger;
import space.util.string.builder.CharBufferBuilder2D;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Consumer;

public final class FreeableReferenceCleaner {
	
	public static final ReferenceQueue<Object> QUEUE = new ReferenceQueue<>();
	public static final FreeableReferenceList LIST_ROOT = new FreeableReferenceList(0);
	
	//require restart
	public static String cleanupThreadName = "FreeableReferenceCleaner";
	public static int cleanupThreadPriority = 8;
	public static SimpleEvent<Runnable> cleanupThreadInitCall = new SimpleEvent<>();
	public static boolean cleanupThreadDaemon = false;
	
	//instant apply
	public static volatile Consumer<Reference<?>> cleanupThreadIllegalReference = ref -> {
		throw new IllegalArgumentException("Inappropriate Reference of type " + ref.getClass().getName() + ": " + ref);
	};
	public static volatile Logger cleanupLogger;
	public static volatile boolean cleanupLoggerDebug = false;
	
	//instance
	public static ThreadInfo cleanupThreadInfo;
	
	public static synchronized void startCleanupThread() throws IllegalStateException {
		if (cleanupThreadInfo != null)
			throw new IllegalStateException("Cleaner already started!");
		
		ThreadInfo info = new ThreadInfo();
		Thread thread = info.thread = new Thread(() -> {
			cleanupThreadInitCall.run(TypeRunnable.INSTANCE);
			
			while (info.doRun) {
				try {
					//return == null -> retry
					Reference<?> ref1 = QUEUE.remove();
					if (ref1 == null)
						continue;
					
					//one reference, no further -> handle
					Reference<?> ref2 = QUEUE.poll();
					if (ref2 == null) {
						handleReference(ref1);
						continue;
					}
					
					//more than two references
					//-> collect
					ArrayList<IFreeableReference> list = new ArrayList<>();
					handleReferenceOrAdd(list, ref1);
					handleReferenceOrAdd(list, ref2);
					Reference<?> ref;
					while ((ref = QUEUE.poll()) != null) {
						handleReferenceOrAdd(list, ref);
					}
					
					//-> sort
					list.sort(Comparator.comparingInt(IFreeableReference::rootDistance));
					
					//-> log if logger exists
					if (cleanupLogger != null) {
						if (cleanupLoggerDebug)
							cleanupLogger.log(LogLevel.INFO, new CharBufferBuilder2D<>().append("Cleaning up ").append(list.size()).append(" Objects: ").append(list).toString());
						else
							cleanupLogger.log(LogLevel.INFO, new CharBufferBuilder2D<>().append("Cleaning up ").append(list.size()).append(" Objects").toString());
					}
					
					//-> handle
					list.forEach(Freeable::free);
				} catch (InterruptedException ignore) {
				
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		});
		thread.setName(cleanupThreadName);
		thread.setPriority(cleanupThreadPriority);
		thread.setDaemon(cleanupThreadDaemon);
		thread.start();
		
		cleanupThreadInfo = info;
	}
	
	public static void handleReference(Reference<?> ref) {
		if (ref instanceof IFreeableReference)
			((IFreeableReference) ref).free();
		else
			cleanupThreadIllegalReference.accept(ref);
	}
	
	private static void handleReferenceOrAdd(ArrayList<IFreeableReference> array, Reference<?> ref) {
		if (ref instanceof IFreeableReference)
			array.add((IFreeableReference) ref);
		else
			cleanupThreadIllegalReference.accept(ref);
	}
	
	public static synchronized void stopCleanupThread() {
		if (cleanupThreadInfo == null)
			throw new IllegalStateException("");
		
		cleanupThreadInfo.doRun = false;
		cleanupThreadInfo.thread.interrupt();
		cleanupThreadInfo = null;
	}
	
	public static synchronized void restartCleanupThread() {
		if (cleanupThreadInfo != null)
			stopCleanupThread();
		startCleanupThread();
	}
	
	public static synchronized boolean hasCleanupThread() {
		return cleanupThreadInfo != null;
	}
	
	private static final class ThreadInfo {
		
		Thread thread;
		volatile boolean doRun = true;
	}
}
