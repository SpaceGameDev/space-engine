package space.util.vfs.virtual;

import space.util.vfs.AbstractEntry;
import space.util.vfs.Entry;
import space.util.vfs.File;
import space.util.vfs.Folder;
import space.util.vfs.Link;
import space.util.vfs.exception.UnsupportedEntry;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.SeekableByteChannel;
import java.util.Set;

public abstract class VirtualLink extends AbstractEntry implements Link {
	
	public VirtualLink(String name) {
		super(name);
	}
	
	public static VirtualLink create(String name, Entry entry) throws UnsupportedEntry {
		if (entry instanceof File)
			return new VirtualFileLink(name, (File) entry);
		if (entry instanceof Folder)
			return new VirtualFolderLink(name, (Folder) entry);
		throw new UnsupportedEntry(entry);
	}
	
	public static class VirtualFileLink extends VirtualLink implements File {
		
		public File pointer;
		
		public VirtualFileLink(String name, File pointer) {
			super(name);
			this.pointer = pointer;
		}
		
		@Override
		public Entry getPointer() {
			return pointer;
		}
		
		//delegate
		@Override
		public long getSizeInBytes() {
			return pointer.getSizeInBytes();
		}
		
		@Override
		public InputStream getInputStream() throws IOException {
			return pointer.getInputStream();
		}
		
		@Override
		public OutputStream getOutputStream() throws IOException {
			return pointer.getOutputStream();
		}
		
		@Override
		public SeekableByteChannel getByteChannel() throws IOException {
			return pointer.getByteChannel();
		}
	}
	
	public static class VirtualFolderLink extends VirtualLink implements Folder {
		
		public Folder pointer;
		
		public VirtualFolderLink(String name, Folder pointer) {
			super(name);
			this.pointer = pointer;
		}
		
		@Override
		public Entry getPointer() {
			return pointer;
		}
		
		//delegate
		@Override
		public Set<Entry> getEntries() {
			return pointer.getEntries();
		}
		
		@Override
		public File addFile(String name) throws IOException {
			return pointer.addFile(name);
		}
		
		@Override
		public Folder addFolder(String name) throws IOException {
			return pointer.addFolder(name);
		}
		
		@Override
		public Link addLink(String name, Entry pointer) throws IOException {
			return this.pointer.addLink(name, pointer);
		}
		
		@Override
		public void delete(String name) throws IOException {
			pointer.delete(name);
		}
	}
}
