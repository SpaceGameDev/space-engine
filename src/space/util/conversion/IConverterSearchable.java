package space.util.conversion;

//FIXME: remove
@Deprecated
public interface IConverterSearchable<FROM, TO> extends IConverter<FROM, TO> {
	
	Class<FROM> classFrom();
	
	Class<TO> classTo();
	
	int weight();
}
