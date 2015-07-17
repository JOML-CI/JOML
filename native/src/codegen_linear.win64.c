/*
** This file has been pre-processed with DynASM.
** http://luajit.org/dynasm.html
** DynASM version 1.3.0, DynASM x64 version 1.3.0
** DO NOT EDIT! The original file is in "codegen_linear.dasc".
*/

#line 1 "codegen_linear.dasc"
#include <stdlib.h>
#include <Windows.h>

#include "dynasm/dasm_proto.h"
#include "dynasm/dasm_x86.h"
#include "codegen.h"
#include "opcodes.h"

//|.arch x64
#if DASM_VERSION != 10300
#error "Version mismatch between DynASM and included encoding engine"
#endif
#line 10 "codegen_linear.dasc"
//|.section code
#define DASM_SECTION_CODE	0
#define DASM_MAXSECTION		1
#line 11 "codegen_linear.dasc"
//|.globals GLOB_
enum {
  GLOB__MAX
};
#line 12 "codegen_linear.dasc"
//|.actionlist actionlist
static const unsigned char actionlist[449] = {
  65,15,40,192,15,40,208,65,15,40,201,15,198,193,136,15,198,209,221,65,15,40,
  218,15,40,252,235,65,15,40,252,243,15,198,222,136,15,198,252,238,221,15,40,
  200,15,198,195,136,15,40,226,15,198,213,136,15,198,203,221,15,198,230,221,
  255,68,15,40,192,68,15,40,202,68,15,40,209,68,15,40,220,255,15,40,200,15,
  198,201,235,15,40,208,15,198,210,235,15,40,216,15,198,219,235,15,40,224,15,
  198,228,235,65,15,40,192,15,89,193,65,15,40,201,15,89,202,65,15,40,210,15,
  89,211,65,15,40,219,15,89,220,15,88,193,15,88,211,15,88,194,255,76,139,1,
  72,131,193,16,68,15,40,33,68,15,40,169,233,68,15,40,177,233,68,15,40,185,
  233,255,76,139,1,72,131,193,16,68,15,40,1,68,15,40,137,233,68,15,40,145,233,
  68,15,40,153,233,255,76,139,1,72,131,193,16,68,15,41,33,68,15,41,169,233,
  68,15,41,177,233,68,15,41,185,233,255,76,139,1,72,131,193,16,68,15,41,1,68,
  15,41,137,233,68,15,41,145,233,68,15,41,153,233,255,76,139,9,72,131,193,16,
  255,65,15,40,196,255,65,15,40,197,255,65,15,40,198,255,65,15,40,199,255,68,
  15,40,192,255,68,15,40,200,255,68,15,40,208,255,68,15,40,216,255,252,243,
  15,16,1,72,131,193,4,252,243,15,16,9,72,131,193,12,15,198,192,235,15,198,
  201,235,15,87,210,15,92,208,65,15,40,216,15,89,217,65,15,40,226,15,89,226,
  15,88,220,65,15,40,224,15,89,224,65,15,40,252,234,15,89,252,233,15,88,229,
  68,15,40,195,68,15,40,212,255,76,139,1,72,131,193,16,69,15,41,0,69,15,41,
  136,233,69,15,41,144,233,69,15,41,152,233,255,73,199,193,0,0,128,63,102,77,
  15,110,193,69,15,40,200,69,15,198,201,147,69,15,40,209,69,15,198,210,147,
  69,15,40,218,69,15,198,219,147,255,195,255
};

#line 13 "codegen_linear.dasc"

#ifndef _MM_SHUFFLE
#define _MM_SHUFFLE(x,y,z,w) ((z << 6) | (y <<4) | (x << 2) | (w))
#endif

static void matrix_transpose(dasm_State** Dst) {
  // expect matrix to be in xmm8-xmm11
  //| movaps xmm0, xmm8
  //| movaps xmm2, xmm0
  //| movaps xmm1, xmm9
  //| shufps xmm0, xmm1, 0x88
  //| shufps xmm2, xmm1, 0xdd
  //| movaps xmm3, xmm10
  //| movaps xmm5, xmm3
  //| movaps xmm6, xmm11
  //| shufps xmm3, xmm6, 0x88
  //| shufps xmm5, xmm6, 0xdd
  //| movaps xmm1, xmm0
  //| shufps xmm0, xmm3, 0x88
  //| movaps xmm4, xmm2
  //| shufps xmm2, xmm5, 0x88
  //| shufps xmm1, xmm3, 0xdd
  //| shufps xmm4, xmm6, 0xdd
  dasm_put(Dst, 0);
#line 36 "codegen_linear.dasc"
  // store back into first
  //| movaps xmm8, xmm0
  //| movaps xmm9, xmm2
  //| movaps xmm10, xmm1
  //| movaps xmm11, xmm4
  dasm_put(Dst, 64);
#line 41 "codegen_linear.dasc"
}

