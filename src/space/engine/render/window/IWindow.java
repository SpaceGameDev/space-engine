package space.engine.render.window;

public interface IWindow {
	
	void makeContextCurrent();
	
	void swapBuffers();
	
	void pollEvents();
	
	void destroy();
}
