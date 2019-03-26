package space.engine.freeableStorage;

import org.jetbrains.annotations.Nullable;
import space.engine.baseobject.Freeable;
import space.engine.logger.Logger;
import space.engine.logger.NullLogger;
import space.engine.string.builder.CharBufferBuilder2D;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Consumer;

import static space.engine.logger.LogLevel.*;

public final class FreeableStorageCleaner {
	
	static {
		initialize();
	}
	
	//instance management
	private static boolean initWasCalled = false;
	@Nullable
	private static ThreadInfo cleanupThreadInfo;
	
	public static final ReferenceQueue<Object> QUEUE = new ReferenceQueue<>();
	public static volatile Logger cleanupLogger = NullLogger.NULL_LOGGER;
	public static volatile boolean cleanupLoggerDebug = false;
	public static volatile Consumer<Reference<?>> cleanupThreadIllegalReference = ref -> {
		throw new IllegalArgumentException("Inappropriate Reference of type " + ref.getClass().getName() + ": " + ref);
	};
	
	private static final class ThreadInfo {
		
		Thread thread;
		volatile boolean doRun = true;
	}
	
	//logger
	public static void setCleanupLogger(Logger baseLogger) {
		setCleanupLogger(baseLogger, false);
	}
	
	public static void setCleanupLogger(Logger baseLogger, boolean debug) {
		FreeableStorageCleaner.cleanupLogger = baseLogger.subLogger("Cleanup");
		FreeableStorageCleaner.cleanupLoggerDebug = debug;
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
		ArrayList<FreeableStorage> list = new ArrayList<>();
		handleReferenceOrAdd(list, ref1);
		handleReferenceOrAdd(list, ref2);
		Reference<?> ref;
		while ((ref = QUEUE.poll()) != null) {
			handleReferenceOrAdd(list, ref);
		}
		
		//-> sort
		list.sort(Comparator.comparingInt(FreeableStorage::freePriority).reversed());
		
		//-> log if logger exists
		if (cleanupLoggerDebug)
			cleanupLogger.log(INFO, new CharBufferBuilder2D<>().append("Cleaning up ").append(list.size()).append(" Objects via GC: ").append(list).toString());
		else
			cleanupLogger.log(INFO, new CharBufferBuilder2D<>().append("Cleaning up ").append(list.size()).append(" Objects via GC").toString());
		
		//-> handle
		list.forEach(Freeable::free);
	}
	
	private static void handleReference(Reference<?> ref) {
		if (ref instanceof FreeableStorage) {
			if (cleanupLoggerDebug)
				cleanupLogger.log(INFO, new CharBufferBuilder2D<>().append("Cleaning up 1 Objects via GC: [").append(ref).append(']').toString());
			else
				cleanupLogger.log(INFO, new CharBufferBuilder2D<>().append("Cleaning up 1 Objects via GC").toString());
			((FreeableStorage) ref).free();
		} else
			cleanupThreadIllegalReference.accept(ref);
	}
	
	private static void handleReferenceOrAdd(ArrayList<FreeableStorage> array, Reference<?> ref) {
		if (ref instanceof FreeableStorage)
			array.add((FreeableStorage) ref);
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
	
	//isRunning
	public static synchronized boolean hasCleanupThread() {
		return cleanupThreadInfo != null;
	}
	
	//finalCleanup
	static void initialize() {
		synchronized (FreeableStorageCleaner.class) {
			if (initWasCalled)
				return;
			initWasCalled = true;
		}
		
		startCleanupThread();
		
		Thread thread = new Thread(() -> {
			Logger logger = cleanupLogger.subLogger("final");
			logger.log(INFO, "Final cleanup...");
			logger.log(INFO, "1. Stopping cleanup thread");
			try {
				stopAndJoinCleanupThread();
			} catch (InterruptedException ignore) {
				
			}
			logger.log(INFO, "2. direct free");
			FreeableStorage.ROOT_LIST.free();
			
			logger.log(INFO, "3. gc free");
			System.gc();
			System.runFinalization();
			
			try {
				handle(100);
				logger.log(INFO, "Final cleanup complete!");
			} catch (InterruptedException e) {
				logger.log(ERROR, "FreeableStorageCleanup shutdown procedure was interrupted! Not all resources may have been freed!");
			}
		});
		thread.setName("FreeableStorageShutdown");
		thread.setPriority(8);
		thread.setDaemon(false);
		Runtime.getRuntime().addShutdownHook(thread);
	}
}
