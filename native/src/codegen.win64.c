/*
** This file has been pre-processed with DynASM.
** http://luajit.org/dynasm.html
** DynASM version 1.3.0, DynASM x64 version 1.3.0
** DO NOT EDIT! The original file is in "codegen.dasc".
*/

#line 1 "codegen.dasc"
#include <stdlib.h>
#include <Windows.h>

#include "dynasm/dasm_proto.h"
#include "dynasm/dasm_x86.h"
#include "codegen.h"

//|.arch x64
#if DASM_VERSION != 10300
#error "Version mismatch between DynASM and included encoding engine"
#endif
#line 9 "codegen.dasc"
//|.section code
#define DASM_SECTION_CODE	0
#define DASM_MAXSECTION		1
#line 10 "codegen.dasc"
//|.globals GLOB_
enum {
  GLOB_matrix_transpose,
  GLOB_mul_matrix_matrix,
  GLOB_mul_matrix_vector,
  GLOB__MAX
};
#line 11 "codegen.dasc"
//|.actionlist actionlist
static const unsigned char actionlist[336] = {
  248,10,255,76,139,1,72,129,193,239,255,65,15,40,0,15,40,208,65,15,40,136,
  233,15,198,193,136,15,198,209,221,65,15,40,152,233,15,40,252,235,65,15,40,
  176,233,15,198,222,136,15,198,252,238,221,15,40,200,15,198,195,136,15,40,
  226,15,198,213,136,15,198,203,221,15,198,230,221,255,65,15,41,0,65,15,41,
  144,233,65,15,41,136,233,65,15,41,160,233,255,252,255,226,255,15,40,200,15,
  198,201,235,15,40,208,15,198,210,235,15,40,216,15,198,219,235,15,40,224,15,
  198,228,235,65,15,40,0,15,89,193,65,15,40,136,233,15,89,202,65,15,40,144,
  233,15,89,211,65,15,40,152,233,15,89,220,15,88,193,15,88,211,15,88,194,255,
  248,11,255,76,139,9,72,129,193,239,255,65,15,40,129,233,255,15,40,232,255,
  15,40,252,240,255,15,40,252,248,255,65,15,41,40,65,15,41,176,233,65,15,41,
  184,233,65,15,41,128,233,255,248,12,255,76,139,1,72,129,193,239,65,15,40,
  0,255,15,40,200,15,198,201,235,15,40,208,15,198,210,235,15,40,216,15,198,
  219,235,15,40,224,15,198,228,235,255,65,15,40,1,15,89,193,255,65,15,40,137,
  233,15,89,202,255,65,15,40,145,233,15,89,211,255,65,15,40,153,233,15,89,220,
  255,65,15,41,0,255,72,199,194,247,252,233,244,11,255,249,255,72,199,194,247,
  252,233,244,12,255,72,199,194,247,252,233,244,10,255,195,255
};

#line 12 "codegen.dasc"

#ifndef _MM_SHUFFLE
#define _MM_SHUFFLE(x,y,z,w) ((z << 6) | (y <<4) | (x << 2) | (w))
#endif

/**
 * Transpose a matrix using raw SSE shufps instructions.
 */
static void matrix_transpose(dasm_State** Dst) {
  //|->matrix_transpose:
  dasm_put(Dst, 0);
#line 22 "codegen.dasc"
  // obtain matrix from [rcx]
  //| mov r8, [rcx]
  //| add rcx, sizeof(char*)
  dasm_put(Dst, 3, sizeof(char*));
#line 25 "codegen.dasc"
  // shuffle around a bit
  //| movaps xmm0, [r8]
  //| movaps xmm2, xmm0
  //| movaps xmm1, [r8+4*4]
  //| shufps xmm0, xmm1, 0x88
  //| shufps xmm2, xmm1, 0xdd
  //| movaps xmm3, [r8+8*4]
  //| movaps xmm5, xmm3
  //| movaps xmm6, [r8+12*4]
  //| shufps xmm3, xmm6, 0x88
  //| shufps xmm5, xmm6, 0xdd
  //| movaps xmm1, xmm0
  //| shufps xmm0, xmm3, 0x88
  //| movaps xmm4, xmm2
  //| shufps xmm2, xmm5, 0x88
  //| shufps xmm1, xmm3, 0xdd
  //| shufps xmm4, xmm6, 0xdd
  dasm_put(Dst, 11, 4*4, 8*4, 12*4);
#line 42 "codegen.dasc"
  // store back into matrix at [r8]
  //| movaps [r8], xmm0
  //| movaps [r8+4*4], xmm2
  //| movaps [r8+8*4], xmm1
  //| movaps [r8+12*4], xmm4
  dasm_put(Dst, 77, 4*4, 8*4, 12*4);
#line 47 "codegen.dasc"
  // jump to next operation
  //| jmp rdx
  dasm_put(Dst, 97);
#line 49 "codegen.dasc"
}

