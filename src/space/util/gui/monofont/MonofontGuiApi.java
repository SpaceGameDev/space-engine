package space.util.gui.monofont;

import space.util.gui.elements.direction.GuiColumnCreator;
import space.util.gui.elements.direction.GuiDirectionalCreator;
import space.util.gui.elements.direction.GuiRowCreator;
import space.util.gui.elements.direction.GuiTableCreator;
import space.util.gui.elements.text.GuiText1DCreator;
import space.util.gui.elements.text.GuiText2DCreator;
import space.util.gui.elements.tsh.GuiToStringHelperApiCreator;
import space.util.gui.monofont.elements.direction.MonofontColumn;
import space.util.gui.monofont.elements.direction.MonofontDirectional;
import space.util.gui.monofont.elements.direction.MonofontRow;
import space.util.gui.monofont.elements.direction.MonofontTable;
import space.util.gui.monofont.elements.text.MonofontText1D;
import space.util.gui.monofont.elements.text.MonofontText2D;
import space.util.gui.simple.SimpleGuiApi;
import space.util.string.toStringHelper.ToStringHelper;

public class MonofontGuiApi extends SimpleGuiApi {
	
	public static final MonofontGuiApi INSTANCE = new MonofontGuiApi();
	public static final ToStringHelper<?> TSH = INSTANCE.get(GuiToStringHelperApiCreator.class).create();
	
	protected MonofontGuiApi() {
		//direction
		addElements((GuiColumnCreator) MonofontColumn::new, GuiColumnCreator.class);
		addElements((GuiRowCreator) MonofontRow::new, GuiRowCreator.class);
		addElements((GuiDirectionalCreator) MonofontDirectional::new, GuiDirectionalCreator.class);
		addElements((GuiTableCreator) MonofontTable::new, GuiTableCreator.class);
		//text
		addElements((GuiText1DCreator) MonofontText1D::new, GuiText1DCreator.class);
		addElements((GuiText2DCreator) MonofontText2D::new, GuiText2DCreator.class);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Class<MonofontGuiElement> getBaseElementClass() {
		return MonofontGuiElement.class;
	}
}
