echo "Building for x86..."
cl.exe /c /I"%JAVA_HOME%\include" /I"%JAVA_HOME%\include\win32" /Zi /W3 /WX- /O2 /Oi /Oy- /GL /D NDEBUG /D _WINDOWS /D _USRDLL /D JOML_EXPORTS /D _WINDLL /D _UNICODE /D UNICODE /Gm- /EHsc /MT /GS- /Gy /fp:precise /Zc:wchar_t /Zc:forScope /Zc:inline /Fo"build\\" /Fd"build\\vc140.pdb" /Gd /TC /errorReport:prompt src\JNI.c
link.exe /ERRORREPORT:PROMPT /OUT:"build\joml32.dll" /NODEFAULTLIB /NOENTRY /INCREMENTAL:NO /MANIFEST:NO /SUBSYSTEM:WINDOWS /OPT:REF /OPT:ICF /TLBID:1 /DYNAMICBASE /NXCOMPAT /IMPLIB:"build\joml32.lib" /MACHINE:X86 /SAFESEH /DLL /DEF:joml32.def build\JNI.obj
del build\*.obj build\*.pdb build\*.exp build\*.lib
