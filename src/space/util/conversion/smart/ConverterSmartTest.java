package space.util.conversion.smart;

import space.util.conversion.Converter;
import space.util.gui.monofont.MonofontGuiApi;
import space.util.string.toStringHelper.ToStringHelper;

public class ConverterSmartTest {
	
	public static void main(String[] args) {
		ToStringHelper.setDefault(MonofontGuiApi.TSH);
		
		ConverterSmartImpl<Object> conv = new ConverterSmartImpl<>();
		conv.putConverter(IntWrapper.class, FloatWrapper.class, new Converter<>() {
			@Override
			public FloatWrapper convertNew(IntWrapper wrapper) {
				return convertInstance(wrapper, new FloatWrapper());
			}
			
			@Override
			public <LTO extends FloatWrapper> LTO convertInstance(IntWrapper wrapper, LTO ret) {
				ret.f = (float) wrapper.i;
				return ret;
			}
		});
		
		test(conv, new IntWrapper(7), FloatWrapper.class);
		
		conv.putConverter(FloatWrapper.class, DoubleWrapper.class, new Converter<>() {
			@Override
			public DoubleWrapper convertNew(FloatWrapper wrapper) {
				return convertInstance(wrapper, new DoubleWrapper());
			}
			
			@Override
			public <LTO extends DoubleWrapper> LTO convertInstance(FloatWrapper wrapper, LTO ret) {
				ret.d = wrapper.f;
				return ret;
			}
		});
		
		test(conv, new IntWrapper(42), DoubleWrapper.class);
		
		conv.putConverter(IntWrapper.class, DoubleWrapper.class, new Converter<>() {
			@Override
			public DoubleWrapper convertNew(IntWrapper wrapper) {
				return convertInstance(wrapper, new DoubleWrapper());
			}
			
			@Override
			public <LTO extends DoubleWrapper> LTO convertInstance(IntWrapper wrapper, LTO ret) {
				ret.d = wrapper.i;
				return ret;
			}
		}, 1000);
		
		test(conv, new IntWrapper(500), DoubleWrapper.class);
	}
	
	@SuppressWarnings("unchecked")
	public static void test(ConverterSmartImpl<Object> conv, Object from, Class<?> to) {
		Converter c = conv.getConverter(from.getClass(), to);
		System.out.println(c == null ? "null" : (c + ": " + from + " -> " + c.convertNew(from)));
	}
	
	public static class IntWrapper {
		
		public int i;
		
		public IntWrapper() {
		}
		
		public IntWrapper(int i) {
			this.i = i;
		}
		
		@Override
		public String toString() {
			return Integer.toString(i) + "i";
		}
	}
	
	public static class FloatWrapper {
		
		public float f;
		
		public FloatWrapper() {
		}
		
		public FloatWrapper(float f) {
			this.f = f;
		}
		
		@Override
		public String toString() {
			return Float.toString(f) + "f";
		}
	}
	
	public static class DoubleWrapper {
		
		public double d;
		
		public DoubleWrapper() {
		}
		
		public DoubleWrapper(double d) {
			this.d = d;
		}
		
		@Override
		public String toString() {
			return Double.toString(d) + "d";
		}
	}
}
