#!/bin/sh
# Make sure to have
# - gcc in PATH
# - JAVA_HOME pointing to a JDK (such as "/usr/lib/jvm/java-8-openjdk-amd64")
# - upx in PATH

./minilua dynasm/dynasm.lua -o codegen_linear.linux64.c -D X64 codegen_linear_linux64.dasc
gcc -fPIC -D_GNU_SOURCE -std=c99 -O3 -shared -o libjoml.so -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/linux" codegen_linear.linux64.c Native.c
strip -x -s libjoml.so
upx libjoml.so
