package space.engine;

import org.junit.After;
import org.junit.Before;

public class SingleThreadPoolTest {
	
	@Before
	public void before() {
		Side.overrideThreadlocalPool(Runnable::run);
	}
	
	@After
	public void after() {
		Side.overrideThreadlocalPool(null);
	}
}
