package space.util.unsafe;

import space.util.string.CharSequence2D;
import space.util.string.builder.CharBufferBuilder2D;

import static space.util.buffer.direct.AllowBooleanArrayCopy.ALLOW_BOOLEAN_ARRAY_COPY;
import static sun.misc.Unsafe.*;

public class VMDefinitionInfoDebug {
	
	public static void main(String[] args) {
		System.out.println(getArrayInfo());
	}
	
	public static CharSequence2D getArrayInfo() {
		CharBufferBuilder2D<?> b = new CharBufferBuilder2D<>();
		b.append("byte:    ").append(ARRAY_BYTE_BASE_OFFSET).append(" + ").append(ARRAY_BYTE_INDEX_SCALE).append(" * index").nextLine();
		b.append("short:   ").append(ARRAY_SHORT_BASE_OFFSET).append(" + ").append(ARRAY_SHORT_INDEX_SCALE).append(" * index").nextLine();
		b.append("int:     ").append(ARRAY_INT_BASE_OFFSET).append(" + ").append(ARRAY_INT_INDEX_SCALE).append(" * index").nextLine();
		b.append("long:    ").append(ARRAY_LONG_BASE_OFFSET).append(" + ").append(ARRAY_LONG_INDEX_SCALE).append(" * index").nextLine();
		b.append("float:   ").append(ARRAY_FLOAT_BASE_OFFSET).append(" + ").append(ARRAY_FLOAT_INDEX_SCALE).append(" * index").nextLine();
		b.append("double:  ").append(ARRAY_DOUBLE_BASE_OFFSET).append(" + ").append(ARRAY_DOUBLE_INDEX_SCALE).append(" * index").nextLine();
		b.append("boolean: ").append(ARRAY_BOOLEAN_BASE_OFFSET).append(" + ").append(ARRAY_BOOLEAN_INDEX_SCALE).append(" * index").nextLine();
		b.append("object:  ").append(ARRAY_OBJECT_BASE_OFFSET).append(" + ").append(ARRAY_OBJECT_INDEX_SCALE).append(" * index").nextLine();
		b.append("AllowBooleanArrayCopy: ").append(ALLOW_BOOLEAN_ARRAY_COPY);
		return b;
	}
}
