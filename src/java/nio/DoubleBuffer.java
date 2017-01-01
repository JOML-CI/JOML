package java.nio;

/**
 * Only exists to be able to use the get() methods on JOML objects with Java primitive arrays on Java &lt; 1.4.
 * <p>
 * If we did not include the NIO API classes ourselves, method overload resolution would fail when compiling code that uses the get() methods.
 */
public abstract class DoubleBuffer extends Buffer {

    public abstract DoubleBuffer put(double value);
    public abstract DoubleBuffer put(int pos, double value);

    public abstract double get();
    public abstract double get(int pos);

    public abstract boolean isDirect();

}
