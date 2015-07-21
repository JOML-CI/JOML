:: Make sure to have
:: - MinGW-w64's gcc.exe in PATH
:: - upx.exe in PATH
:: - JAVA_HOME pointing to a JDK

minilua.exe dynasm\dynasm.lua -o codegen_linear.win64.c -D X64 codegen_linear_win64.dasc
gcc -O3 -shared -o ..\joml.dll -I"%JAVA_HOME%\include" -I"%JAVA_HOME%\include\win32" codegen_linear.win64.c Native.c
strip -x -s ..\joml.dll
upx ..\joml.dll
