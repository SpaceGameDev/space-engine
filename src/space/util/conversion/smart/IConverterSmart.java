package space.util.conversion.smart;

import space.util.conversion.Converter;
import space.util.conversion.ConverterMap;
import space.util.logger.Logger;
import space.util.string.builder.IStringBuilder;
import spaceOld.util.string.builder.LayeredToString;

public interface IConverterSmart<MIN> extends ConverterMap<MIN, MIN> {
	
	//convert
//	@Override
//	MIN convertNew(MIN from) throws UnsupportedOperationException;
//
//	@Override
//	<LTO extends MIN> LTO convertInstance(MIN from, LTO ret);
//
//	@Override
//	MIN convertType(MIN from, Class<? extends MIN> type);
	
	//set
	void setDefaultConversionMethod(ConverterSmartDefaultConversionMethod method);
	
	void setResolveSmartIgnoreDuplicatePriorities(boolean resolveSmartIgnoreDuplicatePriorities);
	
	void setResolveSmartLogger(Logger resolveSmartLogger);
	
	//converters
	<LFROM extends MIN, LTO extends MIN> ConverterSmart.ConverterSmartPriorityConverter<LFROM, LTO> getConverter(Class<LFROM> classFrom, Class<LTO> classTo);
	
	<LFROM extends MIN, LTO extends MIN> void putConverter(Class<LFROM> classFrom, Class<LTO> classTo, ConverterSmart.ConverterSmartPriorityConverter<LFROM, LTO> conv);
	
	<LFROM extends MIN, LTO extends MIN> void putConverter(Class<LFROM> classFrom, Class<LTO> classTo, Converter<LFROM, LTO> conv, int weight);
	
	<LFROM extends MIN, LTO extends MIN> Converter<LFROM, LTO> getConverterDefaultMethod(Class<LFROM> classFrom, Class<LTO> classTo);
	
	/**
	 * @throws IllegalStateException when two ways have the same weight (if enabled)
	 */
	<LFROM extends MIN, LTO extends MIN> Converter<LFROM, LTO> getSmart(Class<LFROM> classFrom, Class<LTO> classTo) throws IllegalStateException;
	
	void clearCache();
	
	enum ConverterSmartDefaultConversionMethod {
		
		NormalGet {
			@Override
			public <MIN, LFROM extends MIN, LTO extends MIN> Converter<LFROM, LTO> getConverter(ConverterSmart<MIN> conv, Class<LFROM> classFrom, Class<LTO> classTo) {
				return conv.getConverter(classFrom, classTo);
			}
		},
		Smart {
			@Override
			public <MIN, LFROM extends MIN, LTO extends MIN> Converter<LFROM, LTO> getConverter(ConverterSmart<MIN> conv, Class<LFROM> classFrom, Class<LTO> classTo) {
				return conv.getSmart(classFrom, classTo);
			}
		};
		
		public abstract <MIN, LFROM extends MIN, LTO extends MIN> Converter<LFROM, LTO> getConverter(ConverterSmart<MIN> conv, Class<LFROM> classFrom, Class<LTO> classTo);
		
	}
	
	interface ConverterSmartPriorityConverter<FROM, TO> extends Converter<FROM, TO> {
		
		int getWeight();
	}
	
	class ConverterSmartPriorityConverterWrapper<FROM, TO> implements ConverterSmartPriorityConverter<FROM, TO>, LayeredToString {
		
		public Converter<FROM, TO> conv;
		public int weight;
		
		public ConverterSmartPriorityConverterWrapper(Converter<FROM, TO> conv, int weight) {
			this.conv = conv;
			this.weight = weight;
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
		public TO convertType(FROM from, Class<? extends TO> type) {
			return conv.convertType(from, type);
		}
		
		@Override
		public int getWeight() {
			return weight;
		}
		
		@Override
		public void toStringLayered(IStringBuilder<?> sb) {
			IStringBuilder<?> b = sb.startLine();
			b.append("conv: ").append(conv).nextLine();
			b.append("weight: ").append(weight);
			b.endLine();
		}
		
		@Override
		public String toString() {
			return toString0();
		}
	}
}
