package space.util.number.converter;

import space.util.conversion.Converter;
import space.util.conversion.smart.ConverterSmartImpl;
import space.util.conversion.smart.ConverterSmartImpl.PathWrapper;
import space.util.number.base.NumberBase;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public interface INumberConverterRegistry extends Consumer<ConverterSmartImpl<NumberBase>> {
	
	@Override
	void accept(ConverterSmartImpl<NumberBase> conv);
	
	static <FROM extends NumberBase, TO extends NumberBase> void add(ConverterSmartImpl<NumberBase> conv, Class<FROM> classFrom, Class<TO> classTo, Supplier<TO> creator, BiConsumer<FROM, TO> converter, boolean isFinal) {
		add(conv, classFrom, classTo, creator, converter, PathWrapper.DEFAULT_WEIGHT, isFinal);
	}
	
	static <FROM extends NumberBase, TO extends NumberBase> void add(ConverterSmartImpl<NumberBase> conv, Class<FROM> classFrom, Class<TO> classTo, Supplier<TO> creator, BiConsumer<FROM, TO> converter, int weight, boolean isFinal) {
		conv.putConverter(classFrom, classTo, new Converter<>() {
			@Override
			public TO convertNew(FROM from) {
				return convertInstance(from, creator.get());
			}
			
			@Override
			public <LTO extends TO> LTO convertInstance(FROM from, LTO ret) {
				converter.accept(from, ret);
				return ret;
			}
		}, weight, isFinal);
	}
}
