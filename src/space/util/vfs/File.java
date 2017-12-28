package space.util.vfs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.SeekableByteChannel;

public interface File extends Entry {
	
	/**
	 * gets the size of this file
	 *
	 * @return the size of this file
	 */
	long getSizeInBytes();
	
	//IOStreams
	InputStream getInputStream() throws IOException;
	
	OutputStream getOutputStream() throws IOException;
	
	SeekableByteChannel getByteChannel() throws IOException;
}
