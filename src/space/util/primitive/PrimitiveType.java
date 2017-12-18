package space.util.primitive;

import space.util.SystemInfo;

@SuppressWarnings("unused")
public enum PrimitiveType {
	
	BYTE(1, false),
	BOOLEAN(1, false),
	POINTER(SystemInfo.SYSTEMINFO.archBits, false),
	
	NINT8(1, false),
	NINT16(2, false),
	NINT32(3, false),
	NINT64(4, false),
	
	UINT8(1, false),
	UINT16(2, false),
	UINT32(3, false),
	UINT64(4, false),
	
	INT8(1, true),
	INT16(2, true),
	INT32(3, true),
	INT64(4, true),
	
	FP16(1, false),
	FP32(2, false),
	FP64(3, false);
	
	public final int SHIFT;
	public final int BYTES;
	public final int BITS;
	public final int MASK_SIGN;
	public final int MASK_NUMBER;
	
	PrimitiveType(int bytes, boolean hasSign) {
		this.SHIFT = Integer.numberOfTrailingZeros(bytes);
		this.BYTES = bytes;
		this.BITS = this.BYTES * 8;
		
		if (hasSign) {
			this.MASK_SIGN = 1 << (BITS - 1);
			this.MASK_NUMBER = MASK_SIGN - 1;
		} else {
			this.MASK_NUMBER = (1 << BITS) - 1;
			this.MASK_SIGN = 0;
		}
	}
	
}
