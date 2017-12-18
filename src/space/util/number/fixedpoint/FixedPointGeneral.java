package space.util.number.fixedpoint;

public final class FixedPointGeneral extends IFixedPoint<FixedPointGeneral> {
	
	public long bits;
	public int[] number;
	
	public FixedPointGeneral() {
	}
	
	public FixedPointGeneral(long bits, int[] number) {
		this.bits = bits;
		this.number = number;
	}
	
	@Override
	public FixedPointGeneral set(FixedPointGeneral n) {
		bits = n.bits;
		number = n.number.clone();
		return this;
	}
	
	@Override
	public FixedPointGeneral make() {
		return new FixedPointGeneral();
	}
	
	@Override
	public FixedPointGeneral copy() {
		return new FixedPointGeneral(bits, number.clone());
	}
	
	@Override
	public String toString() {
//		return number.length == 0 ? "0" : ;
	}
}
