package org.joml;

class Options {

    static boolean hasOption(String option) {
        String v = System.getProperty(option);
        if (v == null)
            return false;
        if (v.trim().length() == 0)
            return true;
        return Boolean.valueOf(v).booleanValue();
    }

}
