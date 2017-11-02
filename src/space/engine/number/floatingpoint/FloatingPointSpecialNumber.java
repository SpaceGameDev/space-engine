package space.engine.number.floatingpoint;

public enum FloatingPointSpecialNumber {
	
	NORMAL("Normal"),
	ZERO("0", 0f),
	NEGATIVEZERO("-0", -0f),
	NAN("NaN", Float.NaN),
	POSITIVEINFINITY("Inf", Float.POSITIVE_INFINITY),
	NEGATIVEINFINITY("-Inf", Float.NEGATIVE_INFINITY);
	
	public final String name;
	public final boolean hasRep;
	public final float rep;
	
	FloatingPointSpecialNumber(String name, boolean hasRep, float rep) {
		this.name = name;
		this.hasRep = hasRep;
		this.rep = rep;
	}
	
	FloatingPointSpecialNumber(String name, float rep) {
		this(name, true, rep);
	}
	
	FloatingPointSpecialNumber(String name) {
		this(name, false, 0);
	}
	
	public boolean isZero() {
		return this == ZERO || this == NEGATIVEZERO;
	}
	
	public boolean isInfinite() {
		return this == POSITIVEINFINITY || this == NEGATIVEINFINITY;
	}
	
	public boolean isFiniteMath() {
		return !isNotFiniteMath();
	}
	
	public boolean isNotFiniteMath() {
		return this == NAN || this == POSITIVEINFINITY || this == NEGATIVEINFINITY;
	}
	
}
