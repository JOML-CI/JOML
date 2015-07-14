#ifndef _CODEGEN_H_
#define _CODEGEN_H_

typedef void (__cdecl * mul_matrix_vector_func_t)(float* matrixElements, float* vectorElements);
typedef int (__cdecl * sequence_func_t)(float* elements);
mul_matrix_vector_func_t codegen(void);

#endif
