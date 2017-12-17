package space.util.vfs.virtual;

import space.util.vfs.AbstractEntry;
import space.util.vfs.interfaces.File;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class VirtualFile extends AbstractEntry implements File {
	
	protected ByteArrayOutputStream stream;
	
	public VirtualFile(String name) {
		super(name);
	}
	
	@Override
	public long getByteSize() {
		return stream.size();
	}
	
	@Override
	public InputStream getInputStream() {
		return new ByteArrayInputStream(stream.toByteArray());
	}
	
	@Override
	public OutputStream getOutputStream() {
		return stream;
	}
}
