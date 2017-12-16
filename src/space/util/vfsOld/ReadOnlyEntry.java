package space.util.vfsOld;

import spaceOld.util.string.builder.IStringBuilder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ReadOnlyEntry extends Entry {
	
	public Entry th;
	
	public ReadOnlyEntry(Entry th) {
		super(th.name);
		this.th = th;
	}
	
	@Override
	public InputStream getInputStream(int index, String[] path) throws IOException {
		return th.getInputStream(index, path);
	}
	
	@Override
	public OutputStream getOutputStream(int index, String[] path) throws IOException {
		throw new IOException("Thing " + th + " is read-only!");
	}
	
	@Override
	public File getFilePath(int index, String[] path) throws IOException {
		return th.getFilePath(index, path);
	}
	
	@Override
	public void tree(IStringBuilder b, int spaces) {
		th.tree(b, spaces);
	}
}
