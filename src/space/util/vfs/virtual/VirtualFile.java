package space.util.vfs.virtual;

import space.util.concurrent.lock.rwlock.IRWLock;
import space.util.concurrent.lock.rwlock.RWLock;
import space.util.vfs.AbstractEntry;
import space.util.vfs.File;
import space.util.vfs.Folder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.NonReadableChannelException;
import java.nio.channels.NonWritableChannelException;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.OpenOption;

import static java.nio.file.StandardOpenOption.*;
import static space.util.FlagUtil.hasFlag;
import static space.util.math.MathUtils.min;

public class VirtualFile extends AbstractEntry implements File {
	
	public IRWLock rwLock = new RWLock();
	public volatile byte[] array;
	
	public VirtualFile(String name) {
		super(name);
	}
	
	public VirtualFile(String name, Folder parent) {
		super(name, parent);
	}
	
	@Override
	public long getSizeInBytes() {
		return array.length;
	}
	
	//Stream
	@Override
	public InputStream getInputStream() {
		rwLock.readLock().lock();
		
		return new InputStream() {
			ByteArrayInputStream stream = new ByteArrayInputStream(array);
			boolean isClosed = false;
			
			void checkClosed() throws IOException {
				if (isClosed)
					throw new IOException("Stream closed!");
			}
			
			//delegate
			@Override
			public int read() throws IOException {
				checkClosed();
				return stream.read();
			}
			
			@Override
			public int read(byte[] b) throws IOException {
				checkClosed();
				return stream.read(b);
			}
			
			@Override
			public int read(byte[] b, int off, int len) throws IOException {
				checkClosed();
				return stream.read(b, off, len);
			}
			
			@Override
			public long skip(long n) throws IOException {
				checkClosed();
				return stream.skip(n);
			}
			
			@Override
			public int available() throws IOException {
				checkClosed();
				return stream.available();
			}
			
			@Override
			public synchronized void mark(int readlimit) {
				stream.mark(readlimit);
			}
			
			@Override
			public synchronized void reset() {
				stream.reset();
			}
			
			@Override
			public boolean markSupported() {
				return stream.markSupported();
			}
			
			//close
			@Override
			public void close() throws IOException {
				try {
					super.close();
				} finally {
					rwLock.readLock().unlock();
				}
			}
		};
	}
	
	@Override
	public OutputStream getOutputStream() {
		rwLock.writeLock().lock();
		
		return new OutputStream() {
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			boolean isClosed = false;
			
			void checkClosed() throws IOException {
				if (isClosed)
					throw new IOException("Stream closed!");
			}
			
			//delegate
			@Override
			public void write(byte[] b) throws IOException {
				checkClosed();
				stream.write(b);
			}
			
			@Override
			public void write(byte[] b, int off, int len) throws IOException {
				checkClosed();
				stream.write(b, off, len);
			}
			
			@Override
			public void flush() throws IOException {
				checkClosed();
				array = stream.toByteArray();
			}
			
			@Override
			public void write(int b) throws IOException {
				checkClosed();
				stream.write(b);
			}
			
			//close
			@Override
			public void close() throws IOException {
				try {
					checkClosed();
					isClosed = true;
					stream.close();
					
					array = stream.toByteArray();
				} finally {
					rwLock.writeLock().unlock();
				}
			}
		};
	}
	
	//SeekableByteChannel
	@Override
	public SeekableByteChannel getByteChannel(OpenOption... options) {
		if (hasFlag(options, READ)) {
			rwLock.writeLock().lock();
			return new VirtualFileSeekableByteChannelRead(array);
		}
		
		if (hasFlag(options, WRITE)) {
			rwLock.writeLock().lock();
			return new VirtualFileSeekableByteChannelWrite(hasFlag(options, APPEND) ? array : null, hasFlag(options, SYNC) || hasFlag(options, DSYNC));
		}
		
		throw new IllegalArgumentException("OpenOptions did not contain READ or WRITE");
	}
	
