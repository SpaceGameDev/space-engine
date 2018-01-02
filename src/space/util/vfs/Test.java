package space.util.vfs;

import space.util.gui.monofont.MonofontGuiApi;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.vfs.fsmount.MountFolder;
import space.util.vfs.virtual.VirtualFolder;

import java.io.IOException;

public class Test {
	
	public static void main(String[] args) throws IOException {
		ToStringHelper.setDefault(MonofontGuiApi.TSH);
		
		VirtualFolder root = new VirtualFolder("");
		File hiFile = root.addFile("Hi!");
		root.addFile("2");
		root.addFile("3");
		root.addFile("4");
		root.addFile("END");
		root.addFile(Integer.toString(Integer.MAX_VALUE));
		root.addFile(Integer.toString(Integer.MAX_VALUE - 1));
		
		Folder newFolder1 = root.addFolder("new folder1");
		newFolder1.addLink("linkToHi", hiFile);
		System.out.println(root);
		
		MountFolder mount = new MountFolder(new java.io.File("/home/sebastian/test"));
		root.addLink("testing", mount);
		mount.addFile("bla2");
		System.out.println(mount);
	}
}