static void linearProduct(dasm_State** Dst) {
  // assume matrix to be in xmm8-xmm11
  //| movaps xmm1, xmm0
  //| shufps xmm1, xmm1, _MM_SHUFFLE(0, 0, 0, 0)
  //| movaps xmm2, xmm0
  //| shufps xmm2, xmm2, _MM_SHUFFLE(1, 1, 1, 1)
  //| movaps xmm3, xmm0
  //| shufps xmm3, xmm3, _MM_SHUFFLE(2, 2, 2, 2)
  //| movaps xmm4, xmm0
  //| shufps xmm4, xmm4, _MM_SHUFFLE(3, 3, 3, 3)
  //| movaps xmm0, xmm8
  //| mulps xmm0, xmm1
  //| movaps xmm1, xmm9
  //| mulps xmm1, xmm2
  //| movaps xmm2, xmm10
  //| mulps xmm2, xmm3
  //| movaps xmm3, xmm11
  //| mulps xmm3, xmm4
  //| addps xmm0, xmm1
  //| addps xmm2, xmm3
  //| addps xmm0, xmm2
  dasm_put(Dst, 81, _MM_SHUFFLE(0, 0, 0, 0), _MM_SHUFFLE(1, 1, 1, 1), _MM_SHUFFLE(2, 2, 2, 2), _MM_SHUFFLE(3, 3, 3, 3));
#line 64 "codegen_linear.dasc"
}

static void load_second(dasm_State** Dst) {
  //| mov r8, [rcx]
  //| add rcx, 16
  //| movaps xmm12, [rcx]
  //| movaps xmm13, [rcx+4*4]
  //| movaps xmm14, [rcx+4*8]
  //| movaps xmm15, [rcx+4*12]
  dasm_put(Dst, 147, 4*4, 4*8, 4*12);
#line 73 "codegen_linear.dasc"
}

static void load_first(dasm_State** Dst) {
  //| mov r8, [rcx]
  //| add rcx, 16
  //| movaps xmm8, [rcx]
  //| movaps xmm9, [rcx+4*4]
  //| movaps xmm10, [rcx+4*8]
  //| movaps xmm11, [rcx+4*12]
  dasm_put(Dst, 174, 4*4, 4*8, 4*12);
#line 82 "codegen_linear.dasc"
}

static void store_second(dasm_State** Dst) {
  //| mov r8, [rcx]
  //| add rcx, 16
  //| movaps [rcx], xmm12
  //| movaps [rcx+4*4], xmm13
  //| movaps [rcx+4*8], xmm14
  //| movaps [rcx+4*12], xmm15
  dasm_put(Dst, 201, 4*4, 4*8, 4*12);
#line 91 "codegen_linear.dasc"
}

static void store_first(dasm_State** Dst) {
  //| mov r8, [rcx]
  //| add rcx, 16
  //| movaps [rcx], xmm8
  //| movaps [rcx+4*4], xmm9
  //| movaps [rcx+4*8], xmm10
  //| movaps [rcx+4*12], xmm11
  dasm_put(Dst, 228, 4*4, 4*8, 4*12);
#line 100 "codegen_linear.dasc"
}

static void mul_matrix_matrix(dasm_State** Dst) {
  // obtain second matrix
  //| mov r9, [rcx]
  //| add rcx, 16
  dasm_put(Dst, 255);
#line 106 "codegen_linear.dasc"
  for (int i = 0; i < 4; i++) {
    // load from second
    if (i == 0) {
      //| movaps xmm0, xmm12
      dasm_put(Dst, 263);
#line 110 "codegen_linear.dasc"
    } else if (i == 1) {
      //| movaps xmm0, xmm13
      dasm_put(Dst, 268);
#line 112 "codegen_linear.dasc"
    } else if (i == 2) {
      //| movaps xmm0, xmm14
      dasm_put(Dst, 273);
#line 114 "codegen_linear.dasc"
    } else if (i == 3) {
      //| movaps xmm0, xmm15
      dasm_put(Dst, 278);
#line 116 "codegen_linear.dasc"
    }
    linearProduct(Dst);
    // store into first
    if (i == 0) {
      //| movaps xmm8, xmm0
      dasm_put(Dst, 283);
#line 121 "codegen_linear.dasc"
    } else if (i == 1) {
      //| movaps xmm9, xmm0
      dasm_put(Dst, 288);
#line 123 "codegen_linear.dasc"
    } else if (i == 2) {
      //| movaps xmm10, xmm0
      dasm_put(Dst, 293);
#line 125 "codegen_linear.dasc"
    } else if (i == 3) {
      //| movaps xmm11, xmm0
      dasm_put(Dst, 298);
#line 127 "codegen_linear.dasc"
    }
  }
}

