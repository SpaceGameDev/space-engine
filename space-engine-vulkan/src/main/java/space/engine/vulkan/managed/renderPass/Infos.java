package space.engine.vulkan.managed.renderPass;

public abstract class Infos {
	
	public final int frameBufferIndex;
	
	public Infos(int frameBufferIndex) {
		this.frameBufferIndex = frameBufferIndex;
	}
}
