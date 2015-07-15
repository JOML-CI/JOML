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
  GLOB_translation_rotate_scale,
  GLOB_rotateZ,
  GLOB_vector_negate,
  GLOB__MAX
};
#line 11 "codegen.dasc"
//|.actionlist actionlist
static const unsigned char actionlist[511] = {
  248,10,255,76,139,1,72,131,193,16,255,65,15,40,0,15,40,208,65,15,40,136,233,
  15,198,193,136,15,198,209,221,65,15,40,152,233,15,40,252,235,65,15,40,176,
  233,15,198,222,136,15,198,252,238,221,15,40,200,15,198,195,136,15,40,226,
  15,198,213,136,15,198,203,221,15,198,230,221,255,65,15,41,0,65,15,41,144,
  233,65,15,41,136,233,65,15,41,160,233,255,15,40,200,15,198,201,235,15,40,
  208,15,198,210,235,15,40,216,15,198,219,235,15,40,224,15,198,228,235,65,15,
  40,0,15,89,193,65,15,40,136,233,15,89,202,65,15,40,144,233,15,89,211,65,15,
  40,152,233,15,89,220,15,88,193,15,88,211,15,88,194,255,248,11,255,76,139,
  1,72,131,193,8,255,76,139,9,72,131,193,8,255,65,15,40,129,233,255,15,40,232,
  255,15,40,252,240,255,15,40,252,248,255,65,15,41,40,65,15,41,176,233,65,15,
  41,184,233,65,15,41,128,233,255,248,12,255,76,139,1,72,131,193,8,65,15,40,
  0,255,15,40,200,15,198,201,235,15,40,208,15,198,210,235,15,40,216,15,198,
  219,235,15,40,224,15,198,228,235,255,65,15,40,1,15,89,193,255,65,15,40,137,
  233,15,89,202,255,65,15,40,145,233,15,89,211,255,65,15,40,153,233,15,89,220,
  255,65,15,41,0,255,248,13,255,15,40,1,72,129,193,239,255,15,40,9,72,129,193,
  239,255,15,40,17,72,129,193,239,255,248,14,76,139,1,72,131,193,8,252,243,
  15,16,1,72,131,193,4,252,243,15,16,9,72,131,193,4,15,198,192,235,15,198,201,
  235,15,87,210,15,92,208,65,15,40,24,15,89,217,65,15,40,160,233,15,89,224,
  15,88,220,65,15,40,32,15,89,226,65,15,40,168,233,15,89,252,233,15,88,229,
  65,15,41,24,65,15,41,160,233,255,248,15,255,76,139,1,72,131,193,16,65,15,
  40,0,15,87,201,15,92,200,65,15,41,8,255,72,199,194,247,252,233,244,11,255,
  252,255,226,255,249,255,72,199,194,247,252,233,244,12,255,72,199,194,247,
  252,233,244,10,255,72,199,194,247,252,233,244,13,255,72,199,194,247,252,233,
  244,14,255,72,199,194,247,252,233,244,15,255,195,255
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
  //| add rcx, 16
  dasm_put(Dst, 3);
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
}

static void linearProduct(dasm_State** Dst) {
  // create xmm1-xmm4 containing the i'th column of the second matrix to be multiplied by the first matrix
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
  dasm_put(Dst, 97, _MM_SHUFFLE(0, 0, 0, 0), _MM_SHUFFLE(1, 1, 1, 1), _MM_SHUFFLE(2, 2, 2, 2), _MM_SHUFFLE(3, 3, 3, 3), 4*4, 4*8, 4*12);
#line 70 "codegen.dasc"
  // result is in xmm0
}

