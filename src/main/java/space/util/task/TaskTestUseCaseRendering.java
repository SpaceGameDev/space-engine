package space.util.task;

import space.util.task.impl.CallableTaskWithExceptionImpl;
import space.util.task.impl.RunnableTaskImpl;

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
	public static Task createWindow(String name) {
		return new RunnableTaskImpl() {
			@Override
			protected void execute() {
				println("create Window: '" + name + "'");
			}
			
			@Override
			protected void submit1(Runnable toRun) {
				WINDOW_POOL.execute(toRun);
			}
		};
	}
	
	public static Task closeWindow() {
		return new RunnableTaskImpl() {
			@Override
			protected void execute() {
				println("close Window");
			}
			
			@Override
			protected void submit1(Runnable toRun) {
				WINDOW_POOL.execute(toRun);
			}
		};
	}
	
	public static CallableTaskWithException<float[], IOException> loadVertexData() {
		return new CallableTaskWithExceptionImpl<>(IOException.class) {
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
	public static Task renderFrame(float[] triangles) {
//		return new SequentialMultiTask(new Task[] {
//				new RunnableTaskImpl() {
//					@Override
//					protected void execute() {
//						println("clear buffer");
//					}
//
//					@Override
//					protected void submit1(Runnable toRun) {
//						WINDOW_POOL.execute(toRun);
//					}
//				},
//				new RunnableTaskImpl() {
//					@Override
//					protected void execute() {
//						println("render triangles: " + Arrays.toString(triangles));
//					}
//
//					@Override
//					protected void submit1(Runnable toRun) {
//						WINDOW_POOL.execute(toRun);
//					}
//				}
//		});
		
		return new RunnableTaskImpl() {
			@Override
			protected void execute() {
				println("clear buffer");
				println("render triangles: " + Arrays.toString(triangles));
			}
			
			@Override
			protected void submit1(Runnable toRun) {
				WINDOW_POOL.execute(toRun);
			}
		};
	}
	
	public static Task swapBuffers() {
		return new RunnableTaskImpl() {
			@Override
			protected void execute() {
				println("swap buffer");
			}
			
			@Override
			protected void submit1(Runnable toRun) {
				WINDOW_POOL.execute(toRun);
			}
		};
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
		Task create_window = createWindow("My Window").submit();
		
		try {
			//loading and rendering
			CallableTaskWithException<float[], IOException> taskLoadVertexData = loadVertexData().submit();
			float[] vertexData = taskLoadVertexData.get();
			
			create_window.await();
			for (int i = 0; i < 10; i++) {
				Task taskRenderFrame = renderFrame(vertexData).submit();
				swapBuffers().submit(taskRenderFrame).await();
				
				if (DO_SLEEP_BETWEEN_FRAMES)
					Thread.sleep(1000L / 60);
			}
		} finally {
			closeWindow().submit(create_window).await();
		}
	}
}
