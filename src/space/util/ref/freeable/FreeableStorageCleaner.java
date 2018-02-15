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

public final class FreeableStorageCleaner {
	
	public static final ReferenceQueue<Object> QUEUE = new ReferenceQueue<>();
	public static final SimpleEvent<Runnable> CLEANUP_THREAD_INIT_CALL = new SimpleEvent<>();
	public static volatile Logger cleanupLogger;
	public static volatile boolean cleanupLoggerDebug = false;
	public static volatile Consumer<Reference<?>> cleanupThreadIllegalReference = ref -> {
		throw new IllegalArgumentException("Inappropriate Reference of type " + ref.getClass().getName() + ": " + ref);
	};
	
	//instance
	public static ThreadInfo cleanupThreadInfo;
	
	public static void setCleanupLogger(Logger cleanupLogger) {
		setCleanupLogger(cleanupLogger, false);
	}
	
	public static void setCleanupLogger(Logger cleanupLogger, boolean debug) {
		FreeableStorageCleaner.cleanupLogger = cleanupLogger.subLogger("Cleanup");
		FreeableStorageCleaner.cleanupLoggerDebug = debug;
	}
	
	//start
	public static synchronized void startCleanupThread() throws IllegalStateException {
		if (cleanupThreadInfo != null)
			stopCleanupThread();
		
		ThreadInfo info = new ThreadInfo();
		Thread thread = info.thread = new Thread(() -> {
			CLEANUP_THREAD_INIT_CALL.run(TypeRunnable.INSTANCE);
			
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
					ArrayList<IFreeableStorage> list = new ArrayList<>();
					handleReferenceOrAdd(list, ref1);
					handleReferenceOrAdd(list, ref2);
					Reference<?> ref;
					while ((ref = QUEUE.poll()) != null) {
						handleReferenceOrAdd(list, ref);
					}
					
					//-> sort
					list.sort(Comparator.comparingInt(IFreeableStorage::freePriority).reversed());
					
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
		thread.setName("FreeableStorageCleaner");
		thread.setPriority(8);
		thread.setDaemon(false);
		thread.start();
		
		cleanupThreadInfo = info;
	}
	
	private static final class ThreadInfo {
		
		Thread thread;
		volatile boolean doRun = true;
	}
	
	private static void handleReference(Reference<?> ref) {
		if (ref instanceof IFreeableStorage)
			((IFreeableStorage) ref).free();
		else
			cleanupThreadIllegalReference.accept(ref);
	}
	
	private static void handleReferenceOrAdd(ArrayList<IFreeableStorage> array, Reference<?> ref) {
		if (ref instanceof IFreeableStorage)
			array.add((IFreeableStorage) ref);
		else
			cleanupThreadIllegalReference.accept(ref);
	}
	
	//stop
	public static synchronized void stopCleanupThread() {
		if (cleanupThreadInfo == null)
			throw new IllegalStateException("No CleanupThread started!");
		
		cleanupThreadInfo.doRun = false;
		cleanupThreadInfo.thread.interrupt();
		cleanupThreadInfo = null;
	}
	
	public static synchronized boolean hasCleanupThread() {
		return cleanupThreadInfo != null;
	}
}
