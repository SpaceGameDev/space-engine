package space.util.string.toStringHelper;

import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractToStringHelperObjectsInstance<T> implements ToStringHelperObjectsInstance<T> {
	
	public ToStringHelper<T> helper;
	public List<Entry<T>> list = new ArrayList<>();
	
	public AbstractToStringHelperObjectsInstance(ToStringHelper<T> helper) {
		this.helper = helper;
	}
	
	@Override
	public String toString() {
		return build().toString();
	}
	
	//add
	@Override
	public void add(String name, byte obj) {
		list.add(new Entry<>(name, helper.toString(obj)));
	}
	
	@Override
	public void add(String name, short obj) {
		list.add(new Entry<>(name, helper.toString(obj)));
	}
	
	@Override
	public void add(String name, int obj) {
		list.add(new Entry<>(name, helper.toString(obj)));
	}
	
	@Override
	public void add(String name, long obj) {
		list.add(new Entry<>(name, helper.toString(obj)));
	}
	
	@Override
	public void add(String name, float obj) {
		list.add(new Entry<>(name, helper.toString(obj)));
	}
	
	@Override
	public void add(String name, double obj) {
		list.add(new Entry<>(name, helper.toString(obj)));
	}
	
	@Override
	public void add(String name, boolean obj) {
		list.add(new Entry<>(name, helper.toString(obj)));
	}
	
	@Override
	public void add(String name, char obj) {
		list.add(new Entry<>(name, helper.toString(obj)));
	}
	
	@Override
	public void add(String name, byte[] obj, int from, int to) {
		list.add(new Entry<>(name, helper.toString(obj, from, to)));
	}
	
	@Override
	public void add(String name, short[] obj, int from, int to) {
		list.add(new Entry<>(name, helper.toString(obj, from, to)));
	}
	
	@Override
	public void add(String name, int[] obj, int from, int to) {
		list.add(new Entry<>(name, helper.toString(obj, from, to)));
	}
	
	@Override
	public void add(String name, long[] obj, int from, int to) {
		list.add(new Entry<>(name, helper.toString(obj, from, to)));
	}
	
	@Override
	public void add(String name, float[] obj, int from, int to) {
		list.add(new Entry<>(name, helper.toString(obj, from, to)));
	}
	
	@Override
	public void add(String name, double[] obj, int from, int to) {
		list.add(new Entry<>(name, helper.toString(obj, from, to)));
	}
	
	@Override
	public void add(String name, boolean[] obj, int from, int to) {
		list.add(new Entry<>(name, helper.toString(obj, from, to)));
	}
	
	@Override
	public void add(String name, char[] obj, int from, int to) {
		list.add(new Entry<>(name, helper.toString(obj, from, to)));
	}
	
	@Override
	public void add(String name, Object obj) {
		list.add(new Entry<>(name, helper.toString(obj)));
	}
	
	@Override
	public void add(String name, Object[] obj, int from, int to) {
		list.add(new Entry<>(name, helper.toString(obj, from, to)));
	}
	
	public static class Entry<T> {
		
		public String name;
		public T value;
		
		public Entry(String name, T value) {
			this.name = name;
			this.value = value;
		}
		
		@Override
		public String toString() {
			return name + ": " + value;
		}
	}
}