static void linearProduct(dasm_State** Dst) {
  // create xmm1-xmm4 containing the first column of the second matrix to be multiplied by the first matrix
  //| movaps xmm1, xmm0
  //| shufps xmm1, xmm1, _MM_SHUFFLE(0, 0, 0, 0)
  //| movaps xmm2, xmm0
  //| shufps xmm2, xmm2, _MM_SHUFFLE(1, 1, 1, 1)
  //| movaps xmm3, xmm0
  //| shufps xmm3, xmm3, _MM_SHUFFLE(2, 2, 2, 2)
  //| movaps xmm4, xmm0
  //| shufps xmm4, xmm4, _MM_SHUFFLE(3, 3, 3, 3)
  //| movaps xmm0, [r8]
  //| mulps xmm0, xmm1
  //| movaps xmm1, [r8+4*4]
  //| mulps xmm1, xmm2
  //| movaps xmm2, [r8+4*8]
  //| mulps xmm2, xmm3
  //| movaps xmm3, [r8+4*12]
  //| mulps xmm3, xmm4
  //| addps xmm0, xmm1
  //| addps xmm2, xmm3
  //| addps xmm0, xmm2
  dasm_put(Dst, 101, _MM_SHUFFLE(0, 0, 0, 0), _MM_SHUFFLE(1, 1, 1, 1), _MM_SHUFFLE(2, 2, 2, 2), _MM_SHUFFLE(3, 3, 3, 3), 4*4, 4*8, 4*12);
#line 72 "codegen.dasc"
  // result is in xmm0
}

static void mul_matrix_matrix(dasm_State** Dst) {
  //|->mul_matrix_matrix:
  dasm_put(Dst, 170);
#line 77 "codegen.dasc"
  // obtain first matrix
  //| mov r8, [rcx]
  //| add rcx, sizeof(char*)
  dasm_put(Dst, 3, sizeof(char*));
#line 80 "codegen.dasc"
  // obtain second matrix
  //| mov r9, [rcx]
  //| add rcx, sizeof(char*)
  dasm_put(Dst, 173, sizeof(char*));
#line 83 "codegen.dasc"
  for (int i = 0; i < 4; i++) {
    // get i-th column of second matrix
    //| movaps xmm0, [r9+4*4*i]
    dasm_put(Dst, 181, 4*4*i);
#line 86 "codegen.dasc"
    linearProduct(Dst);
    /* We must be careful to not overwrite the destination
       matrix with the column results, since that matrix
       is needed in subsequence linearProducts.
       Luckily, we have 8 XMM registers, and can 
       buffer the first three results into them and
       during the last iteration we store all of them
       including xmm0 of that computation into the destination
       matrix. */
    if (i == 0) {
      //| movaps xmm5, xmm0
      dasm_put(Dst, 187);
#line 97 "codegen.dasc"
    } else if (i == 1) {
      //| movaps xmm6, xmm0
      dasm_put(Dst, 191);
#line 99 "codegen.dasc"
    } else if (i == 2) {
      //| movaps xmm7, xmm0
      dasm_put(Dst, 196);
#line 101 "codegen.dasc"
    } else if (i == 3) {
      //| movaps [r8], xmm5
      //| movaps [r8+4*4], xmm6
      //| movaps [r8+8*4], xmm7
      //| movaps [r8+12*4], xmm0
      dasm_put(Dst, 201, 4*4, 8*4, 12*4);
#line 106 "codegen.dasc"
    }
  }
  // jump to next operation
  //| jmp rdx
  dasm_put(Dst, 97);
#line 110 "codegen.dasc"
}

/**
 * Fast matrix-vector multiplication.
 * 
 * Adapted from GCC intrinsics of:
 * http://stackoverflow.com/questions/14967969/efficient-4x4-matrix-vector-multiplication-with-sse-horizontal-add-and-dot-prod
 */
