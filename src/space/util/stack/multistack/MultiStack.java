package space.util.stack.multistack;

import space.util.ArrayUtils;
import space.util.stack.PointerList;

import java.util.Arrays;
import java.util.function.Consumer;

public class MultiStack<T> implements IMultiStack<T> {
	
	public static final int DEFAULT_START_SIZE = 16;
	
	public int size;
	public T[] array;
	
	public PointerList pointerList = new PointerList();
	public Consumer<T> onDelete;
	
	public MultiStack(int initsize) {
		this(initsize, null);
	}
	
	public MultiStack(Consumer<T> onDelete) {
		this(DEFAULT_START_SIZE, onDelete);
	}
	
	public MultiStack(int initSize, Consumer<T> onDelete) {
		this.size = 0;
		//noinspection unchecked
		this.array = (T[]) new Object[initSize];
		this.onDelete = onDelete;
	}
	
	public void ensureCapacity(int capa) {
		if (array.length < capa) {
			T[] old = array;
			//noinspection unchecked
			array = (T[]) new Object[ArrayUtils.getOptimalArraySizeExpansion(old.length, capa, 1)];
			System.arraycopy(old, 0, array, 0, old.length);
		}
	}
	
	@Override
	public <X extends T> X put(X t) {
		int pos = size++;
		ensureCapacity(size);
		array[pos] = t;
		return t;
	}
	
	//push
	@Override
	public void push() {
		pointerList.push(size);
	}
	
	@Override
	public long pushPointer() {
		return size;
	}
	
	//pop
	@Override
	public void pop() {
		popPointer(pointerList.pop());
	}
	
	@Override
	public void popPointer(long idl) {
		int id = (int) idl;
		if (onDelete != null)
			for (int i = id; i < size; i++)
				onDelete.accept(array[i]);
		
		Arrays.fill(array, id, size, null);
	}
}
