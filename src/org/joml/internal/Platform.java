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

import java.util.regex.Pattern;

/**
 * This code was taken and adapted from the LWJGL 3 sources.
 * The original code can be seen here: <a href="https://github.com/LWJGL/lwjgl3/blob/master/modules/lwjgl/core/src/main/java/org/lwjgl/system/Platform.java">Platform.java</a>
 * 
 * @author The LWJGL authors
 */
abstract class Platform {

    private static final Platform LINUX = new Platform("Linux") {
        private final Pattern SO = Pattern.compile("lib\\w+[.]so(?:[.]\\d+){0,3}");

        String mapLibraryName(String name) {
            if (SO.matcher(name).matches())
                return name;

            return System.mapLibraryName(name);
        }
    };
    private static final Platform MACOSX = new Platform("Mac OS X") {
        private final Pattern DYLIB = Pattern.compile("lib\\w+[.]dylib");

        String mapLibraryName(String name) {
            if (DYLIB.matcher(name).matches())
                return name;

            return System.mapLibraryName(name);
        }
    };
    private static final Platform WINDOWS = new Platform("Windows") {
        String mapLibraryName(String name) {
            if (name.endsWith(".dll"))
                return name;

            return System.mapLibraryName(name);
        }
    };

    public static final Platform PLATFORM;

    static {
        String osName = System.getProperty("os.name");
        if (osName.startsWith("Windows"))
            PLATFORM = Platform.WINDOWS;
        else if (osName.startsWith("Linux") || osName.startsWith("FreeBSD") || osName.startsWith("SunOS") || osName.startsWith("Unix"))
            PLATFORM = Platform.LINUX;
        else if (osName.startsWith("Mac OS X") || osName.startsWith("Darwin"))
            PLATFORM = Platform.MACOSX;
        else
            throw new LinkageError("Unknown platform: " + osName);
    }

    private final String name;

    Platform(String name) {
        this.name = name;
    }

    /**
     * Returns the platform name.
     */
    public String getName() {
        return name;
    }

    abstract String mapLibraryName(String name);

}

//#endif
