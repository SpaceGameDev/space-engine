package space.util.vfs;

import space.util.FlagUtil;
import space.util.string.toStringHelper.ToStringHelper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.CopyOption;
import java.nio.file.OpenOption;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;

public interface File extends Entry {
	
	/**
	 * gets the size of this file
	 *
	 * @return the size of this file
	 */
	long getSizeInBytes();
	
	//IOStreams
	
	/**
	 * creates a new {@link InputStream} of this {@link File}
	 *
	 * @return a new {@link InputStream} of this {@link File}
	 * @throws IOException if an IOError occurs
	 */
	InputStream getInputStream() throws IOException;
	
	/**
	 * creates a new {@link OutputStream} of this {@link File}
	 *
	 * @return a new {@link OutputStream} of this {@link File}
	 * @throws IOException if an IOError occurs
	 */
	OutputStream getOutputStream() throws IOException;
	
	/**
	 * creates a new {@link SeekableByteChannel} of this {@link File}
	 *
	 * @param options {@link OpenOption OpenOptions} to open the {@link File} with
	 * @return a new {@link SeekableByteChannel} of this {@link File}
	 * @throws IOException if an IOError occurs
	 * @see java.nio.file.StandardOpenOption the standard OpenOptions
	 */
	SeekableByteChannel getByteChannel(OpenOption... options) throws IOException;
	
	/**
	 * copies the contents of the Argument from to this {@link File}
	 *
	 * @param from the file to copy from
	 * @throws IOException if an IOError occurs
	 */
	void copyFrom(File from) throws IOException;
	
	@Override
	default Entry copy(String newName, Folder newParent, CopyOption... options) throws IOException {
		Folder p = (newParent != null) ? newParent : getParent();
		String n = (newName != null) ? newName : name();
		
		File f = FlagUtil.hasFlag(options, StandardCopyOption.REPLACE_EXISTING) ? p.addFile(n, StandardOpenOption.CREATE) : p.addFile(n);
		f.copyFrom(this);
		return f;
	}
	
	@Override
	default <T> T toTSH(ToStringHelper<T> api) {
		return api.toString(name());
	}
}
