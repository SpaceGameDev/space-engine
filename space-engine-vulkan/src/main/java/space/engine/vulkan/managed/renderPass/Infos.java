package space.engine.vulkan.managed.renderPass;

import org.jetbrains.annotations.NotNull;
import space.engine.freeableStorage.Freeable;
import space.engine.freeableStorage.Freeable.FreeableWrapper;
import space.engine.sync.barrier.BarrierImpl;

import static space.engine.Empties.EMPTY_OBJECT_ARRAY;

public abstract class Infos implements FreeableWrapper {
	
	public final int frameBufferIndex;
	public final BarrierImpl frameDone = new BarrierImpl();
	
	public Infos(int frameBufferIndex) {
		this.frameBufferIndex = frameBufferIndex;
		frameDone.addHook(storage::free);
	}
	
	//storage
	public final Freeable storage = Freeable.createDummy(EMPTY_OBJECT_ARRAY);
	
	@Override
	public @NotNull Freeable getStorage() {
		return storage;
	}
}
