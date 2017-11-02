package space.util.string.toStringHelperOld.objects;

import space.util.string.CharSequence2D;
import space.util.string.String2D;
import space.util.string.toStringHelperOld.ToStringHelper;
import space.util.string.toStringHelperOld.ToStringHelperInstance;

public interface TSHObjects extends ToStringHelper {
	
	TSHObjectsInstance getInstance(Object obj);
	
	interface TSHObjectsInstance extends ToStringHelperInstance {
		
		//primitive
		void add(String name, byte obj);
		
		void add(String name, short obj);
		
		void add(String name, int obj);
		
		void add(String name, long obj);
		
		void add(String name, float obj);
		
		void add(String name, double obj);
		
		void add(String name, boolean obj);
		
		void add(String name, char obj);
		
		//array
		default void add(String name, byte[] obj) {
			add(name, obj, 0, obj.length);
		}
		
		default void add(String name, short[] obj) {
			add(name, obj, 0, obj.length);
		}
		
		default void add(String name, int[] obj) {
			add(name, obj, 0, obj.length);
		}
		
		default void add(String name, long[] obj) {
			add(name, obj, 0, obj.length);
		}
		
		default void add(String name, float[] obj) {
			add(name, obj, 0, obj.length);
		}
		
		default void add(String name, double[] obj) {
			add(name, obj, 0, obj.length);
		}
		
		default void add(String name, boolean[] obj) {
			add(name, obj, 0, obj.length);
		}
		
		default void add(String name, char[] obj) {
			add(name, obj, 0, obj.length);
		}
		
		//array from to
		void add(String name, byte[] obj, int from, int to);
		
		void add(String name, short[] obj, int from, int to);
		
		void add(String name, int[] obj, int from, int to);
		
		void add(String name, long[] obj, int from, int to);
		
		void add(String name, float[] obj, int from, int to);
		
		void add(String name, double[] obj, int from, int to);
		
		void add(String name, boolean[] obj, int from, int to);
		
		void add(String name, char[] obj, int from, int to);
		
		//object
		void add(String name, Object obj);
		
		default void add(String name, Object[] obj) {
			add(name, obj, 0, obj.length);
		}
		
		void add(String name, Object[] obj, int from, int to);
		
		//extra
		default void add(String name, String obj) {
			add(name, (CharSequence) obj);
		}
		
		default void add(String name, CharSequence obj) {
			add(name, (Object) obj);
		}
		
		default void add(String name, String2D obj) {
			add(name, (CharSequence2D) obj);
		}
		
		default void add(String name, CharSequence2D obj) {
			add(name, (Object) obj);
		}
	}
}
