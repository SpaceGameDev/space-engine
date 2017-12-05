package space.util.conversion.smart;

import space.util.baseobject.ToString;
import space.util.conversion.Converter;
import space.util.indexmap.IndexMapArray;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * A Resolver made to find an optimal way to convert between two Classes or Objects
 * You can add {@link Path}s, on which the Resolver use to convert from one Class to another.
 * It is synchronized.
 *
 * @see space.util.conversion.smart.ConverterSmart.Path
 */
public class ConverterSmart<MIN> implements IConverterSmart<MIN> {
	
	public Map<Class<?>, Node<?>> nodes = new HashMap<>();
	
	public <NODE extends MIN> Node<NODE> getNodeOrCreate(Class<NODE> clazz) {
		Node<?> node = nodes.get(clazz);
		if (node != null)
			//noinspection unchecked
			return (Node<NODE>) node;
		
		Node<NODE> newNode = new Node<>(clazz);
		nodes.put(clazz, newNode);
		return newNode;
	}
	
	//putConverter
	public <FROM extends MIN, TO extends MIN> void putConverter(Class<FROM> classFrom, Class<TO> classTo, Converter<FROM, TO> conv) {
		putPath(classFrom, classTo, new PathWrapper<>(conv));
	}
	
	public <FROM extends MIN, TO extends MIN> void putConverter(Class<FROM> classFrom, Class<TO> classTo, Converter<FROM, TO> conv, int weight) {
		putPath(classFrom, classTo, new PathWrapper<>(conv, weight));
	}
	
	public <FROM extends MIN, TO extends MIN> void putConverter(Class<FROM> classFrom, Class<TO> classTo, Converter<FROM, TO> conv, boolean isFinal) {
		putPath(classFrom, classTo, new PathWrapper<>(conv, isFinal));
	}
	
	public <FROM extends MIN, TO extends MIN> void putConverter(Class<FROM> classFrom, Class<TO> classTo, Converter<FROM, TO> conv, int weight, boolean isFinal) {
		putPath(classFrom, classTo, new PathWrapper<>(conv, weight, isFinal));
	}
	
	public synchronized <FROM extends MIN, TO extends MIN> void putPath(Class<FROM> classFrom, Class<TO> classTo, Path<FROM, TO> conv) {
		getNodeOrCreate(classFrom).putConvertTo(classTo, conv);
		getNodeOrCreate(classTo).putConvertFrom(classFrom, conv);
	}
	
	//getConverter
	@Override
	public <FROM extends MIN, TO extends MIN> Converter<FROM, TO> getConverter(Class<FROM> fromClass, Class<TO> toClass) {
		if (fromClass.equals(toClass))
			return Converter.identity();
		
		Resolver result = new Resolver();
		
		synchronized (this) {
			Node<?> node = nodes.get(fromClass);
			if (node == null)
				return null;
			
			getConverter0(node, new IndexMapArray<>(), 0, 0, toClass, result);
		}
		
		//noinspection unchecked
		return (Converter<FROM, TO>) result.build();
	}
	
	protected void getConverter0(Node<?> node, IndexMapArray<Path<?, ?>> path, int depth, int weight, Class<?> toClass, Resolver result) {
		for (Entry<Class<? extends MIN>, ? extends Path<?, ?>> entry : node.mapConvertTo.entrySet()) {
			Class<?> to = entry.getKey();
			Path<?, ?> p = entry.getValue();
			int newWeight = weight + p.weight();
			
			if (to.equals(toClass) && newWeight < result.weight) {
				Path<?, ?>[] newPath = new Path[depth + 1];
				System.arraycopy(path.array, 0, newPath, 0, depth);
				newPath[depth] = p;
				
				result.path = newPath;
				result.weight = newWeight;
			} else if (!p.isFinal()) {
				Node<?> newNode = nodes.get(to);
				path.put(depth, p);
				getConverter0(newNode, path, depth + 1, newWeight, toClass, result);
			}
		}
	}
	
	protected static class Resolver {
		
		Converter<?, ?>[] path;
		int weight = Integer.MAX_VALUE;
		
		public Converter<?, ?> build() {
			if (path == null || path.length == 0)
				return null;
			
			Converter curr = path[0];
			for (int i = 1; i < path.length; i++)
				//noinspection unchecked
				curr = curr.andThen(path[i]);
			return curr;
		}
	}
	
