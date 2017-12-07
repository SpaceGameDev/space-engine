package space.util.vfs;

import spaceOld.util.string.builder.IStringBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class LocalFileSystemMount extends FileSystemMount {
	
	public LocalFileSystemMount(String name, String location, String splitRegex) {
		super(name, location, splitRegex);
	}
	
	public LocalFileSystemMount(String name, String location) {
		super(name, location);
	}
	
	@Override
	public InputStream getInputStream(int index, String[] path) throws IOException {
		return new FileInputStream(makeAddress(index, path));
	}
	
	@Override
	public OutputStream getOutputStream(int index, String[] path) throws IOException {
		return new FileOutputStream(makeAddress(index, path));
	}
	
	@Override
	public File getFilePath(int index, String[] path) {
		return new File(makeAddress(index, path));
	}
	
	@Override
	public void tree(IStringBuilder b, int spaces) {
		writeSpaces(b, spaces);
		b.append("fs ");
		b.append(name);
		b.append(" -> File: ");
		b.append(location);
		b.nextLine();
	}
}
