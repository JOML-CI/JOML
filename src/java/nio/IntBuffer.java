package java.nio;

/**
 * Only exists to be able to use the get() methods on JOML objects with Java primitive arrays on Java &lt; 1.4.
 * <p>
 * If we did not include the NIO API classes ourselves, method overload resolution would fail when compiling code that uses the get() methods.
 */
public abstract class IntBuffer extends Buffer {

    public abstract IntBuffer put(int value);
    public abstract IntBuffer put(int pos, int value);

    public abstract int get();
    public abstract int get(int pos);

    public abstract boolean isDirect();

}
