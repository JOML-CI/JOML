minilua.exe dynasm\dynasm.lua -o codegen.win64.c -D X64 codegen.dasc
gcc -shared -o joml.dll -I"C:\Program Files\Java\jdk1.8.0_45\include" -I"C:\Program Files\Java\jdk1.8.0_45\include\win32" codegen.win64.c NativeVector4f.c
