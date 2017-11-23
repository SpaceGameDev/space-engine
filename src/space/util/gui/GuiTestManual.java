package space.util.gui;

import space.util.gui.elements.direction.GuiTable;
import space.util.gui.elements.text.GuiText1D;
import space.util.gui.elements.text.GuiText2D;
import space.util.gui.monofont.MonofontGuiApi;
import space.util.gui.monofont.MonofontGuiElement;
import space.util.gui.monofont.elements.direction.MonofontColumn;
import space.util.gui.monofont.elements.direction.MonofontRow;
import space.util.gui.monofont.elements.direction.MonofontTable;
import space.util.gui.monofont.tableCreator.MonofontTableCreatorIncludingTable;
import space.util.string.builder.CharBufferBuilder2D;

public class GuiTestManual {
	
	public static void main(String[] args) {
		GuiApi<?> api = MonofontGuiApi.INSTANCE;
		
		MonofontRow row = api.create(MonofontRow.class);
		row.setMonofontStyle(MonofontTableCreatorIncludingTable.DEFAULT);
		row.add(api.create(GuiText2D.class).setCharSequence(new CharBufferBuilder2D<>().append("First!").nextLine().append("Secound").nextLine().append("Third.").nextLine().append("Forth").nextLine().append("Last...").nextLine().append("...").nextLine().append(".").nextLine().append("I really don't").nextLine().append("know where I'm").nextLine().append("going with this :)")));
		
		MonofontColumn column = api.create(MonofontColumn.class);
		column.setMonofontStyle(MonofontTableCreatorIncludingTable.DEFAULT);
		column.add(api.create(GuiText1D.class).setCharSequence("Hi there!!!"));
		column.add(api.create(GuiText1D.class).setCharSequence("I'm writing things..."));
		row.add(column);
		
		MonofontTable table = api.create(GuiTable.class);
		table.setMonofontStyle(MonofontTableCreatorIncludingTable.DEFAULT);
		table.put(new int[] {0, 0}, api.create(GuiText1D.class).setCharSequence("7"));
		table.put(new int[] {0, 1}, api.create(GuiText1D.class).setCharSequence("8"));
		table.put(new int[] {0, 2}, api.create(GuiText1D.class).setCharSequence("9"));
		table.put(new int[] {1, 0}, api.create(GuiText1D.class).setCharSequence("4"));
		table.put(new int[] {1, 1}, api.create(GuiText1D.class).setCharSequence("mid"));
		table.put(new int[] {1, 2}, api.create(GuiText1D.class).setCharSequence("6"));
		table.put(new int[] {2, 0}, api.create(GuiText1D.class).setCharSequence("1"));
		table.put(new int[] {2, 1}, api.create(GuiText1D.class).setCharSequence("2"));
		table.put(new int[] {2, 2}, api.create(GuiText1D.class).setCharSequence("3"));
		table.put(new int[] {3, 1}, api.create(GuiText1D.class).setCharSequence("0"));
		table.put(new int[] {3, 2}, api.create(GuiText1D.class).setCharSequence(","));
		row.add(table);
		
		println(row);
	}
	
	public static void println(GuiElement<?> elem) {
		System.out.println(((MonofontGuiElement) elem).build());
	}
}
