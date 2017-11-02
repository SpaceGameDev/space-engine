package space.util.creatorOld;

@Deprecated
public interface CreatableObject<CREATOR extends Creator<?>> {
	
	CREATOR creator();
}
