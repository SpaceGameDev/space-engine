package space.engine.number.integer;

import space.util.creatorOld.Creator;
import space.util.string.builder.IStringBuilder;

public final class IntegerGeneral extends IInteger<IntegerGeneral> implements LayeredToString {
	
	public static final Creator<IntegerGeneral> CREATOR = new Creator<IntegerGeneral>() {
		@Override
		public IntegerGeneral create() {
			return new IntegerGeneral();
		}
		
		@Override
		public IntegerGeneral[] createArray(int size) {
			return new IntegerGeneral[size];
		}
	};
	public boolean sign;
	public int[] number;
	
	public IntegerGeneral() {
	}
	
	public IntegerGeneral(boolean sign, int[] number) {
		this.sign = sign;
		this.number = number;
	}
	
	@Override
	public Creator<IntegerGeneral> creator() {
		return CREATOR;
	}
	
	@Override
	public IntegerGeneral set(IntegerGeneral n) {
		sign = n.sign;
		number = n.number.clone();
		return this;
	}
	
	@Override
	public IntegerGeneral make() {
		return new IntegerGeneral();
	}
	
	@Override
	public IntegerGeneral copy() {
		return new IntegerGeneral(sign, number.clone());
	}
	
	@Override
	public void toStringLayered(IStringBuilder<?> sb) {
		IStringBuilder<?> b = sb.startLine();
		if (number.length == 0) {
			b.append('0');
		} else {
			
			if (sign) {
				BigMath.toString(b, number);
			} else {
				b.append('-');
				BigMath.toStringNegative(b, number);
			}
		}
		b.endLine();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
