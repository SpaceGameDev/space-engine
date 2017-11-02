package space.util.creatorOld;

import space.util.annotation.HasAllocations;

@Deprecated
@FunctionalInterface
public interface Creator<OBJ> {
	
	@HasAllocations
	OBJ create();
	
	@HasAllocations
	@SuppressWarnings("unchecked")
	default OBJ[] createArray(int size) {
		return (OBJ[]) new Object[size];
	}
	
	@HasAllocations
	default OBJ[] createFilledArray(int size) {
		OBJ[] array = createArray(size);
		for (int i = 0; i < size; i++)
			array[i] = create();
		return array;
	}
}
