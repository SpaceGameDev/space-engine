package space.util.gui.monofont;

import space.util.gui.elements.direction.GuiColumn;
import space.util.gui.elements.direction.GuiDirectional;
import space.util.gui.elements.direction.GuiRow;
import space.util.gui.elements.direction.GuiTable;
import space.util.gui.elements.text.GuiText1D;
import space.util.gui.elements.text.GuiText2DCreator;
import space.util.gui.elements.tsh.GuiArray;
import space.util.gui.elements.tsh.GuiModifier;
import space.util.gui.elements.tsh.GuiVariable;
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
import space.util.string.toStringHelper.ToStringHelper;

public class MonofontGuiApi extends SimpleGuiApi<MonofontGuiElement> implements ToStringHelper<MonofontGuiElement> {
	
	public static final MonofontGuiApi INSTANCE = new MonofontGuiApi();
	
	protected MonofontGuiApi() {
		//direction
		addElements(MonofontColumn::new, MonofontColumn.class, GuiColumn.class);
		addElements(MonofontRow::new, MonofontRow.class, GuiRow.class);
		addElements(MonofontDirectional::new, MonofontDirectional.class, GuiDirectional.class);
		addElements(MonofontTable::new, MonofontTable.class, GuiTable.class);
		//text
		addElements(MonofontText1D::new, MonofontText1D.class, GuiText1D.class);
		addElements(MonofontText2D::new, MonofontText2D.class, GuiText2DCreator.class);
		//tsh
		addElements(MonofontArray::new, MonofontArray.class, GuiArray.class);
		addElements(MonofontArray::new, MonofontModifier.class, GuiModifier.class);
		addElements(MonofontVariable::new, MonofontVariable.class, GuiVariable.class);
//		new SimpleGuiElementSearcher<>(this, false).run();
	}
	
	@Override
	public Class<MonofontGuiElement> getBaseElementClass() {
		return MonofontGuiElement.class;
	}
}
