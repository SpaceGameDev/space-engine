package space.engine;

import org.junit.After;
import org.junit.Before;
import space.engine.key.attribute.AttributeListModify;

import static space.engine.Side.*;

public class SingleThreadPoolTest {
	
	@Before
	public void before() throws InterruptedException {
		AttributeListModify<Side> modify = side().createModify();
		modify.put(EXECUTOR_POOL, Runnable::run);
		modify.apply().await();
	}
	
	@After
	public void after() throws InterruptedException {
		AttributeListModify<Side> modify = side().createModify();
		modify.reset(EXECUTOR_POOL);
		modify.apply().await();
	}
}
