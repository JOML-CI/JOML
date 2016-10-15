/*
 * Copyright LWJGL. All rights reserved.
 * License terms: http://lwjgl.org/license.php
 */
package org.joml;

import java.util.regex.Pattern;

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
