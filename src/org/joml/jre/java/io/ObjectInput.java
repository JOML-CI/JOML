package java.io;

public interface ObjectInput extends DataOutput {

    double readDouble() throws IOException;
    float readFloat() throws IOException;
    int readInt() throws IOException;

}
