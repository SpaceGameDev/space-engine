package space.util.indexmap.multi;

import space.util.ArrayUtils;
import space.util.delegate.iterator.Iteratorable;
import space.util.delegate.list.IntList;

import java.util.Arrays;
import java.util.function.Supplier;

public interface IndexMultiMapLayered<VALUE> extends IndexMultiMap<VALUE> {
	
	boolean isExpandable(int[] pos, int offset);
	
	int size(int[] pos, int offset);
	
	boolean contains(int[] pos, int offset);
	
	VALUE get(int[] pos, int offset);
	
	VALUE remove(int[] pos, int offset);
	
	VALUE put(int[] pos, int offset, VALUE v);
	
	void clear(int[] pos, int offset);
	
	Iteratorable<IndexMultiMapEntry<VALUE>> table(int[] pos, int offset);
	
	@Override
	default boolean isExpandable(int[] pos) {
		return isExpandable(pos, 0);
	}
	
	@Override
	default int size(int[] pos) {
		return size(pos, 0);
	}
	
	@Override
	default boolean contains(int[] pos) {
		return contains(pos, 0);
	}
	
	@Override
	default VALUE get(int[] pos) {
		return get(pos, 0);
	}
	
	@Override
	default VALUE remove(int[] pos) {
		return remove(pos, 0);
	}
	
	@Override
	default VALUE put(int[] pos, VALUE v) {
		return put(pos, 0, v);
	}
	
	@Override
	default void clear(int[] pos) {
		clear(pos, 0);
	}
	
	@Override
	default Iteratorable<IndexMultiMapEntry<VALUE>> table(int[] pos) {
		return table(pos, 0);
	}
	
	class IndexMultiMapLayeredImpl<VALUE> implements IndexMultiMapLayered<VALUE> {
		
		public static int defaultCapacity = 4;
		public static int expandShift = 1;
		
		public Supplier<IndexMultiMapLayered> newLayers;
		public boolean newLayersExpandable;
		
		public Object[] array;
		public boolean[] isValue;
		
		public IndexMultiMapLayeredImpl() {
			this(IndexMultiMapLayeredImpl::new, defaultCapacity);
		}
		
		public IndexMultiMapLayeredImpl(int initCapacity) {
			this(() -> new IndexMultiMapLayeredImpl(initCapacity), initCapacity);
		}
		
		public IndexMultiMapLayeredImpl(Supplier<IndexMultiMapLayered> newLayers) {
			this(newLayers, defaultCapacity);
		}
		
		public IndexMultiMapLayeredImpl(Supplier<IndexMultiMapLayered> newLayers, int initCapacity) {
			this.newLayers = newLayers;
			array = new Object[initCapacity];
			isValue = new boolean[initCapacity];
		}
		
		public void setNewLayers(Supplier<IndexMultiMapLayered> newLayers) {
			this.newLayers = newLayers;
			newLayersExpandable = newLayers.get().isExpandable(IndexMultiMap.EMPTYINT);
		}
		
		public boolean ensureCapacityAvailable(int index) {
			return ensureCapacity(index + 1);
		}
		
		public boolean ensureCapacity(int capa) {
			int oldl = array.length;
			if (oldl < capa) {
				int newl = ArrayUtils.getOptimalArraySizeExpansion(oldl, capa, expandShift);
				
				Object[] oldArray = array;
				array = new Object[newl];
				System.arraycopy(oldArray, 0, array, 0, oldl);
				
				boolean[] oldValue = isValue;
				isValue = new boolean[newl];
				System.arraycopy(oldValue, 0, isValue, 0, oldl);
				return true;
			}
			return false;
		}
		
		@Override
		public boolean isExpandable() {
			return true;
		}
		
