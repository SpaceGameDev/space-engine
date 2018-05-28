package space.util.primitive;

import static space.util.primitive.Primitives.*;

public class IntegerConst {
	
	public static final int INT8MaskSign = 1 << INT8.BITS - 1;
	public static final int INT8MaskNumber = INT8MaskSign - 1;
	public static final int INT16MaskSign = 1 << INT16.BITS - 1;
	public static final int INT16MaskNumber = INT16MaskSign - 1;
	public static final int INT32MaskSign = 1 << INT32.BITS - 1;
	public static final int INT32MaskNumber = INT32MaskSign - 1;
	public static final int INT64MaskSign = 1 << INT64.BITS - 1;
	public static final int INT64MaskNumber = INT64MaskSign - 1;
}
