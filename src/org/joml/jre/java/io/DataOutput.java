package java.io;

public interface DataOutput {
    void writeInt(int value) throws IOException;
    void writeFloat(float value) throws IOException;
    void writeDouble(double value) throws IOException;
}
