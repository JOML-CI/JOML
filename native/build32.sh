#!/bin/sh
echo "Building for x86..."
mkdir -p build
gcc -c -fPIC -m32 -O3 -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/linux" src/JNI.c -o build/JNI.o
gcc -shared -nostdlib -nodefaultlibs -O3 -o build/libjoml32.so -fPIC -m32 build/JNI.o
rm build/*.o
strip -S --strip-unneeded --remove-section=.note.gnu.gold-version --remove-section=.comment --remove-section=.note --remove-section=.note.gnu.build-id --remove-section=.note.ABI-tag build/libjoml32.so
