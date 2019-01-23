package space.util.event.typehandler;

import org.jetbrains.annotations.NotNull;

public class TypeRunnable implements TypeHandler<Runnable>, AllowMultithreading {
	
	public static final TypeRunnable INSTANCE = new TypeRunnable();
	
	private TypeRunnable() {
	}
	
	@Override
	public void accept(@NotNull Runnable runnable) {
		runnable.run();
	}
}
