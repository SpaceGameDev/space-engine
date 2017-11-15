package space.util.conversion.smartOld;

import space.util.conversion.Converter;
import space.util.conversion.smart.IConverterSmart;
import spaceOld.engine.logger.Logger;

public class ConverterSmartSameCheck<MIN> implements IConverterSmart<MIN> {
	
	public IConverterSmart<MIN> converterSmart;
	
	public ConverterSmartSameCheck() {
	}
	
	public ConverterSmartSameCheck(IConverterSmart<MIN> converterSmart) {
		this.converterSmart = converterSmart;
	}
	
	//convert
	@Override
	public MIN convertNew(MIN from) throws UnsupportedOperationException {
		return converterSmart.convertNew(from);
	}
	
	@Override
	public <LTO extends MIN> LTO convertInstance(MIN from, LTO ret) {
		return converterSmart.convertInstance(from, ret);
	}
	
	@Override
	public MIN convertType(MIN from, Class<? extends MIN> type) {
		if (type == from.getClass())
			return from;
		return converterSmart.convertType(from, type);
	}
	
	//set
	@Override
	public void setDefaultConversionMethod(ConverterSmartDefaultConversionMethod method) {
		converterSmart.setDefaultConversionMethod(method);
	}
	
	@Override
	public void setResolveSmartIgnoreDuplicatePriorities(boolean resolveSmartIgnoreDuplicatePriorities) {
		converterSmart.setResolveSmartIgnoreDuplicatePriorities(resolveSmartIgnoreDuplicatePriorities);
	}
	
	@Override
	public void setResolveSmartLogger(Logger resolveSmartLogger) {
		converterSmart.setResolveSmartLogger(resolveSmartLogger);
	}
	
	//converters
	@Override
	public <LFROM extends MIN, LTO extends MIN> ConverterSmartPriorityConverter<LFROM, LTO> getConverter(Class<LFROM> classFrom, Class<LTO> classTo) {
		return converterSmart.getConverter(classFrom, classTo);
	}
	
	@Override
	public <LFROM extends MIN, LTO extends MIN> void putConverter(Class<LFROM> classFrom, Class<LTO> classTo, ConverterSmartPriorityConverter<LFROM, LTO> conv) {
		converterSmart.putConverter(classFrom, classTo, conv);
	}
	
	@Override
	public <LFROM extends MIN, LTO extends MIN> void putConverter(Class<LFROM> classFrom, Class<LTO> classTo, Converter<LFROM, LTO> conv, int weight) {
		converterSmart.putConverter(classFrom, classTo, conv, weight);
	}
	
	@Override
	public <LFROM extends MIN, LTO extends MIN> Converter<LFROM, LTO> getConverterDefaultMethod(Class<LFROM> classFrom, Class<LTO> classTo) {
		return converterSmart.getConverterDefaultMethod(classFrom, classTo);
	}
	
	@Override
	public <LFROM extends MIN, LTO extends MIN> Converter<LFROM, LTO> getSmart(Class<LFROM> classFrom, Class<LTO> classTo) throws IllegalStateException {
		return converterSmart.getSmart(classFrom, classTo);
	}
	
	@Override
	public void clearCache() {
		converterSmart.clearCache();
	}
}
