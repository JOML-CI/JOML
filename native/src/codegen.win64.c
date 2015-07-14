/*
** This file has been pre-processed with DynASM.
** http://luajit.org/dynasm.html
** DynASM version 1.3.0, DynASM x64 version 1.3.0
** DO NOT EDIT! The original file is in "codegen.dasc".
*/

#line 1 "codegen.dasc"
#include <stdio.h>
#include <stdlib.h>
#include <Windows.h>

#include "dynasm/dasm_proto.h"
#include "dynasm/dasm_x86.h"
#include "codegen.h"

//|.arch x64
#if DASM_VERSION != 10300
#error "Version mismatch between DynASM and included encoding engine"
#endif
#line 10 "codegen.dasc"
//|.section code
#define DASM_SECTION_CODE	0
#define DASM_MAXSECTION		1
#line 11 "codegen.dasc"
//|.globals GLOB_
enum {
  GLOB__MAX
};
#line 12 "codegen.dasc"
//|.actionlist actionlist
static const unsigned char actionlist[75] = {
  15,40,2,15,40,200,15,198,201,235,15,40,208,15,198,210,235,15,40,216,15,198,
  219,235,15,40,224,15,198,228,235,255,15,40,1,15,89,193,15,40,137,233,15,89,
  202,15,40,145,233,15,89,211,15,40,153,233,15,89,220,255,15,88,193,15,88,211,
  15,88,194,255,15,41,2,195,255
};

#line 13 "codegen.dasc"

#ifndef _MM_SHUFFLE
#define _MM_SHUFFLE(x,y,z,w) ((z << 6) | (y <<4) | (x << 2) | (w))
#endif

/**
 * Fast matrix-vector multiplication.
 * 
 * Adapted from GCC inline assembly from:
 * Reference: http://stackoverflow.com/questions/14967969/efficient-4x4-matrix-vector-multiplication-with-sse-horizontal-add-and-dot-prod
 */
static void gen_mul_matrix_vector(dasm_State** Dst) {
  /* matrix float[16] is given in rcx, vector float[4] is given in rdx */
  // create xmm1-xmm4 containing the vectors to be multiplied
  //| movaps xmm0, [rdx]
  //| movaps xmm1, xmm0
  //| shufps xmm1, xmm1, _MM_SHUFFLE(0, 0, 0, 0)
  //| movaps xmm2, xmm0
  //| shufps xmm2, xmm2, _MM_SHUFFLE(1, 1, 1, 1)
  //| movaps xmm3, xmm0
  //| shufps xmm3, xmm3, _MM_SHUFFLE(2, 2, 2, 2)
  //| movaps xmm4, xmm0
  //| shufps xmm4, xmm4, _MM_SHUFFLE(3, 3, 3, 3)
  dasm_put(Dst, 0, _MM_SHUFFLE(0, 0, 0, 0), _MM_SHUFFLE(1, 1, 1, 1), _MM_SHUFFLE(2, 2, 2, 2), _MM_SHUFFLE(3, 3, 3, 3));
#line 36 "codegen.dasc"
  // load first matrix column and multiply with xmm1
  //| movaps xmm0, [rcx]
  //| mulps xmm0, xmm1
  //| movaps xmm1, [rcx+4*4]
  //| mulps xmm1, xmm2
  //| movaps xmm2, [rcx+4*8]
  //| mulps xmm2, xmm3
  //| movaps xmm3, [rcx+4*12]
  //| mulps xmm3, xmm4
  dasm_put(Dst, 32, 4*4, 4*8, 4*12);
#line 45 "codegen.dasc"
  // now the results are in xmm0-xmm3 and need to be added
  //| addps xmm0, xmm1
  //| addps xmm2, xmm3
  //| addps xmm0, xmm2
  dasm_put(Dst, 60);
#line 49 "codegen.dasc"
  // the result vector is in xmm0
  //| movaps [rdx], xmm0
  //| ret
  dasm_put(Dst, 70);
#line 52 "codegen.dasc"
}

mul_matrix_vector_func_t codegen(void) {
  dasm_State* state;
  dasm_State** Dst = &state;
  int status;
  void* code;
  size_t code_size;
  DWORD dwOld;
  void* global_labels[GLOB__MAX];
  dasm_init(&state, DASM_MAXSECTION);
  dasm_setupglobal(&state, global_labels, GLOB__MAX);
  dasm_setup(&state, actionlist);
  gen_mul_matrix_vector(&state);
  status = dasm_link(&state, &code_size);
  code = VirtualAlloc(0, code_size, MEM_RESERVE | MEM_COMMIT, PAGE_READWRITE);
  status = dasm_encode(&state, code);
  VirtualProtect(code, code_size, PAGE_EXECUTE, &dwOld);
  FlushInstructionCache(GetCurrentProcess(), code, code_size);
  dasm_free(&state);
  return (mul_matrix_vector_func_t) code;
}
