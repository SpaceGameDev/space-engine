package space.util.conversion.smart;

import space.util.GetClass;
import space.util.conversion.IConverter;
import space.util.string.builder.CharBufferBuilder2D;
import space.util.string.builder.IStringBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConverterSmart<MIN> implements IConverterSmart<MIN> {
	
	public boolean resolveSmartIgnoreDuplicatePriorities;
	public Logger resolveSmartLogger;
	public ConverterSmartDefaultConversionMethod defConvMethod;
	
	public HashMap<Class<? extends MIN>, ConverterSmartNode<?>> nodes = new HashMap<>();
	
	public ConverterSmart() {
		this(false, null, ConverterSmartDefaultConversionMethod.NormalGet);
	}
	
	public ConverterSmart(ConverterSmartDefaultConversionMethod defConvMethod) {
		this(false, null, defConvMethod);
	}
	
	public ConverterSmart(boolean resolveSmartIgnoreDuplicatePriorities, Logger resolveSmartLogger, ConverterSmartDefaultConversionMethod defConvMethod) {
		this.resolveSmartIgnoreDuplicatePriorities = resolveSmartIgnoreDuplicatePriorities;
		this.resolveSmartLogger = resolveSmartLogger;
		this.defConvMethod = defConvMethod;
	}
	
	//convert
	@Override
	public MIN convertNew(MIN from) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public <LTO extends MIN> LTO convertInstance(MIN from, LTO ret) {
		return getConverterDefaultMethod(GetClass.gClass(from), GetClass.gClass(ret)).convertInstance(from, ret);
	}
	
	@Override
	public MIN convertType(MIN from, Class<? extends MIN> type) {
		return convertType0(from, type);
	}
	
	private <LFROM extends MIN, LTO extends MIN> LTO convertType0(LFROM from, Class<LTO> type) {
		return getConverterDefaultMethod(GetClass.gClass(from), type).convertType(from, type);
	}
	
	//set
	
	@Override
	public void setDefaultConversionMethod(ConverterSmartDefaultConversionMethod method) {
		defConvMethod = method;
	}
	
	@Override
	public void setResolveSmartIgnoreDuplicatePriorities(boolean resolveSmartIgnoreDuplicatePriorities) {
		this.resolveSmartIgnoreDuplicatePriorities = resolveSmartIgnoreDuplicatePriorities;
	}
	
	@Override
	public void setResolveSmartLogger(Logger resolveSmartLogger) {
		this.resolveSmartLogger = resolveSmartLogger;
	}
	
	//nodes
	@SuppressWarnings("unchecked")
	public <NODE extends MIN> ConverterSmartNode<NODE> get(Class<NODE> clazz) {
		return (ConverterSmartNode<NODE>) nodes.get(clazz);
	}
	
	public <NODE extends MIN> ConverterSmartNode<NODE> getOrCreate(Class<NODE> clazz) {
		ConverterSmartNode<NODE> node = get(clazz);
		if (node != null)
			return node;
		
		node = new ConverterSmartNode<>(clazz);
		put(clazz, node);
		return node;
	}
	
	public <NODE extends MIN> ConverterSmartNode<?> put(Class<NODE> clazz, ConverterSmartNode<NODE> node) {
		return nodes.put(clazz, node);
	}
	
	//converters
	@Override
	public <LFROM extends MIN, LTO extends MIN> ConverterSmartPriorityConverter<LFROM, LTO> getConverter(Class<LFROM> classFrom, Class<LTO> classTo) {
		ConverterSmartNode<LFROM> conv = get(classFrom);
		return conv == null ? null : conv.getConvertTo(classTo);
	}
	
	@Override
	public <LFROM extends MIN, LTO extends MIN> void putConverter(Class<LFROM> classFrom, Class<LTO> classTo, ConverterSmartPriorityConverter<LFROM, LTO> conv) {
		getOrCreate(classFrom).putConvertTo(classTo, conv);
		getOrCreate(classTo).putConvertFrom(classFrom, conv);
	}
	
	@Override
	public <LFROM extends MIN, LTO extends MIN> void putConverter(Class<LFROM> classFrom, Class<LTO> classTo, IConverter<LFROM, LTO> conv, int weight) {
		putConverter(classFrom, classTo, new ConverterSmartPriorityConverterWrapper<>(conv, weight));
	}
	
	@Override
	public <LFROM extends MIN, LTO extends MIN> IConverter<LFROM, LTO> getConverterDefaultMethod(Class<LFROM> classFrom, Class<LTO> classTo) {
		return defConvMethod.getConverter(this, classFrom, classTo);
	}
	
	//smart
	
	@Override
	public <LFROM extends MIN, LTO extends MIN> IConverter<LFROM, LTO> getSmart(Class<LFROM> classFrom, Class<LTO> classTo) throws IllegalStateException {
		Logger subLogger;
		if (resolveSmartLogger != null) {
			subLogger = new SubLogger(resolveSmartLogger, classFrom.getSimpleName() + " -> " + classTo.getSimpleName());
			subLogger.print(LogLevel.INFO, new LayeredStringBuilderBase().append("Resolving....."));
		} else {
			subLogger = null;
		}
		
		List<ConverterSmartSmartStorage0<?, ?>> read = new ArrayList<>();
		List<ConverterSmartSmartStorage0<?, ?>> write = new ArrayList<>();
		List<ConverterSmartSmartStorage1Used<?>> used = new ArrayList<>();
		
		ConverterSmartNode<LFROM> startNode = get(classFrom);
		if (startNode == null)
			return null;
		read.add(new ConverterSmartSmartStorage0<LFROM, Object>(startNode));
		used.add(new ConverterSmartSmartStorage1Used<>(startNode, 0));
		
		ConverterSmartSmartStorage0<LTO, ?> ret = new ConverterSmartSmartStorage0<>(Integer.MAX_VALUE);
		
		while (!read.isEmpty()) {
			for (ConverterSmartSmartStorage0<?, ?> cRead : read) {
				for (Map.Entry<Class<? extends MIN>, ? extends ConverterSmartPriorityConverter<?, ? extends MIN>> cNext : cRead.currNode.mapConvertTo.entrySet()) {
					
					//get
					Class<? extends MIN> cNextClass = cNext.getKey();
					ConverterSmartPriorityConverter<?, ? extends MIN> cNextConverter = cNext.getValue();
					ConverterSmartNode<? extends MIN> cNextNode = get(cNextClass);
					
					//weight calc and pre-check
					int cNextWeight = cRead.currWeight + cNextConverter.getWeight();
					if (cNextWeight > ret.currWeight)
						continue;
					
					//storage
					@SuppressWarnings("unchecked")
					ConverterSmartSmartStorage0<?, ?> cNextStorage = new ConverterSmartSmartStorage0(cNextNode, cNextWeight, cRead, cNextConverter);
					
					//is Goal?
					if (classTo.equals(cNextClass)) {
						if (cNextWeight == ret.currWeight) {
							boolean loggerNotNull = subLogger != null;
							if (loggerNotNull || !resolveSmartIgnoreDuplicatePriorities) {
								CharBufferBuilder2D<?> b = new CharBufferBuilder2D<>()
										.append("getSmart has found two Algorithms with same Priority!").nextLine()
										.append("First: ").append(ret).nextLine()
										.append("Second: ").append(cNextStorage);
								
								if (loggerNotNull)
									subLogger.print(LogLevel.WARNING, b);
								if (!resolveSmartIgnoreDuplicatePriorities)
									throw new IllegalStateException(b.toString());
							}
						}
						
						if (cNextWeight < ret.currWeight) {
							if (subLogger != null)
								subLogger.print(LogLevel.FINE, new CharBufferBuilder2D<>()
										.append("Found new better way!   :)").nextLine()
										.append("weight: " + cNextWeight).nextLine()
										.append(cNextStorage).nextLine()
										.append("from: ").append(ret));
							//noinspection unchecked
							ret = (ConverterSmartSmartStorage0<LTO, ?>) cNextStorage;
						}
						continue;
					}
					
					//check if further resolve if necessary (not resolved or lower weight than previous)
					boolean doWrite = true;
					for (ConverterSmartSmartStorage1Used cUsed : used) {
						if (cUsed.node == cNextNode) {
							if (cNextWeight < cUsed.weight) {
								//faster way to node
								if (subLogger != null)
									subLogger.print(LogLevel.INFO, new CharBufferBuilder2D<>()
											.append("Found quicker way to ").append(cNextNode).nextLine()
											.append("from ").append(cUsed.weight).append(" down to ").append(cNextWeight));
								cUsed.weight = cNextWeight;
							} else {
								//already checked
								doWrite = false;
							}
							break;
						}
					}
					
					//actual write
					if (doWrite) {
						write.add(cNextStorage);
						used.add(new ConverterSmartSmartStorage1Used<>(cNextNode, cNextWeight));
					}
				}
			}
			
			//switch lists
			List<ConverterSmartSmartStorage0<?, ?>> temp = read;
			read = write;
			write = temp;
			write.clear();
		}
		
		//check converter
		if (ret.currNode == null) {
			if (subLogger != null)
				subLogger.print(LogLevel.WARNING, new CharBufferBuilder2D<>()
						.append("Resolving failed!!!"));
			return null;
		}
		
		if (subLogger != null)
			subLogger.print(LogLevel.INFO, new CharBufferBuilder2D<>()
					.append("Resolved!!!").nextLine()
					.append("weight: " + ret.currWeight).nextLine()
					.append(ret));
		
		//build converter
		return ret.resolve();
	}
	
	@Override
	public void clearCache() {
		
	}
	
	//classes
	public class ConverterSmartNode<NODE> {
		
		public final Class<NODE> clazzNode;
		public HashMap<Class<? extends MIN>, ConverterSmartPriorityConverter<? extends MIN, NODE>> mapConvertFrom = new HashMap<>();
		public HashMap<Class<? extends MIN>, ConverterSmartPriorityConverter<NODE, ? extends MIN>> mapConvertTo = new HashMap<>();
		
		public ConverterSmartNode(Class<NODE> clazzNode) {
			this.clazzNode = clazzNode;
		}
		
		@SuppressWarnings("unchecked")
		public <CLAZZ extends MIN> ConverterSmartPriorityConverter<CLAZZ, NODE> getConvertFrom(Class<CLAZZ> clazz) {
			return (ConverterSmartPriorityConverter<CLAZZ, NODE>) mapConvertFrom.get(clazz);
		}
		
		public <CLAZZ extends MIN> IConverter<? extends MIN, NODE> putConvertFrom(Class<CLAZZ> clazz, ConverterSmartPriorityConverter<CLAZZ, NODE> node) {
			return mapConvertFrom.put(clazz, node);
		}
		
		@SuppressWarnings("unchecked")
		public <CLAZZ extends MIN> ConverterSmartPriorityConverter<NODE, CLAZZ> getConvertTo(Class<CLAZZ> clazz) {
			return (ConverterSmartPriorityConverter<NODE, CLAZZ>) mapConvertTo.get(clazz);
		}
		
		public <CLAZZ extends MIN> IConverter<NODE, ? extends MIN> putConvertTo(Class<CLAZZ> clazz, ConverterSmartPriorityConverter<NODE, CLAZZ> node) {
			return mapConvertTo.put(clazz, node);
		}
		
		@Override
		public String toString() {
			return "Node " + clazzNode.getName();
		}
	}
	
	class ConverterSmartSmartStorage0<NODE, SUPER> implements LayeredToString {
		
		ConverterSmartNode<NODE> currNode;
		int currWeight;
		
		ConverterSmartSmartStorage0<?, SUPER> superNode;
		IConverter<SUPER, NODE> superConverter;
		
		public ConverterSmartSmartStorage0() {
		}
		
		public ConverterSmartSmartStorage0(ConverterSmartNode<NODE> currNode) {
			this.currNode = currNode;
		}
		
		public ConverterSmartSmartStorage0(int currWeight) {
			this.currWeight = currWeight;
		}
		
		public ConverterSmartSmartStorage0(ConverterSmartNode<NODE> currNode, int currWeight, ConverterSmartSmartStorage0<?, SUPER> superNode, IConverter<SUPER, NODE> superConverter) {
			this.currNode = currNode;
			this.currWeight = currWeight;
			this.superNode = superNode;
			this.superConverter = superConverter;
		}
		
		public boolean hasSuper() {
			return superNode != null;
		}
		
		@SuppressWarnings("unchecked")
		public <LFROM> IConverter<LFROM, NODE> resolve() {
			if (hasSuper())
				return resolve0();
			return null;
		}
		
		@SuppressWarnings("unchecked")
		protected IConverter resolve0() {
			if (!superNode.hasSuper())
				return superConverter;
			return superNode.resolve0().andThen(superConverter);
		}
		
		@Override
		public void toStringLayered(IStringBuilder<?> sb) {
			if (currNode == null) {
				sb.append("null");
				return;
			}
			
			IStringBuilder<?> b = sb.startLine(" -> ");
			if (superNode != null)
				superNode.toStringLayered0(b);
			b.append(currNode.clazzNode.getName());
			b.endLine();
		}
		
		private void toStringLayered0(IStringBuilder<?> b) {
			if (superNode != null)
				superNode.toStringLayered0(b);
			b.append(currNode.clazzNode.getName()).nextLine();
		}
		
		@Override
		public String toString() {
			return toString0();
		}
	}
	
	class ConverterSmartSmartStorage1Used<NODE> implements LayeredToString {
		
		ConverterSmartNode<NODE> node;
		int weight;
		
		public ConverterSmartSmartStorage1Used(ConverterSmartNode<NODE> node, int weight) {
			this.node = node;
			this.weight = weight;
		}
		
		@Override
		public void toStringLayered(IStringBuilder<?> sb) {
			IStringBuilder<?> b = sb.startLine();
			b.append("node: ").append(node).nextLine();
			b.append("weight: ").append(weight);
			b.endLine();
		}
		
		@Override
		public String toString() {
			return toString0();
		}
	}
}
