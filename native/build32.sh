#!/bin/sh
echo "Building for x86..."
gcc -c -msse2 -fPIC -m32 -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/linux" src/linux/JNI.c -lm -o build/JNI.o
gcc -c -msse2 -fPIC -m32 -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/linux" src/linux/org_joml_JNI.c -lm -o build/org_joml_JNI.o
gcc -c -msse2 -fPIC -m32 -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/linux" src/linux/org_joml_Matrix3f.c -lm -o build/org_joml_Matrix3f.o
gcc -c -msse2 -fPIC -m32 -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/linux" src/linux/org_joml_Matrix4f.c -lm -o build/org_joml_Matrix4f.o
gcc -c -mavx -fPIC -m32 -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/linux" src/linux/org_joml_Matrix4f_avx.c -lm -o build/org_joml_Matrix4f_avx.o
gcc -c -mavx -fPIC -m32 -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/linux" src/linux/org_joml_Matrix4d.c -lm -o build/org_joml_Matrix4d.o
gcc -shared -static-libgcc -o build/libjoml32.so -fPIC -m32 build/JNI.o build/org_joml_JNI.o build/org_joml_Matrix3f.o build/org_joml_Matrix4f.o build/org_joml_Matrix4f_avx.o build/org_joml_Matrix4d.o -lm
rm build/*.o
