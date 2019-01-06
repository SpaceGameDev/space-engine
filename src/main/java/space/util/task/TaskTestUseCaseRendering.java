package space.util.task;

import space.util.sync.barrier.Barrier;
import space.util.task.impl.FutureTaskWithException;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TaskTestUseCaseRendering {
	
	public static final Executor GLOBAL_POOL = new ThreadPoolExecutor(4, 4, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
	public static final Executor WINDOW_POOL = GLOBAL_POOL;
	public static final Executor COMPUTE_POOL = GLOBAL_POOL;
	public static final boolean VERTEX_DATA_FORCE_IO_EXCEPTION = true;
	public static final boolean DO_SLEEP_BETWEEN_FRAMES = false;
	
	public static void println(String str) {
		System.out.println(System.nanoTime() + ": " + str);
	}
	
	//setup
	public static TaskCreator<?> createWindow(String name) {
		return Tasks.create(WINDOW_POOL, () -> println("create Window: '" + name + "'"));
	}
	
	public static TaskCreator<?> closeWindow() {
		return Tasks.create(WINDOW_POOL, () -> println("close Window"));
	}
	
	public static TaskCreator<? extends FutureTaskWithException<float[], IOException>> loadVertexData() {
		return (locks, barriers) -> new FutureTaskWithException<>(IOException.class, locks, barriers) {
			@Override
			protected float[] execute0() throws IOException {
				if (VERTEX_DATA_FORCE_IO_EXCEPTION)
					throw new IOException("Forced IO Exception for testing");
				return new float[] {0f, 0.5f, 0.5f, -0.5f, -0.5f, 0.5f};
			}
			
			@Override
			protected void submit1(Runnable toRun) {
				COMPUTE_POOL.execute(toRun);
			}
		};
	}
	
	//render loop
	public static TaskCreator renderFrame(float[] triangles) {
		//FIXME: static barriers added to dynamic ones (usually args of the method creating the TaskCreator
		return Tasks.create(WINDOW_POOL, () -> {
			println("clear buffer");
			println("render triangles: " + Arrays.toString(triangles));
		});
	}
	
	public static TaskCreator swapBuffers() {
		return Tasks.create(WINDOW_POOL, () -> println("swap buffer"));
	}
	
	public static void main(String[] args) throws Exception {
		try {
			System.out.print(""); //initialization
			test();
		} finally {
			if (GLOBAL_POOL instanceof ThreadPoolExecutor)
				((ThreadPoolExecutor) GLOBAL_POOL).shutdown();
		}
	}
	
	public static void test() throws InterruptedException, IOException {
		Barrier create_window = createWindow("My Window").submit();
		
		try {
			//loading and rendering
			float[] vertexData = loadVertexData().submit().awaitGet();
			create_window.await();
			
			TaskCreator creatorRenderFrame = renderFrame(vertexData);
			TaskCreator taskCreatorSwapBuffers = swapBuffers();
			
			for (int i = 0; i < 10; i++) {
				Barrier taskRenderFrame = creatorRenderFrame.submit();
				Barrier taskSwapBuffers = taskCreatorSwapBuffers.submit(taskRenderFrame);
				
				taskSwapBuffers.await();
				if (DO_SLEEP_BETWEEN_FRAMES)
					Thread.sleep(1000L / 60);
			}
		} finally {
			closeWindow().submit(create_window).await();
		}
	}
}