static void mul_matrix_matrix(dasm_State** Dst) {
  //|->mul_matrix_matrix:
  dasm_put(Dst, 166);
#line 75 "codegen.dasc"
  // obtain first matrix
  //| mov r8, [rcx]
  //| add rcx, 8
  dasm_put(Dst, 169);
#line 78 "codegen.dasc"
  // obtain second matrix
  //| mov r9, [rcx]
  //| add rcx, 8
  dasm_put(Dst, 177);
#line 81 "codegen.dasc"
  for (int i = 0; i < 4; i++) {
    // get i-th column of second matrix
    //| movaps xmm0, [r9+4*4*i]
    dasm_put(Dst, 185, 4*4*i);
#line 84 "codegen.dasc"
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
      dasm_put(Dst, 191);
#line 95 "codegen.dasc"
    } else if (i == 1) {
      //| movaps xmm6, xmm0
      dasm_put(Dst, 195);
#line 97 "codegen.dasc"
    } else if (i == 2) {
      //| movaps xmm7, xmm0
      dasm_put(Dst, 200);
#line 99 "codegen.dasc"
    } else if (i == 3) {
      //| movaps [r8], xmm5
      //| movaps [r8+4*4], xmm6
      //| movaps [r8+8*4], xmm7
      //| movaps [r8+12*4], xmm0
      dasm_put(Dst, 205, 4*4, 8*4, 12*4);
#line 104 "codegen.dasc"
    }
  }
}

/**
 * Fast matrix-vector multiplication.
 * 
 * Adapted from GCC intrinsics of:
 * http://stackoverflow.com/questions/14967969/efficient-4x4-matrix-vector-multiplication-with-sse-horizontal-add-and-dot-prod
 */
static void mul_matrix_vector(dasm_State** Dst) {
  //|->mul_matrix_vector:
  dasm_put(Dst, 225);
#line 116 "codegen.dasc"
  // obtain vector
  //| mov r8, [rcx]
  //| add rcx, 8
  //| movaps xmm0, [r8]
  dasm_put(Dst, 228);
#line 120 "codegen.dasc"

  // create xmm1-xmm4 containing the vectors to be multiplied
  //| movaps xmm1, xmm0
  //| shufps xmm1, xmm1, _MM_SHUFFLE(0, 0, 0, 0)
  //| movaps xmm2, xmm0
  //| shufps xmm2, xmm2, _MM_SHUFFLE(1, 1, 1, 1)
  //| movaps xmm3, xmm0
  //| shufps xmm3, xmm3, _MM_SHUFFLE(2, 2, 2, 2)
  //| movaps xmm4, xmm0
  //| shufps xmm4, xmm4, _MM_SHUFFLE(3, 3, 3, 3)
  dasm_put(Dst, 240, _MM_SHUFFLE(0, 0, 0, 0), _MM_SHUFFLE(1, 1, 1, 1), _MM_SHUFFLE(2, 2, 2, 2), _MM_SHUFFLE(3, 3, 3, 3));
#line 130 "codegen.dasc"

  // obtain matrix
  //| mov r9, [rcx]
  //| add rcx, 8
  dasm_put(Dst, 177);
#line 134 "codegen.dasc"

  // load first matrix column and multiply with xmm1
  //| movaps xmm0, [r9]
  //| mulps xmm0, xmm1
  dasm_put(Dst, 269);
#line 138 "codegen.dasc"
  // load second matrix column and multiply with xmm2
  //| movaps xmm1, [r9+4*4]
  //| mulps xmm1, xmm2
  dasm_put(Dst, 277, 4*4);
#line 141 "codegen.dasc"
  // load third matrix column and multiply with xmm3
  //| movaps xmm2, [r9+4*8]
  //| mulps xmm2, xmm3
  dasm_put(Dst, 286, 4*8);
#line 144 "codegen.dasc"
  // load fourth matrix column and multiply with xmm4
  //| movaps xmm3, [r9+4*12]
  //| mulps xmm3, xmm4
  dasm_put(Dst, 295, 4*12);
#line 147 "codegen.dasc"
  // now the results are in xmm0-xmm3 and need to be added
  //| addps xmm0, xmm1
  //| addps xmm2, xmm3
  //| addps xmm0, xmm2
  dasm_put(Dst, 156);
#line 151 "codegen.dasc"

  // write final result in xmm0 back into vector
  //| movaps [r8], xmm0
  dasm_put(Dst, 304);
#line 154 "codegen.dasc"
}