		@Override
		public boolean isExpandable(int[] pos, int offset) {
			if (pos.length < offset) {
				int index = pos[offset];
				if (index < 0)
					throw new IndexOutOfBoundsException("no negative index!");
				if (index > array.length)
					return newLayersExpandable;
				
				Object o = array[index];
				boolean value = isValue[index];
				
				if (value) {
					//noinspection unchecked
					array[index] = null;
					return newLayersExpandable;
				}
				
				if (o == null)
					return newLayersExpandable;
				//noinspection unchecked
				return ((IndexMultiMapLayered<VALUE>) o).isExpandable(pos, offset + 1);
			} else {
				return isExpandable();
			}
		}
		
		@Override
		public int size(int[] pos, int offset) {
			if (offset < pos.length) {
				int index = pos[offset];
				
				Object o = array[index];
				boolean value = isValue[index];
				
				if (value)
					return 1;
				if (o == null)
					return 0;
				
				//noinspection unchecked
				return ((IndexMultiMapLayered<VALUE>) o).size(pos, offset + 1);
			} else {
				return array.length;
			}
		}
		
		@Override
		public boolean contains(int[] pos, int offset) {
			int index = offset < pos.length ? pos[offset] : 0;
			if (index < 0)
				throw new IndexOutOfBoundsException("no negative index!");
			if (index > array.length)
				return false;
			
			Object o = array[index];
			boolean value = isValue[index];
			
			if (value)
				return true;
			if (o == null)
				return false;
			//noinspection unchecked
			return ((IndexMultiMapLayered<VALUE>) o).contains(pos, offset + 1);
		}
		
		@Override
		public VALUE get(int[] pos, int offset) {
			int index = offset < pos.length ? pos[offset] : 0;
			if (index < 0)
				throw new IndexOutOfBoundsException("no negative index!");
			if (index > array.length)
				return null;
			
			Object o = array[index];
			boolean value = isValue[index];
			
			if (value)
				//noinspection unchecked
				return (VALUE) o;
			
			if (o == null)
				return null;
			//noinspection unchecked
			return ((IndexMultiMapLayered<VALUE>) o).get(pos, offset + 1);
		}
		
		@Override
		public VALUE remove(int[] pos, int offset) {
			int index = offset < pos.length ? pos[offset] : 0;
			if (index < 0)
				throw new IndexOutOfBoundsException("no negative index!");
			if (index > array.length)
				return null;
			
			Object o = array[index];
			boolean value = isValue[index];
			
			if (value) {
				array[index] = null;
				isValue[index] = false;
				//noinspection unchecked
				return (VALUE) o;
			}
			
			if (o == null)
				return null;
			//noinspection unchecked
			return ((IndexMultiMapLayered<VALUE>) o).remove(pos, offset + 1);
		}
		
		@Override
		public VALUE put(int[] pos, int offset, VALUE v) {
			int index = offset < pos.length ? pos[offset] : 0;
			ensureCapacityAvailable(index);
			
			Object o = array[index];
			boolean value = isValue[index];
			boolean isLast = offset >= pos.length - 1;
			
			if (isLast) {
				if (value) {
					array[index] = v;
					isValue[index] = true;
					//noinspection unchecked
					return (VALUE) o;
				}
				
				if (o == null) {
					array[index] = v;
					isValue[index] = true;
					return null;
				}
				
				//noinspection unchecked
				return ((IndexMultiMapLayeredImpl<VALUE>) o).put(pos, offset + 1, v);
			} else {
				if (o == null) {
					IndexMultiMapLayeredImpl<VALUE> n = new IndexMultiMapLayeredImpl<>();
					array[index] = n;
					isValue[index] = false;
					return n.put(pos, offset + 1, v);
				}
				
				if (value) {
					IndexMultiMapLayeredImpl<VALUE> n = new IndexMultiMapLayeredImpl<>();
					array[index] = n;
					isValue[index] = false;
					//noinspection unchecked
					n.put(new int[] {}, 0, (VALUE) o);
					return n.put(pos, offset + 1, v);
				}
				
				//noinspection unchecked
				return ((IndexMultiMapLayeredImpl<VALUE>) o).put(pos, offset + 1, v);
			}
		}
		
