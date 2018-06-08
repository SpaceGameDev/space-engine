package space.util.buffer.pointer;

import space.util.buffer.DelegatingBuffer;
import space.util.buffer.direct.DirectBuffer;
import space.util.primitive.Primitive;

public class AbstractPointerBuffer extends DelegatingBuffer<DirectBuffer> {
	
	public final Primitive<?> type;
	
	public AbstractPointerBuffer(DirectBuffer buffer, Primitive<?> type) {
		super(buffer);
		this.type = type;
	}
}
