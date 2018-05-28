package space.util.primitive;

import org.jetbrains.annotations.NotNull;
import space.util.SystemInfo;
import space.util.baseobject.ToString;
import space.util.math.UnsignedMath;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

@SuppressWarnings("unused")
public class Primitives implements ToString {
	
	public static final Primitives BYTE = new Primitives(1, false);
	public static final Primitives BOOLEAN = new Primitives(1, false);
	public static final Primitives POINTER = new Primitives(SystemInfo.archBits / 8, false);
	
	public static final Primitives NINT8 = new Primitives(1, false);
	public static final Primitives NINT16 = new Primitives(2, false);
	public static final Primitives NINT32 = new Primitives(4, false);
	public static final Primitives NINT64 = new Primitives(8, false);
	
	public static final Primitives UINT8 = new Primitives(1, false);
	public static final Primitives UINT16 = new Primitives(2, false);
	public static final Primitives UINT32 = new Primitives(4, false);
	public static final Primitives UINT64 = new Primitives(8, false);
	
	public static final Primitives INT8 = new Primitives(1, true);
	public static final Primitives INT16 = new Primitives(2, true);
	public static final Primitives INT32 = new Primitives(4, true);
	public static final Primitives INT64 = new Primitives(8, true);
	
	public static final Primitives FP16 = new Primitives(2, false);
	public static final Primitives FP32 = new Primitives(4, false);
	public static final Primitives FP64 = new Primitives(8, false);
	
	public final int BYTES;
	public final int BITS;
	public final boolean ISALIGNED;
	public final int SHIFT;
	public final boolean HASSIGN;
	
	public Primitives(int bytes, boolean hasSign) {
		this.BYTES = bytes;
		this.BITS = this.BYTES * 8;
		this.ISALIGNED = UnsignedMath.isPowerOfTwo(bytes);
		this.SHIFT = ISALIGNED ? UnsignedMath.getPowerOfTwoFloor(BYTES) : 0;
		this.HASSIGN = hasSign;
	}
	
	public Primitives(int BYTES, int BITS, boolean ISALIGNED, int SHIFT, boolean HASSIGN) {
		this.BYTES = BYTES;
		this.BITS = BITS;
		this.ISALIGNED = ISALIGNED;
		this.SHIFT = SHIFT;
		this.HASSIGN = HASSIGN;
	}
	
	public long multiply(long number) {
		return ISALIGNED ? number << SHIFT : number * BYTES;
	}
	
	public long multiply(long number, long add) {
		return (ISALIGNED ? number << SHIFT : number * BYTES) + add;
	}
	
	public long divide(long number) {
		return ISALIGNED ? number >>> SHIFT : number / BYTES;
	}
	
	@NotNull
	@Override
	public <TSHTYPE> TSHTYPE toTSH(@NotNull ToStringHelper<TSHTYPE> api) {
		ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
		tsh.add("BYTES", this.BYTES);
		tsh.add("BITS", this.BITS);
		tsh.add("ISALIGNED", this.ISALIGNED);
		tsh.add("SHIFT", this.SHIFT);
		tsh.add("HASSIGN", this.HASSIGN);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
