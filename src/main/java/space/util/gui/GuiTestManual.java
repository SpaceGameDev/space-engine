package space.util.gui;

import space.util.gui.elements.direction.GuiColumnCreator;
import space.util.gui.elements.direction.GuiColumnCreator.GuiColumn;
import space.util.gui.elements.direction.GuiRowCreator;
import space.util.gui.elements.direction.GuiRowCreator.GuiRow;
import space.util.gui.elements.direction.GuiTableCreator;
import space.util.gui.elements.direction.GuiTableCreator.GuiTable;
import space.util.gui.elements.text.GuiText1DCreator;
import space.util.gui.elements.text.GuiText2DCreator;
import space.util.gui.monofont.MonofontGuiApi;
import space.util.gui.monofont.MonofontGuiElement;
import space.util.string.builder.CharBufferBuilder2D;

public class GuiTestManual {
	
	public static void main(String[] args) {
		GuiApi api = MonofontGuiApi.INSTANCE;
		println(api.get(GuiText1DCreator.class).create("Hi!"));
	}
	
	public static void work(GuiApi api) {
		println(api.get(GuiText1DCreator.class).create("Hi!"));
		
		GuiRow row = api.get(GuiRowCreator.class).create();
		row.add(api.get(GuiText2DCreator.class).create(new CharBufferBuilder2D<>().append("First!").nextLine().append("Secound").nextLine().append("Third.").nextLine().append("Forth").nextLine().append("Last...").nextLine().append("...").nextLine().append(".").nextLine().append("I really don't").nextLine().append("know where I'm").nextLine().append("going with this :)")));
		
		GuiColumn column = api.get(GuiColumnCreator.class).create();
		column.add(api.get(GuiText1DCreator.class).create("Hi there!!!"));
		column.add(api.get(GuiText1DCreator.class).create("I'm writing things..."));
		row.add(column);
		
		GuiTable table = api.get(GuiTableCreator.class).create();
		table.put(new int[] {0, 0}, api.get(GuiText1DCreator.class).create("7"));
		table.put(new int[] {0, 1}, api.get(GuiText1DCreator.class).create("8"));
		table.put(new int[] {0, 2}, api.get(GuiText1DCreator.class).create("9"));
		table.put(new int[] {1, 0}, api.get(GuiText1DCreator.class).create("4"));
		table.put(new int[] {1, 1}, api.get(GuiText1DCreator.class).create("mid"));
		table.put(new int[] {1, 2}, api.get(GuiText1DCreator.class).create("6"));
		table.put(new int[] {2, 0}, api.get(GuiText1DCreator.class).create("1"));
		table.put(new int[] {2, 1}, api.get(GuiText1DCreator.class).create("2"));
		table.put(new int[] {2, 2}, api.get(GuiText1DCreator.class).create("3"));
		table.put(new int[] {3, 1}, api.get(GuiText1DCreator.class).create("0"));
		table.put(new int[] {3, 2}, api.get(GuiText1DCreator.class).create(","));
		row.add(table);
		
		println(row);
	}
	
	public static void println(GuiElement elem) {
		System.out.println(((MonofontGuiElement) elem).buildSequence2D());
	}
}