	private abstract class VirtualFileSeekableByteChannelAbstract implements SeekableByteChannel {
		
		byte[] array;
		int position;
		boolean isClosed = false;
		
		public VirtualFileSeekableByteChannelAbstract(byte[] array) {
			this(array, 0);
		}
		
		public VirtualFileSeekableByteChannelAbstract(byte[] array, int position) {
			this.array = array;
			this.position = position;
		}
		
		void checkClosed() throws IOException {
			if (isClosed)
				throw new IOException("Stream closed!");
		}
		
		@Override
		public int read(ByteBuffer dst) throws IOException {
			throw new NonReadableChannelException();
		}
		
		@Override
		public int write(ByteBuffer src) throws IOException {
			throw new NonWritableChannelException();
		}
		
		@Override
		public long position() throws IOException {
			checkClosed();
			return position;
		}
		
		@Override
		public long size() throws IOException {
			checkClosed();
			return array.length;
		}
		
		@Override
		public SeekableByteChannel truncate(long size) {
			throw new NonWritableChannelException();
		}
		
		@Override
		public SeekableByteChannel position(long newPosition) {
			if (position < 0)
				throw new IllegalArgumentException("newPosition was negative: " + newPosition);
			position = (int) newPosition;
			return this;
		}
		
		//close
		@Override
		public boolean isOpen() {
			return !isClosed;
		}
		
		@Override
		public void close() throws IOException {
			rwLock.writeLock().unlock();
		}
	}
	
	private class VirtualFileSeekableByteChannelRead extends VirtualFileSeekableByteChannelAbstract {
		
		public VirtualFileSeekableByteChannelRead(byte[] array) {
			super(array);
		}
		
		public VirtualFileSeekableByteChannelRead(byte[] array, int position) {
			super(array, position);
		}
		
		@Override
		public int read(ByteBuffer dst) throws IOException {
			checkClosed();
			
			int remaining = array.length - position;
			int readCnt = min(remaining, dst.remaining());
			if (readCnt <= 0)
				return 0;
			
			dst.put(array, position, readCnt);
			position += readCnt;
			return readCnt;
		}
	}
	
	private class VirtualFileSeekableByteChannelWrite extends VirtualFileSeekableByteChannelAbstract {
		
		boolean sync;
		
		public VirtualFileSeekableByteChannelWrite(byte[] array, boolean sync) {
			super(array);
			this.sync = sync;
		}
		
		public VirtualFileSeekableByteChannelWrite(byte[] array, int position, boolean sync) {
			super(array, position);
			this.sync = sync;
		}
		
		@Override
		public int write(ByteBuffer src) throws IOException {
			checkClosed();
			
			int available = src.remaining();
			if (array == null) {
				array = new byte[available];
				src.get(array, 0, available);
			} else {
				byte[] old = array;
				array = new byte[old.length + available];
				System.arraycopy(old, 0, array, 0, old.length);
				src.get(array, old.length, available);
			}
			
			if (sync)
				VirtualFile.this.array = array;
			return available;
		}
		
		@Override
		public SeekableByteChannel truncate(long size2) {
			int size = (int) size2;
			if (size < array.length) {
				byte[] old = array;
				array = new byte[size];
				System.arraycopy(old, 0, array, 0, size);
			}
			
			if (sync)
				VirtualFile.this.array = array;
			return this;
		}
		
		@Override
		public void close() throws IOException {
			VirtualFile.this.array = array;
			super.close();
		}
	}
	
	//copyFrom
	@Override
	public void copyFrom(File from) throws IOException {
		try {
			rwLock.writeLock().lock();
			if (from instanceof VirtualFile)
				array = ((VirtualFile) from).array;
			else
				from.getByteChannel(READ).read(ByteBuffer.wrap(array));
		} finally {
			rwLock.writeLock().unlock();
		}
	}
}
