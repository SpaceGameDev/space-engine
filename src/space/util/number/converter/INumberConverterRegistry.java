package space.util.number.converter;

import space.util.conversion.Converter;
import space.util.conversion.smart.ConverterSmart;
import space.util.conversion.smart.ConverterSmart.PathWrapper;
import space.util.number.base.NumberBase;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public interface INumberConverterRegistry extends Consumer<ConverterSmart<NumberBase>> {
	
	@Override
	void accept(ConverterSmart<NumberBase> conv);
	
	static <FROM extends NumberBase, TO extends NumberBase> void add(ConverterSmart<NumberBase> conv, Class<FROM> classFrom, Class<TO> classTo, Supplier<TO> creator, BiConsumer<FROM, TO> converter, boolean isFinal) {
		add(conv, classFrom, classTo, creator, converter, PathWrapper.DEFAULT_WEIGHT, isFinal);
	}
	
	static <FROM extends NumberBase, TO extends NumberBase> void add(ConverterSmart<NumberBase> conv, Class<FROM> classFrom, Class<TO> classTo, Supplier<TO> creator, BiConsumer<FROM, TO> converter, int weight, boolean isFinal) {
		conv.putConverter(classFrom, classTo, new Converter<FROM, TO>() {
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