static void rotateY(dasm_State** Dst) {
  //| movss xmm0, dword [rcx] // sin
  //| add rcx, 4
  //| movss xmm1, dword [rcx] // cos
  //| add rcx, 12
  //| shufps xmm0, xmm0, _MM_SHUFFLE(0, 0, 0, 0)
  //| shufps xmm1, xmm1, _MM_SHUFFLE(0, 0, 0, 0)
  //| xorps xmm2, xmm2
  //| subps xmm2, xmm0 // -sin
  //| movaps xmm3, xmm8
  //| mulps xmm3, xmm1 // m0X * rm00
  //| movaps xmm4, xmm10
  //| mulps xmm4, xmm2 // m2X * rm02
  //| addps xmm3, xmm4
  //| movaps xmm4, xmm8
  //| mulps xmm4, xmm0 // m0X * rm20
  //| movaps xmm5, xmm10
  //| mulps xmm5, xmm1 // m2X * rm22
  //| addps xmm4, xmm5
  //| movaps xmm8, xmm3
  //| movaps xmm10, xmm4
  dasm_put(Dst, 303, _MM_SHUFFLE(0, 0, 0, 0), _MM_SHUFFLE(0, 0, 0, 0));
#line 152 "codegen_linear.dasc"
}

static void matrix_get(dasm_State** Dst) {
  //| mov r8, [rcx]
  //| add rcx, 16
  //| movaps [r8], xmm8
  //| movaps [r8+4*4], xmm9
  //| movaps [r8+4*8], xmm10
  //| movaps [r8+4*12], xmm11
  dasm_put(Dst, 380, 4*4, 4*8, 4*12);
#line 161 "codegen_linear.dasc"
}

static void matrix_identity(dasm_State** Dst) {
  //| mov r9, 0x3f800000 // int representation of 1.0f
  //| movd xmm8, r9
  //| movaps xmm9, xmm8
  //| shufps xmm9, xmm9, 0x93
  //| movaps xmm10, xmm9
  //| shufps xmm10, xmm10, 0x93
  //| movaps xmm11, xmm10
  //| shufps xmm11, xmm11, 0x93
  dasm_put(Dst, 407);
#line 172 "codegen_linear.dasc"
}

batch_func_t codegen(const char* opcodes, int opcodesLength, size_t* codeSize) {
  dasm_State* state;
  dasm_State** Dst = &state;
  int status;
  void* code;
  DWORD dwOld;
  void* global_labels[GLOB__MAX];
  dasm_init(&state, DASM_MAXSECTION);
  dasm_setupglobal(&state, global_labels, GLOB__MAX);
  dasm_setup(&state, actionlist);
  for (int i = 0; i < opcodesLength; i++) {
    switch (opcodes[i]) {
    case OPCODE_MATRIX_MUL_MATRIX:
      mul_matrix_matrix(&state); break;
    case OPCODE_MATRIX_TRANSPOSE:
      matrix_transpose(&state); break;
    case OPCODE_MATRIX_GET:
      matrix_get(&state); break;
    case OPCODE_MATRIX_IDENTITY:
      matrix_identity(&state); break;
    case OPCODE_ROTATEY:
      rotateY(&state); break;
    default:
      break;
    }
  }
  //| ret
  dasm_put(Dst, 447);
#line 201 "codegen_linear.dasc"
  status = dasm_link(&state, codeSize);
  code = VirtualAlloc(0, *codeSize, MEM_RESERVE | MEM_COMMIT, PAGE_READWRITE);
  status = dasm_encode(&state, code);
  VirtualProtect(code, *codeSize, PAGE_EXECUTE, &dwOld);
  FlushInstructionCache(GetCurrentProcess(), code, *codeSize);
  dasm_free(&state);
  return (batch_func_t) code;
}

int free_code(void* code, size_t codeSize) {
  return VirtualFree(code, 0, MEM_RELEASE);
}
