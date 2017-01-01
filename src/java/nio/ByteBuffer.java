package java.nio;

/**
 * Only exists to be able to use the get() methods on JOML objects with Java primitive arrays on Java &lt; 1.4.
 * <p>
 * If we did not include the NIO API classes ourselves, method overload resolution would fail when compiling code that uses the get() methods.
 */
public abstract class ByteBuffer extends Buffer {

    public abstract ByteBuffer putInt(int value);
    public abstract ByteBuffer putInt(int pos, int value);
    public abstract ByteBuffer putFloat(float value);
    public abstract ByteBuffer putFloat(int pos, float value);
    public abstract ByteBuffer putLong(long value);
    public abstract ByteBuffer putLong(int pos, long value);
    public abstract ByteBuffer putDouble(double value);
    public abstract ByteBuffer putDouble(int pos, double value);

    public abstract int getInt();
    public abstract int getInt(int pos);
    public abstract float getFloat();
    public abstract float getFloat(int pos);
    public abstract double getDouble();
    public abstract double getDouble(int pos);

    public abstract boolean isDirect();

}
