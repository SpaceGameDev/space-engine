package space.engine.number.floatingpoint;

import spaceOld.engine.base.SideStarter;
import spaceOld.util.math.number.floatingpoint.primitive.IFloatingPointPrimitive;
import spaceOld.util.math.number.floatingpoint.primitive.NumberDouble;
import spaceOld.util.math.number.floatingpoint.primitive.NumberFloat;
import spaceOld.util.string.builder.IStringBuilder;
import spaceOld.util.string.builder.layered.LayeredStringBuilderBase;

public class FPTest {
	
	//FIXME: fraction is displayed as Integer!!!
	public static void main(String[] args) {
		SideStarter.launchManagerAndShutdown(() -> {
			IFloatingPointPrimitive<?> fp = new NumberFloat();
			IFloatingPointPrimitive<?> to = new NumberFloat();

//			test(fp, to, 1);
//			test(fp, to, 0);
//			test(fp, to, -0);
//			test(fp, to, Float.POSITIVE_INFINITY);
//			test(fp, to, Float.NEGATIVE_INFINITY);
//			test(fp, to, Float.NaN);

//			hr();
			test(fp, to, 1.23456f);
			test(fp, to, 1f / 4096);
			test(fp, to, 0.00001f);
			test(fp, to, 1E9f);
		});
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
			IStringBuilder<?> b = new LayeredStringBuilderBase();
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
