package space.util.concurrent.task.typehandler;

import java.util.function.Supplier;

public class TypeRunnable {
	
	public static final TypeHandler<Runnable> INSTANCE = Runnable::run;
	public static final Supplier<TypeHandler<Runnable>> SUPPLIER = () -> INSTANCE;
}
