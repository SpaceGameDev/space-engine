package space.util.gui.monofont;

import space.util.gui.elements.direction.GuiColumnCreator;
import space.util.gui.elements.direction.GuiDirectionalCreator;
import space.util.gui.elements.direction.GuiRowCreator;
import space.util.gui.elements.direction.GuiTableCreator;
import space.util.gui.elements.text.GuiText1DCreator;
import space.util.gui.elements.text.GuiText2DCreator;
import space.util.gui.elements.tsh.GuiToStringHelperApiCreator;
import space.util.gui.elements.tsh.GuiToStringHelperArrayCreator;
import space.util.gui.elements.tsh.GuiToStringHelperMapperCreator;
import space.util.gui.elements.tsh.GuiToStringHelperModifierCreator;
import space.util.gui.elements.tsh.GuiToStringHelperObjectsCreator;
import space.util.gui.elements.tsh.GuiToStringHelperTableCreator;
import space.util.gui.monofont.elements.direction.MonofontColumn;
import space.util.gui.monofont.elements.direction.MonofontDirectional;
import space.util.gui.monofont.elements.direction.MonofontRow;
import space.util.gui.monofont.elements.direction.MonofontTable;
import space.util.gui.monofont.elements.text.MonofontText1D;
import space.util.gui.monofont.elements.text.MonofontText2D;
import space.util.gui.monofont.elements.tsh.MonofontTSHArray;
import space.util.gui.monofont.elements.tsh.MonofontTSHModifier;
import space.util.gui.monofont.elements.tsh.MonofontTSHTable;
import space.util.gui.simple.SimpleGuiApi;
import space.util.string.toStringHelper.ToStringHelper;

public class MonofontGuiApi extends SimpleGuiApi {
	
	public static final MonofontGuiApi INSTANCE = new MonofontGuiApi();
	public static final ToStringHelper<?> TSH = INSTANCE.get(GuiToStringHelperApiCreator.class).create();
	
	protected MonofontGuiApi() {
		//direction
		addElements(MonofontColumn.CREATOR, GuiColumnCreator.class);
		addElements(MonofontRow.CREATOR, GuiRowCreator.class);
		addElements(MonofontDirectional.CREATOR, GuiDirectionalCreator.class);
		addElements(MonofontTable.CREATOR, GuiTableCreator.class);
		
		//text
		addElements(MonofontText1D.CREATOR, GuiText1DCreator.class);
		addElements(MonofontText2D.CREATOR, GuiText2DCreator.class);
		
		//tsh
		addElement(null, GuiToStringHelperApiCreator.class);
		addElement(MonofontTSHArray.CREATOR, GuiToStringHelperArrayCreator.class);
		addElement(null, GuiToStringHelperMapperCreator.class);
		addElement(MonofontTSHModifier.CREATOR, GuiToStringHelperModifierCreator.class);
		addElement(null, GuiToStringHelperObjectsCreator.class);
		addElement(MonofontTSHTable.CREATOR, GuiToStringHelperTableCreator.class);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Class<MonofontGuiElement> getBaseElementClass() {
		return MonofontGuiElement.class;
	}
}
