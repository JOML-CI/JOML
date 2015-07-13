#ifndef _CODEGEN_H_
#define _CODEGEN_H_

typedef int (__cdecl * sequence_func_t)(float* elements);
sequence_func_t codegen(void);

#endif
