package space.util.gui.monofont;

import space.util.gui.AbstractGuiApi;
import space.util.gui.elements.direction.GuiColumnCreator;
import space.util.gui.elements.direction.GuiDirectionalCreator;
import space.util.gui.elements.direction.GuiRowCreator;
import space.util.gui.elements.direction.GuiTableCreator;
import space.util.gui.elements.text.GuiText1DCreator;
import space.util.gui.elements.text.GuiText2DCreator;
import space.util.gui.monofont.elements.direction.MonofontColumn;
import space.util.gui.monofont.elements.direction.MonofontDirectional;
import space.util.gui.monofont.elements.direction.MonofontRow;
import space.util.gui.monofont.elements.direction.MonofontTable;
import space.util.gui.monofont.elements.text.MonofontText1D;
import space.util.gui.monofont.elements.text.MonofontText2D;
import space.util.gui.monofont.tsh.MonofontToStringHelper;
import space.util.gui.monofont.tsh.elements.MonofontTSHArray;
import space.util.gui.monofont.tsh.elements.MonofontTSHMapper;
import space.util.gui.monofont.tsh.elements.MonofontTSHModifier;
import space.util.gui.monofont.tsh.elements.MonofontTSHObjects;
import space.util.gui.monofont.tsh.elements.MonofontTSHTable;
import space.util.gui.tsh.GuiToStringHelperApiCreator;
import space.util.gui.tsh.elements.GuiToStringHelperArrayCreator;
import space.util.gui.tsh.elements.GuiToStringHelperMapperCreator;
import space.util.gui.tsh.elements.GuiToStringHelperModifierCreator;
import space.util.gui.tsh.elements.GuiToStringHelperObjectsCreator;
import space.util.gui.tsh.elements.GuiToStringHelperTableCreator;
import space.util.string.toStringHelper.ToStringHelper;

public class MonofontGuiApi extends AbstractGuiApi {
	
	public static final MonofontGuiApi INSTANCE = new MonofontGuiApi();
	public static final ToStringHelper<?> TSH = new MonofontToStringHelper(INSTANCE);
	
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
		addElement((GuiToStringHelperApiCreator) () -> TSH, GuiToStringHelperApiCreator.class);
		addElement(MonofontTSHArray.CREATOR, GuiToStringHelperArrayCreator.class);
		addElement(MonofontTSHMapper.CREATOR, GuiToStringHelperMapperCreator.class);
		addElement(MonofontTSHModifier.CREATOR, GuiToStringHelperModifierCreator.class);
		addElement(MonofontTSHObjects.CREATOR, GuiToStringHelperObjectsCreator.class);
		addElement(MonofontTSHTable.CREATOR, GuiToStringHelperTableCreator.class);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Class<MonofontGuiElement> getBaseElementClass() {
		return MonofontGuiElement.class;
	}
}
