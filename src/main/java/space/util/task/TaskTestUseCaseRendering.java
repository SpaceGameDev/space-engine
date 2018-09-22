package space.util.task;

import space.util.task.impl.CallableTaskWithExceptionImpl;
import space.util.task.impl.RunnableTaskImpl;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.Executor;

public class TaskTestUseCaseRendering {
	
	public static final Executor WINDOW_POOL = Runnable::run;
	public static final Executor COMPUTE_POOL = Runnable::run;
	public static final boolean VERTEX_DATA_FORCE_IO_EXCEPTION = false;
	public static final boolean DO_SLEEP_BETWEEN_FRAMES = false;
	
	//setup
	public static Task createWindow(String name) {
		return new RunnableTaskImpl() {
			@Override
			protected void execute() {
				System.out.println("create Window: '" + name + "'");
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
				System.out.println("close Window");
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
//						System.out.println("clear buffer");
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
//						System.out.println("render triangles: " + Arrays.toString(triangles));
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
				System.out.println("clear buffer");
				System.out.println("render triangles: " + Arrays.toString(triangles));
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
				System.out.println("swap buffer");
			}
			
			@Override
			protected void submit1(Runnable toRun) {
				WINDOW_POOL.execute(toRun);
			}
		};
	}
	
	public static void main(String[] args) throws InterruptedException {
		CallableTaskWithException<float[], IOException> taskLoadVertexData = loadVertexData().submit();
		float[] vertexData = new float[0];
		try {
			vertexData = taskLoadVertexData.get();
		} catch (IOException e) {
			System.out.println("Can't load vertecies: IOException");
			e.printStackTrace();
			return;
		}
		
		createWindow("My Window").submit();
		
		for (int i = 0; i < 5 * 60; i++) {
			Task taskRenderFrame = renderFrame(vertexData).submit();
			swapBuffers().submit(taskRenderFrame).await();
			
			if (DO_SLEEP_BETWEEN_FRAMES)
				Thread.sleep(1000L / 60);
		}
		
		closeWindow().submit().await();
	}
}
