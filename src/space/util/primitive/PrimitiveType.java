package space.util.primitive;

import space.util.SystemInfo;

@SuppressWarnings("unused")
public enum PrimitiveType {
	
	BYTE(1),
	BOOLEAN(1),
	POINTER(SystemInfo.SYSTEMINFO.archBits),
	
	NINT8(1),
	NINT16(2),
	NINT32(3),
	NINT64(4),
	
	UINT8(1),
	UINT16(2),
	UINT32(3),
	UINT64(4),
	
	INT8(1),
	INT16(2),
	INT32(3),
	INT64(4),
	
	FP16(1),
	FP32(2),
	FP64(3);
	
	public final int SHIFT;
	public final int BYTES;
	public final int BITS;
	
	PrimitiveType(int bytes) {
		this.SHIFT = Integer.numberOfTrailingZeros(bytes);
		this.BYTES = bytes;
		this.BITS = this.BYTES * 8;
	}
	
}
