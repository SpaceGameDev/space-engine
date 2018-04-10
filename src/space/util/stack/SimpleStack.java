package space.util.stack;

import space.util.indexmap.IndexMapArray;

import java.util.Arrays;

public class SimpleStack<T> implements Stack<T> {
	
	public IndexMapArray<T> list = new IndexMapArray<>();
	
	@Override
	public void push(T t) {
		list.add(t);
	}
	
	@Override
	public long pushPointer(T t) {
		int ret = list.size();
		list.put(ret, t);
		return ret;
	}
	
	@Override
	public T pop() {
		return list.remove(list.size() - 1);
	}
	
	@Override
	public T popPointer(long id) {
		int l = list.size() - 1;
		int i = (int) (id + 1);
		if (i > l)
			Arrays.fill(list.array, i, l, null);
		
		return list.remove((int) id);
	}
}
