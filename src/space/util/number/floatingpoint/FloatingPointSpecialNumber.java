package space.util.number.floatingpoint;

public enum FloatingPointSpecialNumber {
	
	NORMAL("Normal"),
	ZERO("0", 0f),
	NEGATIVE_ZERO("-0", -0f),
	NAN("NaN", Float.NaN),
	POSITIVE_INFINITY("Inf", Float.POSITIVE_INFINITY),
	NEGATIVE_INFINITY("-Inf", Float.NEGATIVE_INFINITY);
	
	public final String name;
	public final boolean isNotNormal;
	public final float floatRep;
	
	FloatingPointSpecialNumber(String name, boolean isNotNormal, float floatRep) {
		this.name = name;
		this.isNotNormal = isNotNormal;
		this.floatRep = floatRep;
	}
	
	FloatingPointSpecialNumber(String name, float floatRep) {
		this(name, true, floatRep);
	}
	
	FloatingPointSpecialNumber(String name) {
		this(name, false, 0);
	}
	
	public boolean isZero() {
		return this == ZERO || this == NEGATIVE_ZERO;
	}
	
	public boolean isInfinite() {
		return this == POSITIVE_INFINITY || this == NEGATIVE_INFINITY;
	}
	
	public boolean isFinite() {
		return this != NAN && this != POSITIVE_INFINITY && this != NEGATIVE_INFINITY;
	}
	
	public boolean isNotFinite() {
		return this == NAN || this == POSITIVE_INFINITY || this == NEGATIVE_INFINITY;
	}
	
}