	/**
	 * Path is a Path between to Classes the Resolver can take. It has {@link Path#weight()} and can be checked on {@link Path#isFinal()}.
	 *
	 * @see space.util.conversion.Converter the baseclass {@link Converter}
	 * @see Path#weight() check the weight of a {@link Path}
	 * @see Path#isFinal() check wether a {@link Path} is lossless
	 */
	interface Path<FROM, TO> extends Converter<FROM, TO> {
		
		int DEFAULT_WEIGHT = 256;
		
		@Override
		TO convertNew(FROM from) throws UnsupportedOperationException;
		
		@Override
		<LTO extends TO> LTO convertInstance(FROM from, LTO ret);
		
		/**
		 * More weight will discourage the Resolver from using this {@link Path}. This can be used to prefer certain {@link Path}s and discourage the usage of others.
		 * On default weight should return 256.
		 *
		 * @return the weight of this Conversion
		 */
		int weight();
		
		/**
		 * If a {@link Path} is final, the Resolver will NOT take it.
		 * EXCEPT it directly converts to the required TO class and is with that the last Converter in the {@link Path}Chain. Then it will also be preferred above other Paths.
		 * This should prevent the Resolver from taking weird {@link Path}s and losing precision.
		 *
		 * @return if the conversion is lossless
		 */
		boolean isFinal();
	}
	
	public class PathWrapper<FROM, TO> implements Path<FROM, TO>, ToString {
		
		public Converter<FROM, TO> conv;
		public int weight;
		public boolean isFinal;
		
		public PathWrapper(Converter<FROM, TO> conv) {
			this(conv, DEFAULT_WEIGHT, false);
		}
		
		public PathWrapper(Converter<FROM, TO> conv, int weight) {
			this(conv, weight, false);
		}
		
		public PathWrapper(Converter<FROM, TO> conv, boolean isFinal) {
			this(conv, DEFAULT_WEIGHT, isFinal);
		}
		
		public PathWrapper(Converter<FROM, TO> conv, int weight, boolean isFinal) {
			this.conv = conv;
			this.weight = weight;
			this.isFinal = isFinal;
		}
		
		@Override
		public TO convertNew(FROM from) throws UnsupportedOperationException {
			return conv.convertNew(from);
		}
		
		@Override
		public <LTO extends TO> LTO convertInstance(FROM from, LTO ret) {
			return conv.convertInstance(from, ret);
		}
		
		@Override
		public int weight() {
			return weight;
		}
		
		@Override
		public boolean isFinal() {
			return isFinal;
		}
		
		@Override
		public <T> T toTSH(ToStringHelper<T> api) {
			ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
			tsh.add("conv", this.conv);
			tsh.add("weight", this.weight);
			tsh.add("isFinal", this.isFinal);
			return tsh.build();
		}
		
		@Override
		public String toString() {
			return toString0();
		}
	}
	
	public class Node<NODE extends MIN> {
		
		public final Class<NODE> clazzNode;
		public HashMap<Class<? extends MIN>, Path<? extends MIN, NODE>> mapConvertFrom = new HashMap<>();
		public HashMap<Class<? extends MIN>, Path<NODE, ? extends MIN>> mapConvertTo = new HashMap<>();
		
		public Node(Class<NODE> clazzNode) {
			this.clazzNode = clazzNode;
		}
		
		//convertFrom
		@SuppressWarnings("unchecked")
		public <CLAZZ extends MIN> Path<CLAZZ, NODE> getConvertFrom(Class<CLAZZ> clazz) {
			return (Path<CLAZZ, NODE>) mapConvertFrom.get(clazz);
		}
		
		public <CLAZZ extends MIN> Converter<? extends MIN, NODE> putConvertFrom(Class<CLAZZ> clazz, Path<CLAZZ, NODE> node) {
			return mapConvertFrom.put(clazz, node);
		}
		
		//convertTo
		@SuppressWarnings("unchecked")
		public <CLAZZ extends MIN> Path<NODE, CLAZZ> getConvertTo(Class<CLAZZ> clazz) {
			return (Path<NODE, CLAZZ>) mapConvertTo.get(clazz);
		}
		
		public <CLAZZ extends MIN> Converter<NODE, ? extends MIN> putConvertTo(Class<CLAZZ> clazz, Path<NODE, CLAZZ> node) {
			return mapConvertTo.put(clazz, node);
		}
		
		@Override
		public String toString() {
			return "Node<" + clazzNode.getName() + ">";
		}
	}
}
