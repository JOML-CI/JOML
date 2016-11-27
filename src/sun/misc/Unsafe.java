package sun.misc;

import java.lang.reflect.Field;

/**
 * API of the sun.misc.Unsafe class.
 * <p>
 * This class is only around to compile against. It will not be picked up at
 * runtime, because the sun/ package is being loaded by the bootstrap
 * classloader.
 */
public final class Unsafe {
	public long objectFieldOffset(Field field) {
		throw new AssertionError();
	}

	public int arrayBaseOffset(Class clazz) {
		throw new AssertionError();
	}

	public native int getInt(Object obj, long offset);

	public native void putInt(Object obj, long offset, int newValue);

	public native void putOrderedInt(Object obj, long offset, int newValue);

	public native long getLong(Object obj, long offset);

	public native void putLong(Object obj, long offset, long newValue);

	public native void putOrderedLong(Object obj, long offset, long newValue);
}
