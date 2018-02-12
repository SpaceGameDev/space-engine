package space.util.baseobject.additional;

public interface Freeable {
	
	/**
	 * May be called by any thread any time.
	 * It has to be synchronized internally and ensured that multiple calls don't cause problems.
	 */
	void free();
}