static void translation_rotate_scale(dasm_State** Dst) {
  //|->translation_rotate_scale:
  dasm_put(Dst, 309);
#line 158 "codegen.dasc"
  // obtain matrix
  //| mov r8, [rcx]
  //| add rcx, 16
  dasm_put(Dst, 3);
#line 161 "codegen.dasc"
  // store translation in xmm0
  //| movaps xmm0, [rcx]
  //| add rcx, sizeof(float)*4
  dasm_put(Dst, 312, sizeof(float)*4);
#line 164 "codegen.dasc"
  // store quaternion in xmm1
  //| movaps xmm1, [rcx]
  //| add rcx, sizeof(float)*4
  dasm_put(Dst, 320, sizeof(float)*4);
#line 167 "codegen.dasc"
  // store scale in xmm2
  //| movaps xmm2, [rcx]
  //| add rcx, sizeof(float)*4
  dasm_put(Dst, 328, sizeof(float)*4);
#line 170 "codegen.dasc"
  
  // TODO: Here we need to actually do what Matrix4f.translationRotateScale() does,
  //       just only better.
}


static void rotateZ(dasm_State** Dst) {
  //|->rotateZ:
  //| mov r8, [rcx]
  //| add rcx, 8
  //| movss xmm0, dword [rcx] // sin
  //| add rcx, 4
  //| movss xmm1, dword [rcx] // cos
  //| add rcx, 4
  //| shufps xmm0, xmm0, _MM_SHUFFLE(0, 0, 0, 0)
  //| shufps xmm1, xmm1, _MM_SHUFFLE(0, 0, 0, 0)
  //| xorps xmm2, xmm2
  //| subps xmm2, xmm0 // -sin
  //| movaps xmm3, [r8]
  //| mulps xmm3, xmm1 // m0X * rm00
  //| movaps xmm4, [r8+4*4]
  //| mulps xmm4, xmm0 // m1X * rm01
  //| addps xmm3, xmm4
  //| movaps xmm4, [r8]
  //| mulps xmm4, xmm2 // m0X * rm10
  //| movaps xmm5, [r8+4*4]
  //| mulps xmm5, xmm1 // m1X * rm11
  //| addps xmm4, xmm5
  //| movaps [r8], xmm3
  //| movaps [r8+4*4], xmm4
  dasm_put(Dst, 336, _MM_SHUFFLE(0, 0, 0, 0), _MM_SHUFFLE(0, 0, 0, 0), 4*4, 4*4, 4*4);
#line 200 "codegen.dasc"
}

static void vector_negate(dasm_State** Dst) {
  //|->vector_negate:
  dasm_put(Dst, 424);
#line 204 "codegen.dasc"
  // obtain vector
  //| mov r8, [rcx]
  //| add rcx, 16
  //| movaps xmm0, [r8]
  //| xorps xmm1, xmm1 // make it zero
  //| subps xmm1, xmm0 // xmm1 = xmm1 - xmm0
  //| movaps [r8], xmm1
  dasm_put(Dst, 427);
#line 211 "codegen.dasc"
}

