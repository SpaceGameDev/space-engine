package space.engine.buffer;

import space.engine.freeableStorage.Freeable;

public abstract class AllocatorStack {
	
	public abstract Frame frame();
	
	public abstract class Frame extends Allocator implements Freeable, AutoCloseable {
		
		@Override
		public void close() {
			free();
		}
	}
}
