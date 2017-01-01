package java.nio;

/**
 * Only exists to be able to use the get() methods on JOML objects with Java primitive arrays on Java &lt; 1.4.
 * <p>
 * If we did not include the NIO API classes ourselves, method overload resolution would fail when compiling code that uses the get() methods.
 */
public abstract class FloatBuffer extends Buffer {

    public abstract FloatBuffer put(float value);
    public abstract FloatBuffer put(int pos, float value);

    public abstract float get();
    public abstract float get(int pos);

    public abstract boolean isDirect();

}
