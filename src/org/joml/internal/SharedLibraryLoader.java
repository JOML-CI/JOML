/*
 * (C) Copyright 2016-2018 JOML

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 THE SOFTWARE.

 */
package org.joml.internal;

//#ifndef __GWT__

import java.io.*;
import java.net.URL;
import java.util.zip.CRC32;

/**
 * This code was taken and adapted from the LWJGL 3 sources.
 * The original code can be seen here: <a href="https://github.com/LWJGL/lwjgl3/blob/master/modules/core/src/main/java/org/lwjgl/system/SharedLibraryLoader.java">SharedLibraryLoader.java</a>
 * 
 * @author The LWJGL authors
 */
final class SharedLibraryLoader {

    private static final int BUFFER_SIZE = 1024;

    private static final String JOML_LIBRARY_NAME = System.getProperty("os.arch").lastIndexOf("64") != -1 ? "joml" : "joml32";

    private static File extractPath;

    private SharedLibraryLoader() {
    }

    static void load() throws IOException {
        try {
            extractPath = extractFile(Platform.PLATFORM.mapLibraryName(JOML_LIBRARY_NAME), null).getParentFile();
        } catch (Exception e) {
            throw new RuntimeException("Unable to extract the JOML shared library", e);
        }
        load(Platform.PLATFORM.mapLibraryName(JOML_LIBRARY_NAME));
    }

    private static void load(String library) throws IOException {
        File extracted = extractFile(Platform.PLATFORM.mapLibraryName(library), extractPath);
        System.load(extracted.getAbsolutePath());
    }

    private static File extractFile(String libraryFile, File libraryPath) throws IOException {
        URL resource = SharedLibraryLoader.class.getResource("/" + libraryFile);
        if (resource == null)
            throw new RuntimeException("Failed to locate resource: " + libraryFile);
        String libraryCRC;
        InputStream input = resource.openStream();
        try {
            libraryCRC = crc(input);
        } finally {
            input.close();
        }
        File extractedFile = getExtractedFile(libraryPath == null ? new File(libraryCRC) : libraryPath, new File(libraryFile).getName());
        extractFile(resource, libraryCRC, extractedFile);
        return extractedFile;
    }

    private static File getExtractedFile(File libraryPath, String fileName) {
        if (libraryPath.isDirectory())
            return new File(libraryPath, fileName);
        String tempDirectory = "joml" + System.getProperty("user.name");
        File file = new File(System.getProperty("java.io.tmpdir") + "/" + tempDirectory + "/" + libraryPath, fileName);
        return file;
    }

    private static void extractFile(URL resource, String libraryCRC, File extractedFile) throws IOException {
        String extractedCrc = null;
        if (extractedFile.exists()) {
            InputStream input = new FileInputStream(extractedFile);
            try {
                extractedCrc = crc(input);
            } finally {
                input.close();
            }
        }
        if (extractedCrc == null || !extractedCrc.equals(libraryCRC)) {
            extractedFile.getParentFile().mkdirs();
            InputStream input = resource.openStream();
            FileOutputStream output = new FileOutputStream(extractedFile);
            try {
                byte[] buffer = new byte[BUFFER_SIZE];
                int n;
                while ((n = input.read(buffer)) > 0)
                    output.write(buffer, 0, n);
            } finally {
                input.close();
                output.close();
            }
        }
    }

    private static String crc(InputStream input) throws IOException {
        CRC32 crc = new CRC32();
        byte[] buffer = new byte[BUFFER_SIZE];
        int n;
        while ((n = input.read(buffer)) > 0)
            crc.update(buffer, 0, n);
        return Long.toHexString(crc.getValue());
    }

}

//#endif