		@Override
		public void clear() {
			Arrays.fill(array, null);
			Arrays.fill(isValue, false);
		}
		
		@Override
		public void clear(int[] pos, int offset) {
			if (pos.length < offset) {
				int index = pos[offset];
				if (index < 0)
					throw new IndexOutOfBoundsException("no negative index!");
				if (index > array.length)
					return;
				
				Object o = array[index];
				boolean value = isValue[index];
				
				if (value) {
					//noinspection unchecked
					array[index] = null;
					return;
				}
				
				if (o == null)
					return;
				//noinspection unchecked
				((IndexMultiMapLayered<VALUE>) o).clear(pos, offset + 1);
			} else {
				clear();
			}
		}
		
		@Override
		public Iteratorable<VALUE> values() {
			return new IndexMultiMapTableIteratorToNormalIterator<>(table());
		}
		
		@Override
		public IndexMultiMapMultiArrayElementIteratorImpl table(int[] pos) {
			return table(pos, 0);
		}
		
		@Override
		public IndexMultiMapMultiArrayElementIteratorImpl table() {
			return table(EMPTYINT, 0);
		}
		
		@Override
		public IndexMultiMapMultiArrayElementIteratorImpl table(int[] pos, int offset) {
			return new IndexMultiMapMultiArrayElementIteratorImpl();
		}
		
		//FIXME: toString()
//	@Override
//	public String toString() {
//		CharBufferBuilder2D<?> buffer = new CharBufferBuilder2D<>();
//		Iteratorable<IndexMultiMapEntry<VALUE>> iter = table();
//		boolean hasNext = iter.hasNext();
//
//		while (hasNext) {
//			IndexMultiMapEntry<VALUE> entry = iter.next();
//			buffer.append(Arrays.toString(entry.position)).append(" - ").append(entry.value);
//			hasNext = iter.hasNext();
//			if (hasNext)
//				buffer.nextLine();
//		}
//		return buffer.toString();
//	}
		
		public class IndexMultiMapMultiArrayElementIteratorImpl implements Iteratorable<IndexMultiMapEntry<VALUE>> {
			
			public int index = -1;
			public IntList position;
			public VALUE next = null;
			public IndexMultiMapMultiArrayElementIteratorImpl nextIter = null;
			
			public IndexMultiMapMultiArrayElementIteratorImpl() {
				calcNext();
			}
			
			@Override
			public boolean hasNext() {
				return next != null;
			}
			
			@Override
			public IndexMultiMapEntry<VALUE> next() {
				IndexMultiMapEntry<VALUE> ret = new IndexMultiMapEntry<VALUE>() {
					int[] pos = position.toArrayBackwards();
					VALUE v = next;
					
					@Override
					public int[] getIndex() {
						return pos;
					}
					
					@Override
					public VALUE getValue() {
						return v;
					}
					
					@Override
					public void setValue(VALUE v) {
						this.v = v;
						IndexMultiMapLayeredImpl.this.put(pos, v);
					}
				};
				calcNext();
				return ret;
			}
			
			public VALUE nextInternal() {
				VALUE ret = next;
				calcNext();
				return ret;
			}
			
			public void calcNext() {
				if (nextIter != null) {
					if (nextIter.hasNext()) {
						position = nextIter.position;
						position.add(index);
						next = nextIter.nextInternal();
						return;
					} else {
						nextIter = null;
					}
				}
				
				index++;
				if (index >= array.length)
					return;
				
				Object o = array[index];
				boolean isValue = IndexMultiMapLayeredImpl.this.isValue[index];
				
				if (isValue) {
					position = new IntList(new int[] {index});
					//noinspection unchecked
					next = (VALUE) o;
					return;
				}
				
				if (o == null) {
					position = IntList.EMPTY;
					next = null;
					return;
				}
				
				//noinspection unchecked
				nextIter = ((IndexMultiMapLayeredImpl<VALUE>) o).table();
				calcNext();
			}
		}
	}
}

