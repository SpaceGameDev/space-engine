package space.util.primitive;

import org.jetbrains.annotations.NotNull;
import space.util.SystemInfo;
import space.util.baseobject.ToString;
import space.util.math.UnsignedMath;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

@SuppressWarnings("unused")
public class NativeType implements ToString {
	
	public static final NativeType BYTE = new NativeType(1, false);
	public static final NativeType BOOLEAN = new NativeType(1, false);
	public static final NativeType POINTER = new NativeType(SystemInfo.archBits / 8, false);
	
	public static final NativeType NINT8 = new NativeType(1, false);
	public static final NativeType NINT16 = new NativeType(2, false);
	public static final NativeType NINT32 = new NativeType(4, false);
	public static final NativeType NINT64 = new NativeType(8, false);
	
	public static final NativeType UINT8 = new NativeType(1, false);
	public static final NativeType UINT16 = new NativeType(2, false);
	public static final NativeType UINT32 = new NativeType(4, false);
	public static final NativeType UINT64 = new NativeType(8, false);
	
	public static final NativeType INT8 = new NativeType(1, true);
	public static final NativeType INT16 = new NativeType(2, true);
	public static final NativeType INT32 = new NativeType(4, true);
	public static final NativeType INT64 = new NativeType(8, true);
	
	public static final NativeType FP16 = new NativeType(2, false);
	public static final NativeType FP32 = new NativeType(4, false);
	public static final NativeType FP64 = new NativeType(8, false);
	
	public final int BYTES;
	public final int BITS;
	public final boolean ISALIGNED;
	public final int SHIFT;
	public final boolean HASSIGN;
	
	public NativeType(int bytes, boolean hasSign) {
		this.BYTES = bytes;
		this.BITS = this.BYTES * 8;
		this.ISALIGNED = UnsignedMath.isPowerOfTwo(bytes);
		this.SHIFT = ISALIGNED ? UnsignedMath.getPowerOfTwoFloor(BYTES) : 0;
		this.HASSIGN = hasSign;
	}
	
	public NativeType(int BYTES, int BITS, boolean ISALIGNED, int SHIFT, boolean HASSIGN) {
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
