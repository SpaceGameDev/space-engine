package space.util.ref.freeable;

import space.util.logger.impl.BaseLogger;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("UnusedAssignment")
public class FreeableStorageTest {
	
	public static final int THING_CNT = 8;
	public static final int GC_TIME_BETWEEN_RUNS = 100;
	public static final int GC_RUNS_BEFORE_ARRAY_CREATION = 0;
	public static final int GC_RUNS_AFTER_CREATION = 0;
	public static final int GC_RUNS_AFTER_NULLING = 4;
	
	//	public static boolean ALLOC_THING_FIRST = false;
	public static boolean CLEAR_PARENT = false;
	public static boolean CLEAR_ARRAY = true;
	
	public static IFreeableStorageList LIST_ROOT = FreeableStorageList.createList(0);
	
	public static void main(String[] args) throws Exception {
		BaseLogger logger = BaseLogger.defaultPrinter(BaseLogger.defaultHandler(new BaseLogger()));
		FreeableStorageCleaner.cleanupLogger = logger.subLogger("Cleanup");
		FreeableStorageCleaner.cleanupLoggerDebug = true;
		FreeableStorageCleaner.startCleanupThread();
		
		Parent p;
		ArrayList<Thing> array;
//		if (ALLOC_THING_FIRST) {
//			array = new ArrayList<>();
//			for (int i = 0; i < THING_CNT; i++)
//				array.add(new Thing());
//			runGC(GC_RUNS_BEFORE_ARRAY_CREATION);
//
//			Parent p2 = p = new Parent();
//			array.forEach(thing -> thing.init(p2));
//		} else {
		p = new Parent();
		runGC(GC_RUNS_BEFORE_ARRAY_CREATION);
		
		array = new ArrayList<>();
		for (int i = 0; i < THING_CNT; i++)
			array.add(new Thing(p));
//		}
		
		System.out.println("LIST_ROOT: " + LIST_ROOT);
		System.out.println("p.freeable.getSubList(): " + p.freeable.getSubList());
		
		runGC(GC_RUNS_AFTER_CREATION);
		
		if (CLEAR_PARENT)
			p = null;
		if (CLEAR_ARRAY)
			array = null;
		
		runGC(GC_RUNS_AFTER_NULLING);
		
		System.out.println("LIST_ROOT: " + LIST_ROOT);
		if (p != null)
			System.out.println("p.freeable.getSubList(): " + p.freeable.getSubList());
		
		Thread.sleep(1000);
		FreeableStorageCleaner.stopCleanupThread();
	}
	
	public static void runGC(int cnt) throws InterruptedException {
		for (int i = 0; i < cnt; i++) {
			System.out.println("GC #" + i);
			System.gc();
			System.runFinalization();
			Thread.sleep(GC_TIME_BETWEEN_RUNS);
		}
	}
	
	public static class Parent {
		
		static final AtomicInteger idGen = new AtomicInteger();
		
		ParentFreeable freeable = new ParentFreeable(this, LIST_ROOT, idGen.getAndIncrement());
	}
	
	public static class ParentFreeable extends FreeableStorage {
		
		int id;
		
		public ParentFreeable(Object referent, IFreeableStorage parent, int id) {
			super(referent, parent);
			this.id = id;
		}
		
		public ParentFreeable(Object referent, IFreeableStorageList parent, int id) {
			super(referent, parent);
			this.id = id;
		}
		
		@Override
		protected void handleFree() {
			System.out.println("Free of " + this.getClass() + ": " + this.toString());
		}
		
		@Override
		public String toString() {
			return "P" + id + "#" + freePriority();
		}
	}
	
	public static class Thing {
		
		static final AtomicInteger idGen = new AtomicInteger();
//		private static final Unsafe UNSAFE = UnsafeInstance.getUnsafeOrThrow();
//		static final long OFFSET_PARENT = UnsafeInstance.objectFieldOffset(FreeableStorage.class, "parent");
		
		Parent parent;
		ThingFreeable freeable;
		
		public Thing(Parent parent) {
			this.parent = parent;
			freeable = new ThingFreeable(this, parent.freeable, idGen.getAndIncrement());
		}

//		public Thing() {
//			freeable = new ThingFreeable(this, idGen.getAndIncrement());
//		}
//
//		public void init(Parent parent) {
//			this.parent = parent;
//
//			IFreeableStorageList subList = parent.freeable.getSubList();
//			UNSAFE.putObject(freeable, OFFSET_PARENT, subList);
//			subList.insert(freeable);
//		}
	}
	
	public static class ThingFreeable extends FreeableStorage {
		
		int id;
		
		public ThingFreeable(Object referent, IFreeableStorage parent, int id) {
			super(referent, parent);
			this.id = id;
		}
		
		public ThingFreeable(Object referent, IFreeableStorageList parent, int id) {
			super(referent, parent);
			this.id = id;
		}

//		public ThingFreeable(Object referent, int id) {
//			super(referent);
//			this.id = id;
//		}
		
		@Override
		protected void handleFree() {
			System.out.println("Free of " + this.getClass() + ": " + this.toString());
		}
		
		@Override
		public String toString() {
			return "T" + id + "#" + freePriority();
		}
	}
}
