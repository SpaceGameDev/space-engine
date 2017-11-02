package space.util.gui.monofont.tableCreator;

public class MonofontTableCreatorDefault {
	
	/**
	 * Don't even ask why this is here and not in the interface {@link IMonofontTableCreator}.
	 * It doesn not seem to write the value into the field, and as Bug reports on Oracle's JDK as well as OpenJDK are sooo anoying to do and the bug won't get fixed either way,
	 * I'm just going to do this and NOT CARE if this is getting fixed any time soon...
	 */
	@SuppressWarnings("unused")
	public static final IMonofontTableCreator STYLE_DEFAULT = MonofontTableCreatorIncludingTable.DEFAULT;
}
