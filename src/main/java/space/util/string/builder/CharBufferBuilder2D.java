package space.util.string.builder;

import org.jetbrains.annotations.NotNull;
import space.util.ArrayUtils;
import space.util.annotation.Self;
import space.util.string.String2D;

import java.util.Arrays;

public class CharBufferBuilder2D<@Self SELF extends CharBufferBuilder2D<SELF>> implements IStringBuilder2D<SELF> {
	
	public static int defaultCapacity = 16;
	public static int defaultHeight = 1;
	public static int expandShiftHeight = 1;
	public static int expandShift = 1;
	
	public static char[][] BEINGEDITED = new char[][] {"Being Edited!".toCharArray()};
	
	public char[][] buffer;
	public int[] length;
	public int height;
	public int maxX;
	public char fillMissingSpaces = ' ';
	
	public int posX;
	public int posY;
	
	public boolean isBuild = false;
	/**
	 * beingEdited is a non-secure lock JUST for easier debugging, so the debugger calling toString() cannot modify the object (see the trimAll() Method inside toString())
	 */
	public int beingEdited = 0;
	
	public CharBufferBuilder2D() {
		this(defaultHeight, defaultCapacity);
	}
	
	public CharBufferBuilder2D(int sizeHeight, int sizeCapacity) {
		this(new char[sizeHeight][sizeCapacity], false);
	}
	
	public CharBufferBuilder2D(char[][] buffer) {
		this(buffer, true);
	}
	
	public CharBufferBuilder2D(char[][] buffer, boolean isFilled) {
		this(buffer, isFilled ? calcLengths(buffer) : new int[buffer.length], isFilled ? buffer.length : 0);
	}
	
	public CharBufferBuilder2D(char[][] buffer, int[] length, int height) {
		if (buffer.length != length.length)
			throw new IllegalArgumentException();
		
		this.buffer = buffer;
		this.length = length;
		this.height = height;
	}
	
	public static int[] calcLengths(char[][] buffer) {
		int l = buffer.length;
		int[] ret = new int[l];
		for (int i = 0; i < l; i++)
			ret[i] = buffer[i].length;
		return ret;
	}
	
	//fillMissingSpaces
	public CharBufferBuilder2D setFillMissingSpaces(char fillMissingSpaces) {
		this.fillMissingSpaces = fillMissingSpaces;
		return this;
	}
	
	public CharBufferBuilder2D setNoFillMissingSpaces() {
		this.fillMissingSpaces = 0;
		return this;
	}
	
	//edit start end
	public void startEdit() {
		beingEdited++;
	}
	
	public void endEdit() {
		isBuild = false;
		beingEdited--;
	}
	
	//ensure height
	@Override
	public boolean ensureHeight(int capa) {
		int l = buffer.length;
		if (l < capa) {
			startEdit();
			int newsize = ArrayUtils.getOptimalArraySizeExpansion(l, capa, expandShiftHeight);
			
			char[][] old = buffer;
			//noinspection unchecked
			buffer = new char[newsize][];
			System.arraycopy(old, 0, buffer, 0, l);
			
			int[] oldl = length;
			length = new int[newsize];
			System.arraycopy(oldl, 0, length, 0, l);
			
			endEdit();
			return true;
		}
		return false;
	}
	
	//ensureCapacity
	@Override
	public boolean ensureCapacity(int capa) {
		return ensureCapacity(posY, capa);
	}
	
	public boolean ensureCapacityIndex(int height, int index) {
		return ensureCapacity(height, index + 1);
	}
	
	public boolean ensureCapacity(int height, int capa) {
		char[] buf = buffer[height];
		
		if (buf == null) {
			startEdit();
			//noinspection unchecked
			buffer[height] = new char[ArrayUtils.getOptimalArraySizeStart(defaultCapacity, capa)];
			endEdit();
			return true;
		}
		
		int l = buf.length;
		if (l < capa) {
			startEdit();
			//noinspection unchecked
			char[] n = new char[ArrayUtils.getOptimalArraySizeExpansion(l, capa, expandShift)];
			System.arraycopy(buf, 0, n, 0, l);
			buffer[height] = n;
			endEdit();
			return true;
		}
		
		return false;
	}
	
	//size
	@Override
	public int height() {
		return height;
	}
	
	@Override
	public int length(int h) {
		return length[h];
	}
	
	//append
	@NotNull
	@Override
	public SELF append(char v) {
		int start = posX;
		setX(posX + 1);
		notifyYX();
		buffer[posY][start] = v;
		//noinspection unchecked
		return (SELF) this;
	}
	
	@NotNull
	@Override
	public SELF append(@NotNull String str) {
		startEdit();
		int l = str.length();
		int start = posX;
		setX(posX + l);
		notifyYX();
		str.getChars(0, l, buffer[posY], start);
		endEdit();
		//noinspection unchecked
		return (SELF) this;
	}
	
