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
static const unsigned char actionlist[191] = {
  76,139,1,65,15,40,0,15,40,208,65,15,40,136,233,15,198,193,136,15,198,209,
  221,65,15,40,160,233,15,40,252,244,65,15,40,168,233,15,198,229,136,15,198,
  252,245,221,15,40,200,15,198,196,136,15,40,218,15,198,214,136,15,198,204,
  221,15,198,222,221,255,65,15,41,0,65,15,41,144,233,65,15,41,136,233,65,15,
  41,152,233,255,72,129,193,239,255,76,139,1,65,15,40,0,255,15,40,200,15,198,
  201,235,15,40,208,15,198,210,235,15,40,216,15,198,219,235,15,40,224,15,198,
  228,235,255,72,129,193,239,76,139,9,255,65,15,40,1,15,89,193,255,65,15,40,
  137,233,15,89,202,255,65,15,40,145,233,15,89,211,255,65,15,40,153,233,15,
  89,220,255,15,88,193,15,88,211,15,88,194,255,65,15,41,0,255,195,255
};

#line 13 "codegen.dasc"

#ifndef _MM_SHUFFLE
#define _MM_SHUFFLE(x,y,z,w) ((z << 6) | (y <<4) | (x << 2) | (w))
#endif

static void preamble(dasm_State** Dst) {
}

static void epilogue(dasm_State** Dst) {
}

/**
 * Transpose a matrix using raw SSE shufps instructions.
 * This is real hardcore and took me about two hours to get it right!
 */
static void matrix_transpose(dasm_State** Dst) {
  // obtain matrix from [rcx]
  //| mov r8, [rcx]
  //| movaps xmm0, [r8]
  //| movaps xmm2, xmm0
  //| movaps xmm1, [r8+4*4]
  //| shufps xmm0, xmm1, 0x88
  //| shufps xmm2, xmm1, 0xdd
  //| movaps xmm4, [r8+8*4]
  //| movaps xmm6, xmm4
  //| movaps xmm5, [r8+12*4]
  //| shufps xmm4, xmm5, 0x88
  //| shufps xmm6, xmm5, 0xdd
  //| movaps xmm1, xmm0
  //| shufps xmm0, xmm4, 0x88
  //| movaps xmm3, xmm2
  //| shufps xmm2, xmm6, 0x88
  //| shufps xmm1, xmm4, 0xdd
  //| shufps xmm3, xmm6, 0xdd
  dasm_put(Dst, 0, 4*4, 8*4, 12*4);
#line 47 "codegen.dasc"
  // store back into matrix at [r8]
  //| movaps [r8], xmm0
  //| movaps [r8+4*4], xmm2
  //| movaps [r8+8*4], xmm1
  //| movaps [r8+12*4], xmm3
  dasm_put(Dst, 69, 4*4, 8*4, 12*4);
#line 52 "codegen.dasc"
  // increase args pointer
  //| add rcx, sizeof(char*)
  dasm_put(Dst, 89, sizeof(char*));
#line 54 "codegen.dasc"
}

/**
 * Fast matrix-vector multiplication.
 * 
 * Adapted from GCC intrinsics of:
 * http://stackoverflow.com/questions/14967969/efficient-4x4-matrix-vector-multiplication-with-sse-horizontal-add-and-dot-prod
 */
static void mul_matrix_vector(dasm_State** Dst) {
  // obtain vector
  //| mov r8, [rcx]
  //| movaps xmm0, [r8]
  dasm_put(Dst, 94);
#line 66 "codegen.dasc"

  // create xmm1-xmm4 containing the vectors to be multiplied
  //| movaps xmm1, xmm0
  //| shufps xmm1, xmm1, _MM_SHUFFLE(0, 0, 0, 0)
  //| movaps xmm2, xmm0
  //| shufps xmm2, xmm2, _MM_SHUFFLE(1, 1, 1, 1)
  //| movaps xmm3, xmm0
  //| shufps xmm3, xmm3, _MM_SHUFFLE(2, 2, 2, 2)
  //| movaps xmm4, xmm0
  //| shufps xmm4, xmm4, _MM_SHUFFLE(3, 3, 3, 3)
  dasm_put(Dst, 102, _MM_SHUFFLE(0, 0, 0, 0), _MM_SHUFFLE(1, 1, 1, 1), _MM_SHUFFLE(2, 2, 2, 2), _MM_SHUFFLE(3, 3, 3, 3));
#line 76 "codegen.dasc"

  // move to matrix
  //| add rcx, sizeof(char*)
  //| mov r9, [rcx]
  dasm_put(Dst, 131, sizeof(char*));
#line 80 "codegen.dasc"

  // load first matrix column and multiply with xmm1
  //| movaps xmm0, [r9]
  //| mulps xmm0, xmm1
  dasm_put(Dst, 139);
#line 84 "codegen.dasc"
  // load second matrix column and multiply with xmm2
  //| movaps xmm1, [r9+4*4]
  //| mulps xmm1, xmm2
  dasm_put(Dst, 147, 4*4);
#line 87 "codegen.dasc"
  // load third matrix column and multiply with xmm3
  //| movaps xmm2, [r9+4*8]
  //| mulps xmm2, xmm3
  dasm_put(Dst, 156, 4*8);
#line 90 "codegen.dasc"
  // load fourth matrix column and multiply with xmm4
  //| movaps xmm3, [r9+4*12]
  //| mulps xmm3, xmm4
  dasm_put(Dst, 165, 4*12);
#line 93 "codegen.dasc"
  // now the results are in xmm0-xmm3 and need to be added
  //| addps xmm0, xmm1
  //| addps xmm2, xmm3
  //| addps xmm0, xmm2
  dasm_put(Dst, 174);
#line 97 "codegen.dasc"

  // write final result in xmm0 back into vector
  //| movaps [r8], xmm0
  dasm_put(Dst, 184);
#line 100 "codegen.dasc"
  // increase args pointer
  //| add rcx, sizeof(char*)
  dasm_put(Dst, 89, sizeof(char*));
#line 102 "codegen.dasc"
}

batch_func_t codegen(const char* opcodes, int opcodesLength) {
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
  preamble(&state);
  for (int i = 0; i < opcodesLength; i++) {
    switch (opcodes[i]) {
    case 0x02: // OPCODE_MATRIX_MUL_VECTOR
      mul_matrix_vector(&state);
      break;
    case 0x03: // OPCODE_MATRIX_TRANSPOSE
      matrix_transpose(&state);
      break;
    default:
      break;
    }
  }
  epilogue(&state);
  //| ret
  dasm_put(Dst, 189);
#line 130 "codegen.dasc"
  status = dasm_link(&state, &code_size);
  code = VirtualAlloc(0, code_size, MEM_RESERVE | MEM_COMMIT, PAGE_READWRITE);
  status = dasm_encode(&state, code);
  VirtualProtect(code, code_size, PAGE_EXECUTE, &dwOld);
  FlushInstructionCache(GetCurrentProcess(), code, code_size);
  dasm_free(&state);
  return (batch_func_t) code;
}
