#!/bin/sh
echo "Building for x86-64..."
mkdir -p build
gcc -c -msse2 -fPIC -m64 -O3 -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/linux" src/JNI.c -lm -o build/JNI.o
gcc -c -msse2 -fPIC -m64 -O3 -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/linux" src/org_joml_JNI.c -lm -o build/org_joml_JNI.o
gcc -c -msse2 -fPIC -m64 -O3 -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/linux" src/org_joml_Matrix4f.c -lm -o build/org_joml_Matrix4f.o
gcc -c -mavx -fPIC -m64 -O3 -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/linux" src/org_joml_Matrix4f_avx.c -lm -o build/org_joml_Matrix4f_avx.o
gcc -shared -static-libgcc -O3 -o build/libjoml.so -fPIC -m64 build/JNI.o build/org_joml_JNI.o build/org_joml_Matrix4f.o build/org_joml_Matrix4f_avx.o -lm
rm build/*.o
strip -x -s build/libjoml.so
