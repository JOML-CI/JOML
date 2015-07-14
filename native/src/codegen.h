#ifndef _CODEGEN_H_
#define _CODEGEN_H_

typedef void (__cdecl * batch_func_t)(const void* arguments);
batch_func_t codegen(const char* opcodes, int opcodesLength);

#endif
