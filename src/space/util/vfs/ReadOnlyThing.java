package space.util.vfs;

import spaceOld.util.string.builder.IStringBuilder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ReadOnlyThing extends Thing {
	
	public Thing th;
	
	public ReadOnlyThing(Thing th) {
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
