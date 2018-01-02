package space.util.vfs.fsmount;

import space.util.vfs.File;
import space.util.vfs.Folder;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.OpenOption;

public class MountFile extends AbstractMountEntry implements File {
	
	public MountFile(java.io.File path) {
		super(path);
	}
	
	protected MountFile(java.io.File path, Folder parent) {
		super(path, parent);
	}
	
	@Override
	public long getSizeInBytes() {
		return path.length();
	}
	
	@Override
	public InputStream getInputStream() throws IOException {
		return new FileInputStream(path);
	}
	
	@Override
	public OutputStream getOutputStream() throws IOException {
		return new FileOutputStream(path);
	}
	
	@Override
	public SeekableByteChannel getByteChannel(OpenOption... options) throws IOException {
		return FileChannel.open(path.toPath(), options);
	}
}
