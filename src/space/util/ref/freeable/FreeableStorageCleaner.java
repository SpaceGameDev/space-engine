package space.util.ref.freeable;

import space.util.baseobject.additional.Freeable;
import space.util.logger.LogLevel;
import space.util.logger.Logger;
import space.util.string.builder.CharBufferBuilder2D;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Consumer;

public final class FreeableStorageCleaner {
	
	static {
		initFinalCleanup();
	}
	
	private static boolean initWasCalled = false;
	public static final ReferenceQueue<Object> QUEUE = new ReferenceQueue<>();
	
	//instance
	public static ThreadInfo cleanupThreadInfo;
	public static volatile Logger cleanupLogger;
	public static volatile boolean cleanupLoggerDebug = false;
	public static volatile Consumer<Reference<?>> cleanupThreadIllegalReference = ref -> {
		throw new IllegalArgumentException("Inappropriate Reference of type " + ref.getClass().getName() + ": " + ref);
	};
	
	private static final class ThreadInfo {
		
		Thread thread;
		volatile boolean doRun = true;
	}
	
	//logger
	public static void setCleanupLogger(Logger cleanupLogger) {
		setCleanupLogger(cleanupLogger, false);
	}
	
	public static void setCleanupLogger(Logger cleanupLogger, boolean debug) {
		FreeableStorageCleaner.cleanupLogger = cleanupLogger.subLogger("Cleanup");
		FreeableStorageCleaner.cleanupLoggerDebug = debug;
	}
	
	//stop
	public static synchronized void stopCleanupThread() {
		if (cleanupThreadInfo == null)
			throw new IllegalStateException("No CleanupThread started!");
		
		cleanupThreadInfo.doRun = false;
		cleanupThreadInfo.thread.interrupt();
		cleanupThreadInfo = null;
	}
	
	public static void stopAndJoinCleanupThread() throws InterruptedException {
		Thread thread;
		synchronized (FreeableStorageCleaner.class) {
			if (cleanupThreadInfo == null)
				return;
			
			cleanupThreadInfo.doRun = false;
			thread = cleanupThreadInfo.thread;
			thread.interrupt();
			cleanupThreadInfo = null;
		}
		thread.join();
	}
	
	public static synchronized boolean hasCleanupThread() {
		return cleanupThreadInfo != null;
	}
	
	//start
	public static synchronized void startCleanupThread() throws IllegalStateException {
		if (cleanupThreadInfo != null)
			stopCleanupThread();
		
		ThreadInfo info = new ThreadInfo();
		Thread thread = info.thread = new Thread(() -> {
			while (info.doRun) {
				try {
					handle(0);
				} catch (InterruptedException ignore) {
				
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		});
		thread.setName("FreeableStorageCleaner");
		thread.setPriority(8);
		thread.setDaemon(true);
		thread.start();
		
		cleanupThreadInfo = info;
	}
	
	//handle
	static void handle(long timeout) throws InterruptedException {
		handle(QUEUE.remove(timeout));
	}
	
	static void handle(Reference<?> ref1) {
		//ref1 == null -> retry
		if (ref1 == null)
			return;
		
		//one reference, no further -> handle
		Reference<?> ref2 = QUEUE.poll();
		if (ref2 == null) {
			handleReference(ref1);
			return;
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
	
	//finalCleanup
	static void initFinalCleanup() {
		synchronized (FreeableStorageCleaner.class) {
			if (initWasCalled)
				return;
			initWasCalled = true;
		}
		
		Thread thread = new Thread(() -> {
			try {
				stopAndJoinCleanupThread();
				FreeableStorageTest.LIST_ROOT.free();
				
				System.gc();
				System.runFinalization();
				
				handle(100);
			} catch (InterruptedException e) {
				System.out.println("FreeableStorageShutdown was interrupted! Not all resources may have been freed!");
			}
		});
		thread.setName("FreeableStorageShutdown");
		thread.setPriority(8);
		thread.setDaemon(false);
		Runtime.getRuntime().addShutdownHook(thread);
	}
}