static void mul_matrix_vector(dasm_State** Dst) {
  //|->mul_matrix_vector:
  dasm_put(Dst, 221);
#line 120 "codegen.dasc"
  // obtain vector
  //| mov r8, [rcx]
  //| add rcx, sizeof(char*)
  //| movaps xmm0, [r8]
  dasm_put(Dst, 224, sizeof(char*));
#line 124 "codegen.dasc"

  // create xmm1-xmm4 containing the vectors to be multiplied
  //| movaps xmm1, xmm0
  //| shufps xmm1, xmm1, _MM_SHUFFLE(0, 0, 0, 0)
  //| movaps xmm2, xmm0
  //| shufps xmm2, xmm2, _MM_SHUFFLE(1, 1, 1, 1)
  //| movaps xmm3, xmm0
  //| shufps xmm3, xmm3, _MM_SHUFFLE(2, 2, 2, 2)
  //| movaps xmm4, xmm0
  //| shufps xmm4, xmm4, _MM_SHUFFLE(3, 3, 3, 3)
  dasm_put(Dst, 236, _MM_SHUFFLE(0, 0, 0, 0), _MM_SHUFFLE(1, 1, 1, 1), _MM_SHUFFLE(2, 2, 2, 2), _MM_SHUFFLE(3, 3, 3, 3));
#line 134 "codegen.dasc"

  // obtain matrix
  //| mov r9, [rcx]
  //| add rcx, sizeof(char*)
  dasm_put(Dst, 173, sizeof(char*));
#line 138 "codegen.dasc"

  // load first matrix column and multiply with xmm1
  //| movaps xmm0, [r9]
  //| mulps xmm0, xmm1
  dasm_put(Dst, 265);
#line 142 "codegen.dasc"
  // load second matrix column and multiply with xmm2
  //| movaps xmm1, [r9+4*4]
  //| mulps xmm1, xmm2
  dasm_put(Dst, 273, 4*4);
#line 145 "codegen.dasc"
  // load third matrix column and multiply with xmm3
  //| movaps xmm2, [r9+4*8]
  //| mulps xmm2, xmm3
  dasm_put(Dst, 282, 4*8);
#line 148 "codegen.dasc"
  // load fourth matrix column and multiply with xmm4
  //| movaps xmm3, [r9+4*12]
  //| mulps xmm3, xmm4
  dasm_put(Dst, 291, 4*12);
#line 151 "codegen.dasc"
  // now the results are in xmm0-xmm3 and need to be added
  //| addps xmm0, xmm1
  //| addps xmm2, xmm3
  //| addps xmm0, xmm2
  dasm_put(Dst, 160);
#line 155 "codegen.dasc"

  // write final result in xmm0 back into vector
  //| movaps [r8], xmm0
  dasm_put(Dst, 300);
#line 158 "codegen.dasc"
  // jump to next operation
  //| jmp rdx
  dasm_put(Dst, 97);
#line 160 "codegen.dasc"
}

batch_func_t codegen(const char* opcodes, int opcodesLength) {
  dasm_State* state;
  dasm_State** Dst = &state;
  int status;
  void* code;
  int num_pc_allocated = 8;
  int next_pc = 0;
  size_t code_size;
  DWORD dwOld;
  void* global_labels[GLOB__MAX];
  char op_generated[3] = {0, 0, 0};
  dasm_init(&state, DASM_MAXSECTION);
  dasm_setupglobal(&state, global_labels, GLOB__MAX);
  dasm_setup(&state, actionlist);
  dasm_growpc(&state, num_pc_allocated);
  for (int i = 0; i < opcodesLength; i++) {
    if (next_pc == num_pc_allocated) {
      num_pc_allocated *= 2;
      dasm_growpc(&state, num_pc_allocated);
    }
    switch (opcodes[i]) {
    case 0x01: // OPCODE_MATRIX_MUL_MATRIX
      // tell the matrix function where to jump to
      // in order to execute the next operation
      //| mov rdx, =>next_pc
      //| jmp ->mul_matrix_matrix
      dasm_put(Dst, 305, next_pc);
#line 188 "codegen.dasc"
      if (!op_generated[0]) {
        mul_matrix_matrix(&state);
        op_generated[0] = 1;
      }
      // define the label that we just jumped to above
      //|=>next_pc:
      dasm_put(Dst, 314, next_pc);
#line 194 "codegen.dasc"
      // use a new fresh label "index" for the next label
      next_pc++;
      break;
    case 0x02: // OPCODE_MATRIX_MUL_VECTOR
      //| mov rdx, =>next_pc
      //| jmp ->mul_matrix_vector
      dasm_put(Dst, 316, next_pc);
#line 200 "codegen.dasc"
      if (!op_generated[1]) {
        mul_matrix_vector(&state);
        op_generated[1] = 1;
      }
      //|=>next_pc:
      dasm_put(Dst, 314, next_pc);
#line 205 "codegen.dasc"
      next_pc++;
      break;
    case 0x03: // OPCODE_MATRIX_TRANSPOSE
      //| mov rdx, =>next_pc
      //| jmp ->matrix_transpose
      dasm_put(Dst, 325, next_pc);
#line 210 "codegen.dasc"
      if (!op_generated[2]) {
        matrix_transpose(&state);
        op_generated[2] = 1;
      }
      //|=>next_pc:
      dasm_put(Dst, 314, next_pc);
#line 215 "codegen.dasc"
      next_pc++;
      break;
    default:
      break;
    }
  }
  //| ret
  dasm_put(Dst, 334);
#line 222 "codegen.dasc"
  status = dasm_link(&state, &code_size);
  code = VirtualAlloc(0, code_size, MEM_RESERVE | MEM_COMMIT, PAGE_READWRITE);
  status = dasm_encode(&state, code);
  VirtualProtect(code, code_size, PAGE_EXECUTE, &dwOld);
  FlushInstructionCache(GetCurrentProcess(), code, code_size);
  dasm_free(&state);
  return (batch_func_t) code;
}
