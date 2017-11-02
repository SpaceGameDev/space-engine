package space.util.string.toStringHelperOld.objects;

import space.util.string.toStringHelperOld.ToStringHelperCollection;

public abstract class AbstractTSHObjects implements TSHObjects {
	
	public ToStringHelperCollection helperCollection;
	
	@Override
	public void setToStringHelperCollection(ToStringHelperCollection coll) {
		this.helperCollection = coll;
	}
	
	public static class Entry {
		
		public String name;
		public String value;
		
		public Entry(String name, String value) {
			this.name = name;
			this.value = value;
		}
		
		@Override
		public String toString() {
			return name + ": " + value;
		}
	}
}
