package space.util.creatorOld.dimensional;

import space.util.creatorOld.Creator;

@Deprecated
public interface CreatorDimensional<OBJ extends CreatableObjectDimensional<CREATOR, ?>, CREATOR extends Creator<? extends OBJ>> {
	
	CREATOR getCreator(int dim);
	
	default CREATOR getCreator(OBJ obj) {
		return getCreator(obj.getDimCnt());
	}
}
