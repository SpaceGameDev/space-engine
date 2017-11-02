package space.util.creatorOld.dimensional;

import space.util.creatorOld.Creator;

@Deprecated
public interface CreatableObjectDimensional<CREATOR extends Creator<?>, CREATORDIM extends CreatorDimensional<?, CREATOR>> {
	
	CREATORDIM creator();
	
	@SuppressWarnings("unchecked")
	default CREATOR creator(int dim) {
		return creator().getCreator(getDimCnt());
	}
	
	int getDimCnt();
}
