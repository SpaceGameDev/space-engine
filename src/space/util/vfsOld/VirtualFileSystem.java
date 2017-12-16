package space.util.vfsOld;

import spaceOld.util.string.builder.IStringBuilder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class VirtualFileSystem extends Entry {
	
	public String splitRegex;
	public Entry rootFolder;
	
	public VirtualFileSystem(String splitRegex, Entry th) {
		this(splitRegex);
		setRootFolder(rootFolder);
	}
	
	public VirtualFileSystem(String splitRegex) {
		super("");
		this.splitRegex = splitRegex;
	}
	
	public VirtualFileSystem() {
		this("/");
	}
	
	public VirtualFileSystem setRootFolder(Entry rootFolder) {
		this.rootFolder = rootFolder;
		rootFolder.name = "root";
		return this;
	}
	
	public InputStream getInputStream(String path) throws IOException {
		return getInputStream(path.split(splitRegex));
	}
	
	public OutputStream getOutputStream(String path) throws IOException {
		return getOutputStream(path.split(splitRegex));
	}
	
	public File getFilePath(String path) throws IOException {
		return getFilePath(path.split(splitRegex));
	}
	
	@Override
	public InputStream getInputStream(int index, String[] path) throws IOException {
		return rootFolder.getInputStream(index, path);
	}
	
	@Override
	public OutputStream getOutputStream(int index, String[] path) throws IOException {
		return rootFolder.getOutputStream(index, path);
	}
	
	@Override
	public File getFilePath(int index, String[] path) throws IOException {
		return rootFolder.getFilePath(index, path);
	}
	
	@Override
	public void tree(IStringBuilder b, int spaces) {
		rootFolder.tree(b, spaces);
	}
}
