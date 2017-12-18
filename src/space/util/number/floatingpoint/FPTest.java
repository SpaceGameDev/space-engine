package space.util.number.floatingpoint;

import space.util.number.floatingpoint.primitive.IFloatingPointPrimitive;
import space.util.number.floatingpoint.primitive.NumberDouble;
import space.util.number.floatingpoint.primitive.NumberFloat;
import space.util.string.builder.CharBufferBuilder2D;

public class FPTest {
	
	//FIXME: fraction is displayed as Integer!!!
	public static void main(String[] args) {
		IFloatingPointPrimitive<?> fp = new NumberFloat();
		IFloatingPointPrimitive<?> to = new NumberFloat();
		
		test(fp, to, 1);
		test(fp, to, 0);
		test(fp, to, -0);
		test(fp, to, Float.POSITIVE_INFINITY);
		test(fp, to, Float.NEGATIVE_INFINITY);
		test(fp, to, Float.NaN);
		
		hr();
		test(fp, to, 1.23456f);
		test(fp, to, 1f / 4096);
		test(fp, to, 0.00001f);
		test(fp, to, 1E9f);
	}
	
	public static void test(IFloatingPointPrimitive<?> fp, IFloatingPointPrimitive<?> to, float set) {
		if (fp instanceof NumberFloat) {
			((NumberFloat) fp).f = set;
		} else if (fp instanceof NumberDouble) {
			((NumberDouble) fp).d = set;
		} else {
			throw new RuntimeException();
		}
		
		FloatingPointGeneral get = null;
		try {
			get = fp.get();
			to.set(get);
		} finally {
			CharBufferBuilder2D<?> b = new CharBufferBuilder2D<>();
			b.append("Testing class: ").append(fp.getClass().getName()).nextLine();
			b.append("ToStirng: ").append(fp).nextLine();
			b.append("Get class: ").append(get != null ? get.getClass().getName() : "null").nextLine();
			b.append("Get ToString: ").append(get).nextLine();
			b.append("To class: ").append(to.getClass().getName()).nextLine();
			b.append("To ToString: ").append(to).nextLine();
			b.append("");
			
			System.out.println(b);
		}
	}
	
	public static void hr() {
		System.out.println();
		System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println();
		System.out.println();
	}
}
