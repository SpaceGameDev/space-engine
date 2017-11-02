package space.engine.number.unsigned;

import space.util.creatorOld.Creator;
import space.util.math.BigMath;
import space.util.string.builder.IStringBuilder;
import spaceOld.util.string.builder.LayeredToString;

public final class UnsignedIntegerGeneral extends IUnsignedInteger<UnsignedIntegerGeneral> implements LayeredToString {
	
	public static final Creator<UnsignedIntegerGeneral> CREATOR = new Creator<UnsignedIntegerGeneral>() {
		@Override
		public UnsignedIntegerGeneral create() {
			return new UnsignedIntegerGeneral();
		}
		
		@Override
		public UnsignedIntegerGeneral[] createArray(int size) {
			return new UnsignedIntegerGeneral[size];
		}
	};
	public int[] number;
	
	public UnsignedIntegerGeneral() {
	}
	
	public UnsignedIntegerGeneral(int[] number) {
		this.number = number;
	}
	
	@Override
	public Creator<UnsignedIntegerGeneral> creator() {
		return CREATOR;
	}
	
	@Override
	public UnsignedIntegerGeneral set(UnsignedIntegerGeneral n) {
		number = n.number.clone();
		return this;
	}
	
	@Override
	public UnsignedIntegerGeneral make() {
		return new UnsignedIntegerGeneral();
	}
	
	@Override
	public UnsignedIntegerGeneral copy() {
		return new UnsignedIntegerGeneral(number.clone());
	}
	
	@Override
	public void toStringLayered(IStringBuilder<?> b) {
		b.append(BigMath.toString(number));
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
