package space.util.vfs;

import spaceOld.util.string.builder.IStringBuilder;
import spaceOld.util.string.builder.layered.LayeredStringBuilder;
import spaceOld.util.string.builder.layered.LayeredStringBuilderBase;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class Thing {
	
	public String name;
	
	public Thing(String name) {
		this.name = name;
	}
	
	public InputStream getInputStream(String[] path) throws IOException {
		return getInputStream(0, path);
	}
	
	protected abstract InputStream getInputStream(int index, String[] path) throws IOException;
	
	public OutputStream getOutputStream(String[] path) throws IOException {
		return getOutputStream(0, path);
	}
	
	protected abstract OutputStream getOutputStream(int index, String[] path) throws IOException;
	
	public File getFilePath(String[] path) throws IOException {
		return getFilePath(0, path);
	}
	
	protected abstract File getFilePath(int index, String[] path) throws IOException;
	
	public void tree(IStringBuilder b, int spaces) {
		writeSpaces(b, spaces);
		b.append("th ");
		b.append(name);
		b.nextLine();
	}
	
	public IStringBuilder<?> tree() {
		LayeredStringBuilder b = new LayeredStringBuilderBase();
		tree(b, 0);
		return b;
	}
	
	public static void writeSpaces(IStringBuilder b, int spaces) {
		for (int i = 0; i < spaces; i++) {
			b.append(' ');
		}
	}
}
