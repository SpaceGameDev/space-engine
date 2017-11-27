package space.util.gui.monofont;

import space.util.gui.elements.direction.GuiColumnCreator;
import space.util.gui.elements.direction.GuiDirectionalCreator;
import space.util.gui.elements.direction.GuiRowCreator;
import space.util.gui.elements.direction.GuiTableCreator;
import space.util.gui.elements.text.GuiText1DCreator;
import space.util.gui.elements.text.GuiText2DCreator;
import space.util.gui.elements.tsh.GuiArrayCreator;
import space.util.gui.elements.tsh.GuiModifierCreator;
import space.util.gui.elements.tsh.GuiVariableCreator;
import space.util.gui.monofont.elements.direction.MonofontColumn;
import space.util.gui.monofont.elements.direction.MonofontDirectional;
import space.util.gui.monofont.elements.direction.MonofontRow;
import space.util.gui.monofont.elements.direction.MonofontTable;
import space.util.gui.monofont.elements.text.MonofontText1D;
import space.util.gui.monofont.elements.text.MonofontText2D;
import space.util.gui.monofont.elements.tsh.MonofontArray;
import space.util.gui.monofont.elements.tsh.MonofontModifier;
import space.util.gui.monofont.elements.tsh.MonofontVariable;
import space.util.gui.simple.SimpleGuiApi;

public class MonofontGuiApi extends SimpleGuiApi<MonofontGuiElement<?>> {
	
	public static final MonofontGuiApi INSTANCE = new MonofontGuiApi();
	
	protected MonofontGuiApi() {
		//direction
		addElements(MonofontColumn::new, MonofontColumn.class, GuiColumnCreator.class);
		addElements(MonofontRow::new, MonofontRow.class, GuiRowCreator.class);
		addElements(MonofontDirectional::new, MonofontDirectional.class, GuiDirectionalCreator.class);
		addElements(MonofontTable::new, MonofontTable.class, GuiTableCreator.class);
		//text
		addElements(MonofontText1D::new, MonofontText1D.class, GuiText1DCreator.class);
		addElements(MonofontText2D::new, MonofontText2D.class, GuiText2DCreator.class);
		//tsh
		addElements(MonofontArray::new, MonofontArray.class, GuiArrayCreator.class);
		addElements(MonofontArray::new, MonofontModifier.class, GuiModifierCreator.class);
		addElements(MonofontVariable::new, MonofontVariable.class, GuiVariableCreator.class);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Class<MonofontGuiElement<?>> getBaseElementClass() {
		return (Class<MonofontGuiElement<?>>) (Object) MonofontGuiElement.class;
	}
}
