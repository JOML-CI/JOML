#ifndef _CODEGEN_H_
#define _CODEGEN_H_

typedef void (*batch_func_t)(const void* arguments);
batch_func_t codegen(const unsigned char* opcodes, int opcodesLength, size_t* codeSize);
int free_code(void* functionPtr, size_t codeSize);

#endif
