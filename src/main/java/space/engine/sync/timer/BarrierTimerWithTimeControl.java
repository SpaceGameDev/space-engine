package space.engine.sync.timer;

/**
 * A {@link BarrierTimer} with extensive methods for manipulating speed and warping time
 */
public class BarrierTimerWithTimeControl extends BarrierTimer {
	
	private double speedNanos;
	private long offsetNanos;
	
	/**
	 * Timer with normal speed starting at the point in time this Constructor is called
	 */
	public BarrierTimerWithTimeControl() {
		this(1.0, -System.nanoTime());
	}
	
	/**
	 * Timer with normal speed starting at a specified point in time
	 */
	public BarrierTimerWithTimeControl(long offsetNanos) {
		this(1.0, offsetNanos);
	}
	
	/**
	 * Timer with variable speed starting at a point in time
	 */
	public BarrierTimerWithTimeControl(double speedNanos, long offsetNanos) {
		this.speedNanos = speedNanos;
		this.offsetNanos = offsetNanos;
	}
	
	//override
	@Override
	public long timeFunction(long input) {
		return (long) (input / speedNanos) - (System.nanoTime() + offsetNanos);
	}
	
	@Override
	public long currTime() {
		return (long) ((System.nanoTime() + offsetNanos) / speedNanos);
	}
	
	@Override
	public double currSpeed() {
		return speedNanos;
	}
	
	//speed
	public void setSpeed(double speedNanos) {
		this.speedNanos = speedNanos;
		recalculateTimer();
	}
	
	public void pause() {
		setSpeed(0);
	}
	
	public void normalSpeed() {
		setSpeed(1);
	}
	
	//offset
	public void warpTo(long offsetNanos) {
		if (offsetNanos < this.offsetNanos)
			throw new IllegalStateException("warping to the past!");
		this.offsetNanos = offsetNanos;
		recalculateTimer();
	}
	
	public void warpBy(long deltaNanos) {
		warpTo(offsetNanos + deltaNanos);
	}
}