	@NotNull
	@Override
	public SELF append(@NotNull char[] str) {
		startEdit();
		int l = str.length;
		int start = posX;
		setX(posX + l);
		notifyYX();
		System.arraycopy(str, 0, buffer[posY], start, l);
		endEdit();
		//noinspection unchecked
		return (SELF) this;
	}
	
	@NotNull
	@Override
	public SELF append(@NotNull char[][] chars) {
		startEdit();
		int absStartX = posX;
		int absCurrY = posY;
		
		setY(posY + height - 1);
		notifyY();
		
		for (int i = 0; i < chars.length; ) {
			char[] from = chars[i];
			int froml = from.length;
			
			setY(absCurrY);
			setX(absStartX + froml);
			notifyYX();
			char[] to = buffer[absCurrY];
			
			System.arraycopy(from, 0, to, absStartX, froml);
			
			i++;
			absCurrY++;
		}
		
		endEdit();
		//noinspection unchecked
		return (SELF) this;
	}
	
	@NotNull
	@Override
	public SELF append(@NotNull char[][] chars, int untily, int untilx, char fillup) {
		startEdit();
		int absCurrY = posY;
		int absStartX = posX;
		
		setY(untily - 1);
		notifyY();
		
		for (int i = 0; i < chars.length; ) {
			char[] from = chars[i];
			int froml = Math.min(from.length, untilx - absStartX);
			
			setY(absCurrY);
			setX(untilx);
			notifyYX();
			char[] to = buffer[absCurrY];
			
			System.arraycopy(from, 0, to, absStartX, froml);
			if (froml < untilx)
				Arrays.fill(to, absStartX + froml, untilx, fillup);
			
			i++;
			absCurrY++;
		}
		
		if (absCurrY < untily) {
			for (int i = absCurrY; i < untily; i++) {
				setY(i);
				setX(untilx);
				notifyYX();
				
				Arrays.fill(buffer[i], absStartX, untilx, fillup);
			}
		}
		
		endEdit();
		//noinspection unchecked
		return (SELF) this;
	}
	
	//pos
	@Override
	public int getX() {
		return posX;
	}
	
	@NotNull
	@Override
	public SELF setX(int pos) {
		posX = pos;
		//noinspection unchecked
		return (SELF) this;
	}
	
	public void notifyX() {
		startEdit();
		ensureCapacity(posX);
		if (posX > length[posY]) {
			if (fillMissingSpaces != 0)
				Arrays.fill(buffer[posY], length[posY], posX, fillMissingSpaces);
			length[posY] = posX;
		}
		if (posX > maxX)
			maxX = posX;
		endEdit();
	}
	
	@Override
	public int getY() {
		return posY;
	}
	
	@NotNull
	@Override
	public SELF setY(int pos) {
		posY = pos;
		//noinspection unchecked
		return (SELF) this;
	}
	
	public void notifyY() {
		startEdit();
		ensureHeightIndex(posY);
		if (posY + 1 > height)
			height = posY + 1;
		endEdit();
	}
	
	public void notifyYX() {
		startEdit();
		notifyY();
		notifyX();
		endEdit();
	}
	
	//pos max
	@Override
	public int maxLength() {
		return maxX;
	}
	
	//fill
	@NotNull
	@Override
	public SELF fill(int l, char c) {
		startEdit();
		int start = posX;
		setX(posX + l - 1);
		notifyYX();
		Arrays.fill(buffer[posY], start, posX, c);
		endEdit();
		//noinspection unchecked
		return (SELF) this;
	}
	
	//nextLine
	@NotNull
	@Override
	public SELF nextLine() {
		startEdit();
		setY(posY + 1);
		setX(0);
		endEdit();
		//noinspection unchecked
		return (SELF) this;
	}
	
	@NotNull
	@Override
	public char[][] getChars() {
		if (beingEdited != 0)
			return BEINGEDITED;
		if (!isBuild) {
			trimAll();
			isBuild = true;
		}
		return buffer;
	}
	
	public void trimAll() {
		trim();
		for (int i = 0; i < height; i++)
			if (!ensureCapacity(i, 0))
				trim(i);
	}
	
	public void trim() {
		if (buffer.length == height)
			return;
		
		char[][] old = buffer;
		buffer = new char[height][];
		System.arraycopy(old, 0, buffer, 0, height);
		
		int[] oldl = length;
		length = new int[height];
		System.arraycopy(oldl, 0, length, 0, height);
	}
	
	public void trim(int h) {
		char[] old = buffer[h];
		int l = length[h];
		
		if (old.length == l)
			return;
		
		//noinspection unchecked
		char[] n = new char[l];
		System.arraycopy(old, 0, n, 0, l);
		buffer[h] = n;
	}
	
	@NotNull
	public String2D toString2D() {
		return new String2D(getChars(), maxX);
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
