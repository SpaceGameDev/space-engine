package space.engine.environmentOld;

public class TheMain {
	
	public static ThreadLocal<ThreadEnvironment> theMain = ThreadLocal.withInitial(ThreadEnvironment::new);
	public static EnvironmentCollection collection = new EnvironmentCollection();
	
	public static ThreadEnvironment getMain() {
		return theMain.get();
	}
	
	public static void setEnvironment(Environment env) {
		getMain().env = env;
	}
	
	public static void setThLocal(ThLocal th) {
		getMain().th = th;
	}
	
	public static class ThreadEnvironment {
		
		public Environment env;
		public ThLocal th;
	}
	
	public static class EnvironmentCollection {
		
		public IntKeyMapIdBased<Environment> list = new IntKeyMapIdBased<>();
		
		public void add(Environment v) {
			list.put(v);
		}
		
		public Environment get(int id) {
			return list.get(id);
		}
		
		public void remove(int id) {
			list.remove(id);
		}
	}
}
