package space.util.vfs.interfaces;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface File extends Entry {
	
	/**
	 * gets the size of this file
	 *
	 * @return the size of this file
	 */
	long getByteSize();
	
	//IOStreams
	InputStream getInputStream() throws IOException;
	
	OutputStream getOutputStream() throws IOException;
}