batch_func_t codegen(const char* opcodes, int opcodesLength) {
  dasm_State* state;
  dasm_State** Dst = &state;
  int status;
  void* code;
  int next_pc = 0;
  size_t code_size;
  DWORD dwOld;
  void* global_labels[GLOB__MAX];
  char op_generated[] = {0, 0, 0, 0, 0, 0, 0};
  dasm_init(&state, DASM_MAXSECTION);
  dasm_setupglobal(&state, global_labels, GLOB__MAX);
  dasm_setup(&state, actionlist);
  dasm_growpc(&state, opcodesLength);
  for (int i = 0; i < opcodesLength; i++) {
    switch (opcodes[i]) {
    case 0x01: // OPCODE_MATRIX_MUL_MATRIX
      // tell the matrix function where to jump to
      // in order to execute the next operation
      //| mov rdx, =>next_pc
      //| jmp ->mul_matrix_matrix
      dasm_put(Dst, 449, next_pc);
#line 234 "codegen.dasc"
      if (!op_generated[0]) {
        mul_matrix_matrix(&state);
        // jump to next operation
        //| jmp rdx
        dasm_put(Dst, 458);
#line 238 "codegen.dasc"
        op_generated[0] = 1;
      }
      // define the label that we just jumped to above
      //|=>next_pc:
      dasm_put(Dst, 462, next_pc);
#line 242 "codegen.dasc"
      // use a new fresh label "index" for the next label
      next_pc++;
      break;
    case 0x02: // OPCODE_MATRIX_MUL_VECTOR
      //| mov rdx, =>next_pc
      //| jmp ->mul_matrix_vector
      dasm_put(Dst, 464, next_pc);
#line 248 "codegen.dasc"
      if (!op_generated[1]) {
        mul_matrix_vector(&state);
        // jump to next operation
        //| jmp rdx
        dasm_put(Dst, 458);
#line 252 "codegen.dasc"
        op_generated[1] = 1;
      }
      //|=>next_pc:
      dasm_put(Dst, 462, next_pc);
#line 255 "codegen.dasc"
      next_pc++;
      break;
    case 0x03: // OPCODE_MATRIX_TRANSPOSE
      //| mov rdx, =>next_pc
      //| jmp ->matrix_transpose
      dasm_put(Dst, 473, next_pc);
#line 260 "codegen.dasc"
      if (!op_generated[2]) {
        matrix_transpose(&state);
        // jump to next operation
        //| jmp rdx
        dasm_put(Dst, 458);
#line 264 "codegen.dasc"
        op_generated[2] = 1;
      }
      //|=>next_pc:
      dasm_put(Dst, 462, next_pc);
#line 267 "codegen.dasc"
      next_pc++;
      break;
    case 0x04: // OPCODE_MATRIX_INVERT
      // Not yet implemented!
      break;
    case 0x05: // OPCODE_TRANSLATION_ROTATE_SCALE
      //| mov rdx, =>next_pc
      //| jmp ->translation_rotate_scale
      dasm_put(Dst, 482, next_pc);
#line 275 "codegen.dasc"
      if (!op_generated[4]) {
        translation_rotate_scale(&state);
        // jump to next operation
        //| jmp rdx
        dasm_put(Dst, 458);
#line 279 "codegen.dasc"
        op_generated[4] = 1;
      }
      //|=>next_pc:
      dasm_put(Dst, 462, next_pc);
#line 282 "codegen.dasc"
      next_pc++;
      break;
    case 0x06: // OPCODE_ROTATEZ
      //| mov rdx, =>next_pc
      //| jmp ->rotateZ
      dasm_put(Dst, 491, next_pc);
#line 287 "codegen.dasc"
      if (!op_generated[5]) {
        rotateZ(&state);
        // jump to next operation
        //| jmp rdx
        dasm_put(Dst, 458);
#line 291 "codegen.dasc"
        op_generated[5] = 1;
      }
      //|=>next_pc:
      dasm_put(Dst, 462, next_pc);
#line 294 "codegen.dasc"
      next_pc++;
      break;
    case 0x07: // OPCODE_VECTOR_NEGATE
      //| mov rdx, =>next_pc
      //| jmp ->vector_negate
      dasm_put(Dst, 500, next_pc);
#line 299 "codegen.dasc"
      if (!op_generated[6]) {
        vector_negate(&state);
        //| jmp rdx
        dasm_put(Dst, 458);
#line 302 "codegen.dasc"
        op_generated[6] = 1;
      }
      //|=>next_pc:
      dasm_put(Dst, 462, next_pc);
#line 305 "codegen.dasc"
      next_pc++;
      break;
    default:
      break;
    }
  }
  //| ret
  dasm_put(Dst, 509);
#line 312 "codegen.dasc"
  status = dasm_link(&state, &code_size);
  code = VirtualAlloc(0, code_size, MEM_RESERVE | MEM_COMMIT, PAGE_READWRITE);
  status = dasm_encode(&state, code);
  VirtualProtect(code, code_size, PAGE_EXECUTE, &dwOld);
  FlushInstructionCache(GetCurrentProcess(), code, code_size);
  dasm_free(&state);
  return (batch_func_t) code;
}
