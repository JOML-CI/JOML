import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;

public class Java6to4 {

    private static byte[] transform(InputStream classfile, ByteArrayOutputStream buffer) throws IOException {
        buffer.reset();
        int nRead;
        byte[] data = new byte[1024];
        while ((nRead = classfile.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        byte[] arr = buffer.toByteArray();
        // patch byte 8
        arr[7] = 48; // <- Java 1.4
        return arr;
    }

    private static void walk(File root, ByteArrayOutputStream buffer) throws IOException {
        File[] list = root.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                File f = new File(dir, name);
                return f.isDirectory() || name.endsWith(".class");
            }
        });
        if (list == null)
            return;
        for (int i = 0; i < list.length; i++) {
            File f = list[i];
            if (f.isDirectory()) {
                walk(f, buffer);
            } else {
                FileInputStream fis = new FileInputStream(f);
                byte[] transformed = transform(fis, buffer);
                fis.close();
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(transformed);
                fos.close();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        walk(new File(args[0]), buffer);
    }

}
