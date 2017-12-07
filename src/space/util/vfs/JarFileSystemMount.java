package space.util.vfs;

import spaceOld.util.string.builder.IStringBuilder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class JarFileSystemMount extends FileSystemMount {
	
	private static final ClassLoader loader = Thread.currentThread().getContextClassLoader();
	
	/**
	 * REMEMBER: Resources in Jars are split with '/' not with '.' !!!
	 */
	public JarFileSystemMount(String name, String location) {
		super(name, location, "/");
	}
	
	public static InputStream getResource(String str) throws IOException {
		InputStream in = loader.getResourceAsStream(str);
		if (in != null) {
			return in;
		}
		throw new IOException("ClassLoader.getResourceAsStream() returned null: " + str);
	}
	
	@Override
	public InputStream getInputStream(int index, String[] path) throws IOException {
		return getResource(makeAddress(index, path));
	}
	
	@Override
	public OutputStream getOutputStream(int index, String[] path) throws IOException {
		throw new IOException("Jars are read-only!");
	}
	
	@Override
	public File getFilePath(int index, String[] path) {
		return new File(makeAddress(index, path));
	}
	
	@Override
	public void tree(IStringBuilder b, int spaces) {
		writeSpaces(b, spaces);
		b.append("ja ");
		b.append(name);
		b.append(" -> Jar: ");
		b.append(location);
		b.nextLine();
	}
}
