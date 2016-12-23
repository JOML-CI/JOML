#!/bin/sh
echo "Building for x86-64..."
mkdir -p build
gcc -c -fPIC -m64 -O3 -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/linux" src/JNI.c -o build/JNI.o
gcc -shared -nostdlib -nodefaultlibs -O3 -o build/libjoml.so -fPIC -m64 build/JNI.o
rm build/*.o
strip -x -s build/libjoml.so
