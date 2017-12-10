package space.util.unsafe;

import space.util.string.CharSequence2D;
import space.util.string.builder.CharBufferBuilder2D;

import static sun.misc.Unsafe.*;

public class UnsafeArraySizeInfo {
	
	public static void main(String[] args) {
		System.out.println(getArrayInfo());
	}
	
	public static CharSequence2D getArrayInfo() {
		CharBufferBuilder2D b = new CharBufferBuilder2D<>();
		b.append("byte:    " + ARRAY_BYTE_BASE_OFFSET + " + " + ARRAY_BYTE_INDEX_SCALE + " * index").nextLine();
		b.append("short:   " + ARRAY_SHORT_BASE_OFFSET + " + " + ARRAY_SHORT_INDEX_SCALE + " * index").nextLine();
		b.append("int:     " + ARRAY_INT_BASE_OFFSET + " + " + ARRAY_INT_INDEX_SCALE + " * index").nextLine();
		b.append("long:    " + ARRAY_LONG_BASE_OFFSET + " + " + ARRAY_LONG_INDEX_SCALE + " * index").nextLine();
		b.append("float:   " + ARRAY_FLOAT_BASE_OFFSET + " + " + ARRAY_FLOAT_INDEX_SCALE + " * index").nextLine();
		b.append("double:  " + ARRAY_DOUBLE_BASE_OFFSET + " + " + ARRAY_DOUBLE_INDEX_SCALE + " * index").nextLine();
		b.append("boolean: " + ARRAY_BOOLEAN_BASE_OFFSET + " + " + ARRAY_BOOLEAN_INDEX_SCALE + " * index").nextLine();
		b.append("char:    " + ARRAY_CHAR_BASE_OFFSET + " + " + ARRAY_CHAR_INDEX_SCALE + " * index").nextLine();
		b.append("object:  " + ARRAY_OBJECT_BASE_OFFSET + " + " + ARRAY_OBJECT_INDEX_SCALE + " * index").nextLine();
		return b;
	}
}
