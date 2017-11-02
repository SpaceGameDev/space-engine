package space.util.conversion;

import space.util.GetClass;
import space.util.conversion.exception.InvalidConversionException;
import space.util.delegate.map.specific.ClassMap.GetClassOrSuperMap;

import java.util.HashMap;
import java.util.Map;

public class ConverterMiddle<FROM, TO, MIDDLE> implements IConverter<FROM, TO> {
	
	public Map<Class<? extends FROM>, IConverter<FROM, MIDDLE>> fromConvMiddle;
	public Map<Class<? extends TO>, IConverter<MIDDLE, TO>> middleConvTo;
	
	public ConverterMiddle() {
		this(new GetClassOrSuperMap<>(new HashMap<>()), new GetClassOrSuperMap<>(new HashMap<>()));
	}
	
	protected ConverterMiddle(Map<Class<? extends FROM>, IConverter<FROM, MIDDLE>> fromConvMiddle, Map<Class<? extends TO>, IConverter<MIDDLE, TO>> middleConvTo) {
		this.fromConvMiddle = fromConvMiddle;
		this.middleConvTo = middleConvTo;
	}
	
	@Override
	@Deprecated
	public TO convertNew(FROM from) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public <LTO extends TO> LTO convertInstance(FROM from, LTO ret) {
		IConverter<FROM, MIDDLE> convFM = fromConvMiddle.get(GetClass.gClass(from));
		if (convFM == null)
			throw new InvalidConversionException("No converter found for From -> Middle");
		
		IConverter<MIDDLE, TO> convMT = middleConvTo.get(GetClass.gClass(ret));
		if (convMT == null)
			throw new InvalidConversionException("No converter found for Middle -> To");
		
		return convMT.convertInstance(convFM.convertNew(from), ret);
	}
	
	@Override
	public TO convertType(FROM from, Class<? extends TO> type) {
		IConverter<FROM, MIDDLE> convFM = fromConvMiddle.get(GetClass.gClass(from));
		if (convFM == null)
			throw new InvalidConversionException("No converter found for From -> Middle");
		
		IConverter<MIDDLE, TO> convMT = middleConvTo.get(type);
		if (convMT == null)
			throw new InvalidConversionException("No converter found for Middle -> To");
		
		return convMT.convertType(convFM.convertNew(from), type);
	}
	
	public IConverter<FROM, MIDDLE> putFromConvMiddle(Class<? extends FROM> key, IConverter<FROM, MIDDLE> value) {
		return fromConvMiddle.put(key, value);
	}
	
	public IConverter<FROM, MIDDLE> removeFromConvMiddle(Class<? extends FROM> key) {
		return fromConvMiddle.remove(key);
	}
	
	public IConverter<MIDDLE, TO> putMiddleConvTo(Class<? extends TO> key, IConverter<MIDDLE, TO> value) {
		return middleConvTo.put(key, value);
	}
	
	public IConverter<MIDDLE, TO> removeMiddleConvTo(Class<? extends TO> key) {
		return middleConvTo.remove(key);
	}
}
