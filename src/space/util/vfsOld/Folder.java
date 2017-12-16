package space.util.vfsOld;

import spaceOld.util.string.builder.IStringBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;

public class Folder extends Entry {
	
	public HashMap<String, Entry> list = new HashMap<>();
	
	public Folder(String name) {
		super(name);
	}
	
	public void add(Entry th) {
		list.put(th.name, th);
	}
	
	public Entry get(int index, String[] path) throws FileNotFoundException {
		String name = path[index];
		Entry th = list.get(name);
		if (th == null)
			throw new FileNotFoundException("Invalid Path " + Arrays.toString(path) + ": name " + name + " not existent in Folder " + this.name);
		return th;
	}
	
	@Override
	public InputStream getInputStream(int index, String[] path) throws IOException {
		return get(index, path).getInputStream(index + 1, path);
	}
	
	@Override
	public OutputStream getOutputStream(int index, String[] path) throws IOException {
		return get(index, path).getOutputStream(index + 1, path);
	}
	
	@Override
	public File getFilePath(int index, String[] path) throws IOException {
		return get(index, path).getFilePath(index + 1, path);
	}
	
	@Override
	public void tree(IStringBuilder b, int spaces) {
		writeSpaces(b, spaces);
		b.append("d  ");
		b.append(name);
		b.nextLine();
		int spacesNew = spaces + 1;
		for (Entry th : list.values()) {
			th.tree(b, spacesNew);
		}
	}
}
