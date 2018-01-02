package space.util.vfs;

import space.util.baseobject.ToString;
import space.util.string.toStringHelper.ToStringHelper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.OpenOption;
import java.util.Arrays;

public interface File extends Entry, ToString {
	
	/**
	 * gets the size of this file
	 *
	 * @return the size of this file
	 */
	long getSizeInBytes();
	
	//IOStreams
	InputStream getInputStream() throws IOException;
	
	OutputStream getOutputStream() throws IOException;
	
	SeekableByteChannel getByteChannel(OpenOption... options) throws IOException;
	
	static boolean containsOption(OpenOption[] options, OpenOption test) {
		return Arrays.binarySearch(options, test) != -1;
	}
	
	@Override
	default <T> T toTSH(ToStringHelper<T> api) {
		return api.toString(name());
	}
}
