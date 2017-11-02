package space.util.string.toStringHelper.objects;

import space.util.string.toStringHelper.ToStringHelperCollection;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTSHObjects implements TSHObjects {
	
	public ToStringHelperCollection helperCollection;
	
	@Override
	public void setToStringHelperCollection(ToStringHelperCollection coll) {
		this.helperCollection = coll;
	}
	
	public abstract class AbstractTSHObjectsInstance implements TSHObjectsInstance {
		
		public List<Entry> list = new ArrayList<>();
		
		@Override
		public void add(String name, byte obj) {
			list.add(new Entry(name, helperCollection.getNativeType().toString(obj)));
		}
		
		@Override
		public void add(String name, short obj) {
			list.add(new Entry(name, helperCollection.getNativeType().toString(obj)));
		}
		
		@Override
		public void add(String name, int obj) {
			list.add(new Entry(name, helperCollection.getNativeType().toString(obj)));
		}
		
		@Override
		public void add(String name, long obj) {
			list.add(new Entry(name, helperCollection.getNativeType().toString(obj)));
		}
		
		@Override
		public void add(String name, float obj) {
			list.add(new Entry(name, helperCollection.getNativeType().toString(obj)));
		}
		
		@Override
		public void add(String name, double obj) {
			list.add(new Entry(name, helperCollection.getNativeType().toString(obj)));
		}
		
		@Override
		public void add(String name, boolean obj) {
			list.add(new Entry(name, helperCollection.getNativeType().toString(obj)));
		}
		
		@Override
		public void add(String name, char obj) {
			list.add(new Entry(name, helperCollection.getNativeType().toString(obj)));
		}
		
		@Override
		public void add(String name, byte[] obj, int from, int to) {
			list.add(new Entry(name, helperCollection.getArray().toString(obj, from, to)));
		}
		
		@Override
		public void add(String name, short[] obj, int from, int to) {
			list.add(new Entry(name, helperCollection.getArray().toString(obj, from, to)));
		}
		
		@Override
		public void add(String name, int[] obj, int from, int to) {
			list.add(new Entry(name, helperCollection.getArray().toString(obj, from, to)));
		}
		
		@Override
		public void add(String name, long[] obj, int from, int to) {
			list.add(new Entry(name, helperCollection.getArray().toString(obj, from, to)));
		}
		
		@Override
		public void add(String name, float[] obj, int from, int to) {
			list.add(new Entry(name, helperCollection.getArray().toString(obj, from, to)));
		}
		
		@Override
		public void add(String name, double[] obj, int from, int to) {
			list.add(new Entry(name, helperCollection.getArray().toString(obj, from, to)));
		}
		
		@Override
		public void add(String name, boolean[] obj, int from, int to) {
			list.add(new Entry(name, helperCollection.getArray().toString(obj, from, to)));
		}
		
		@Override
		public void add(String name, char[] obj, int from, int to) {
			list.add(new Entry(name, helperCollection.getArray().toString(obj, from, to)));
		}
		
		@Override
		public void add(String name, Object obj) {
			list.add(new Entry(name, helperCollection.getNativeType().toString(obj)));
		}
		
		@Override
		public void add(String name, Object[] obj, int from, int to) {
			list.add(new Entry(name, helperCollection.getArray().toString(obj, from, to)));
		}
	}
	
	public static class Entry {
		
		public String name;
		public String value;
		
		public Entry(String name, String value) {
			this.name = name;
			this.value = value;
		}
		
		@Override
		public String toString() {
			return name + ": " + value;
		}
	}
}
