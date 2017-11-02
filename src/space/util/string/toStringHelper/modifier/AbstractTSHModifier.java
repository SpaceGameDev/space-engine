package space.util.string.toStringHelper.modifier;

public abstract class AbstractTSHModifier implements TSHModifier {
	
	@Override
	public abstract TSHModifierInstance getInstance(String name, Object value);
	
	public static abstract class AbstractTSHModifierInstance implements TSHModifierInstance {
		
		public String modifier;
		public Object value;
		
		public AbstractTSHModifierInstance() {
		}
		
		public AbstractTSHModifierInstance(String modifier, Object value) {
			this.modifier = modifier;
			this.value = value;
		}
		
		@Override
		public abstract String toString();
	}
}
