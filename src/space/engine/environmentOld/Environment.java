package space.engine.environmentOld;

import space.engine.manager.ManagerEnvironment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Environment implements IdBasedObject {
	
	//id
	public static final AtomicInteger IDGEN = new AtomicInteger();
	public int id = IDGEN.getAndIncrement();
	//general
	public ManagerEnvironment manager;
	public List<Thread> threads = new ArrayList<>();
	//events
	public EventChainRunnable onStart = new EventChainRunnable();
	public EventChainRunnable onDeath = new EventChainRunnable();
	public EventChainConsumer<Thread> onThreadCreation = new EventChainConsumer<>();
	public EventChainConsumer<Thread> onThreadStart = new EventChainConsumer<>();
	public EventChainConsumer<Thread> onThreadDeath = new EventChainConsumer<>();
	
	@Override
	public int getId() {
		return id;
	}
}
