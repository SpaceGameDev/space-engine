package space.util.conversion.smart;

import space.util.conversion.Converter;
import spaceOld.engine.logger.Logger;

public class ConverterSmartSynchronized<MIN> implements IConverterSmart<MIN> {
	
	public IConverterSmart<MIN> converterSmart;
	
	public ConverterSmartSynchronized() {
	}
	
	public ConverterSmartSynchronized(IConverterSmart<MIN> converterSmart) {
		this.converterSmart = converterSmart;
	}
	
	//convert
	@Override
	public synchronized MIN convertNew(MIN from) throws UnsupportedOperationException {
		return converterSmart.convertNew(from);
	}
	
	@Override
	public synchronized <LTO extends MIN> LTO convertInstance(MIN from, LTO ret) {
		return converterSmart.convertInstance(from, ret);
	}
	
	@Override
	public synchronized MIN convertType(MIN from, Class<? extends MIN> type) {
		return converterSmart.convertType(from, type);
	}
	
	//set
	@Override
	public synchronized void setDefaultConversionMethod(ConverterSmartDefaultConversionMethod method) {
		converterSmart.setDefaultConversionMethod(method);
	}
	
	@Override
	public synchronized void setResolveSmartIgnoreDuplicatePriorities(boolean resolveSmartIgnoreDuplicatePriorities) {
		converterSmart.setResolveSmartIgnoreDuplicatePriorities(resolveSmartIgnoreDuplicatePriorities);
	}
	
	@Override
	public synchronized void setResolveSmartLogger(Logger resolveSmartLogger) {
		converterSmart.setResolveSmartLogger(resolveSmartLogger);
	}
	
	//converters
	@Override
	public synchronized <LFROM extends MIN, LTO extends MIN> ConverterSmart.ConverterSmartPriorityConverter<LFROM, LTO> getConverter(Class<LFROM> classFrom, Class<LTO> classTo) {
		return converterSmart.getConverter(classFrom, classTo);
	}
	
	@Override
	public synchronized <LFROM extends MIN, LTO extends MIN> void putConverter(Class<LFROM> classFrom, Class<LTO> classTo, ConverterSmart.ConverterSmartPriorityConverter<LFROM, LTO> conv) {
		converterSmart.putConverter(classFrom, classTo, conv);
	}
	
	@Override
	public synchronized <LFROM extends MIN, LTO extends MIN> void putConverter(Class<LFROM> classFrom, Class<LTO> classTo, Converter<LFROM, LTO> conv, int weight) {
		converterSmart.putConverter(classFrom, classTo, conv, weight);
	}
	
	@Override
	public synchronized <LFROM extends MIN, LTO extends MIN> Converter<LFROM, LTO> getConverterDefaultMethod(Class<LFROM> classFrom, Class<LTO> classTo) {
		return converterSmart.getConverterDefaultMethod(classFrom, classTo);
	}
	
	@Override
	public synchronized <LFROM extends MIN, LTO extends MIN> Converter<LFROM, LTO> getSmart(Class<LFROM> classFrom, Class<LTO> classTo) throws IllegalStateException {
		return converterSmart.getSmart(classFrom, classTo);
	}
	
	@Override
	public synchronized void clearCache() {
		converterSmart.clearCache();
	}
}
