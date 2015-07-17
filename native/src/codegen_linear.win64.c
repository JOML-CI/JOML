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
static const unsigned char actionlist[686] = {
  65,15,40,192,15,40,208,65,15,40,201,15,198,193,136,15,198,209,221,65,15,40,
  218,15,40,252,235,65,15,40,252,243,15,198,222,136,15,198,252,238,221,15,40,
  200,15,198,195,136,15,40,226,15,198,213,136,15,198,203,221,15,198,230,221,
  255,68,15,40,192,68,15,40,202,68,15,40,209,68,15,40,220,255,68,15,40,224,
  68,15,40,252,234,68,15,40,252,241,68,15,40,252,252,255,69,15,40,196,69,15,
  40,205,69,15,40,214,69,15,40,223,255,76,139,1,72,131,193,16,69,15,40,32,69,
  15,40,168,233,69,15,40,176,233,69,15,40,184,233,255,76,139,1,72,131,193,16,
  69,15,40,0,69,15,40,136,233,69,15,40,144,233,69,15,40,152,233,255,76,139,
  1,72,131,193,16,69,15,41,32,69,15,41,168,233,69,15,41,176,233,69,15,41,184,
  233,255,76,139,1,72,131,193,16,69,15,41,0,69,15,41,136,233,69,15,41,144,233,
  69,15,41,152,233,255,15,40,200,15,198,201,235,15,40,208,15,198,210,235,15,
  40,216,15,198,219,235,15,40,224,15,198,228,235,65,15,40,192,15,89,193,65,
  15,40,201,15,89,202,65,15,40,210,15,89,211,65,15,40,219,15,89,220,15,88,193,
  15,88,211,15,88,194,255,65,15,40,196,255,65,15,40,197,255,65,15,40,198,255,
  65,15,40,199,255,68,15,40,192,255,68,15,40,200,255,68,15,40,208,255,68,15,
  40,216,255,68,15,40,224,255,68,15,40,232,255,68,15,40,252,240,255,68,15,40,
  252,248,255,252,243,15,16,1,72,131,193,4,252,243,15,16,9,72,131,193,12,15,
  198,192,235,15,198,201,235,15,87,210,15,92,208,65,15,40,216,15,89,217,65,
  15,40,226,15,89,226,15,88,220,65,15,40,224,15,89,224,65,15,40,252,234,15,
  89,252,233,15,88,229,255,68,15,40,195,68,15,40,212,255,68,15,40,227,69,15,
  40,252,233,68,15,40,252,244,69,15,40,252,251,255,73,199,193,0,0,128,63,102,
  77,15,110,193,69,15,40,200,69,15,198,201,147,69,15,40,209,69,15,198,210,147,
  69,15,40,218,69,15,198,219,147,255,85,72,137,229,255,72,129,252,236,239,252,
  243,15,127,52,36,252,243,15,127,188,253,36,233,252,243,68,15,127,132,253,
  36,233,252,243,68,15,127,140,253,36,233,252,243,68,15,127,148,253,36,233,
  252,243,68,15,127,156,253,36,233,252,243,68,15,127,164,253,36,233,252,243,
  68,15,127,172,253,36,233,252,243,68,15,127,180,253,36,233,252,243,68,15,127,
  188,253,36,233,255,252,243,15,111,52,36,252,243,15,111,188,253,36,233,252,
  243,68,15,111,132,253,36,233,252,243,68,15,111,140,253,36,233,252,243,68,
  15,111,148,253,36,233,252,243,68,15,111,156,253,36,233,252,243,68,15,111,
  164,253,36,233,252,243,68,15,111,172,253,36,233,252,243,68,15,111,180,253,
  36,233,252,243,68,15,111,188,253,36,233,72,129,196,239,72,137,252,236,93,
  195,255
};

#line 13 "codegen_linear.dasc"

#ifndef _MM_SHUFFLE
#define _MM_SHUFFLE(x,y,z,w) ((z << 6) | (y <<4) | (x << 2) | (w))
#endif

static void matrix_transpose(dasm_State** Dst, char intoSecond) {
  // requires matrix to be in first (xmm8-xmm11)
  // will store in either first or second (xmm12-xmm15)
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
#line 37 "codegen_linear.dasc"
  if (!intoSecond) {
    // store back into first
    //| movaps xmm8, xmm0
    //| movaps xmm9, xmm2
    //| movaps xmm10, xmm1
    //| movaps xmm11, xmm4
    dasm_put(Dst, 64);
#line 43 "codegen_linear.dasc"
  } else {
    // store back into second
    //| movaps xmm12, xmm0
    //| movaps xmm13, xmm2
    //| movaps xmm14, xmm1
    //| movaps xmm15, xmm4
    dasm_put(Dst, 81);
#line 49 "codegen_linear.dasc"
  }
}

static void copy_first_from_second(dasm_State** Dst) {
  //| movaps xmm8, xmm12
  //| movaps xmm9, xmm13
  //| movaps xmm10, xmm14
  //| movaps xmm11, xmm15
  dasm_put(Dst, 101);
#line 57 "codegen_linear.dasc"
}

static void load_second(dasm_State** Dst) {
  //| mov r8, [rcx]
  //| add rcx, 16
  //| movaps xmm12, [r8]
  //| movaps xmm13, [r8+4*4]
  //| movaps xmm14, [r8+4*8]
  //| movaps xmm15, [r8+4*12]
  dasm_put(Dst, 118, 4*4, 4*8, 4*12);
#line 66 "codegen_linear.dasc"
}

static void load_first(dasm_State** Dst) {
  //| mov r8, [rcx]
  //| add rcx, 16
  //| movaps xmm8, [r8]
  //| movaps xmm9, [r8+4*4]
  //| movaps xmm10, [r8+4*8]
  //| movaps xmm11, [r8+4*12]
  dasm_put(Dst, 145, 4*4, 4*8, 4*12);
#line 75 "codegen_linear.dasc"
}

static void store_second(dasm_State** Dst) {
  //| mov r8, [rcx]
  //| add rcx, 16
  //| movaps [r8], xmm12
  //| movaps [r8+4*4], xmm13
  //| movaps [r8+4*8], xmm14
  //| movaps [r8+4*12], xmm15
  dasm_put(Dst, 172, 4*4, 4*8, 4*12);
#line 84 "codegen_linear.dasc"
}

static void store_first(dasm_State** Dst) {
  //| mov r8, [rcx]
  //| add rcx, 16
  //| movaps [r8], xmm8
  //| movaps [r8+4*4], xmm9
  //| movaps [r8+4*8], xmm10
  //| movaps [r8+4*12], xmm11
  dasm_put(Dst, 199, 4*4, 4*8, 4*12);
#line 93 "codegen_linear.dasc"
}

static void linearProduct(dasm_State** Dst) {
  // vector is in xmm0, matrix in xmm8-xmm11
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
  dasm_put(Dst, 226, _MM_SHUFFLE(0, 0, 0, 0), _MM_SHUFFLE(1, 1, 1, 1), _MM_SHUFFLE(2, 2, 2, 2), _MM_SHUFFLE(3, 3, 3, 3));
#line 116 "codegen_linear.dasc"
}

static void matrix_mul_matrix(dasm_State** Dst, char intoSecond) {
  // requires:
  // left matrix in xmm8-xmm11 (aka. first)
  // right matrix in xmm12-xmm15 (aka. second)
  // will store in either first or second
  for (int i = 0; i < 4; i++) {
    // load column/vector from second
    if (i == 0) {
      //| movaps xmm0, xmm12
      dasm_put(Dst, 292);
#line 127 "codegen_linear.dasc"
    } else if (i == 1) {
      //| movaps xmm0, xmm13
      dasm_put(Dst, 297);
#line 129 "codegen_linear.dasc"
    } else if (i == 2) {
      //| movaps xmm0, xmm14
      dasm_put(Dst, 302);
#line 131 "codegen_linear.dasc"
    } else if (i == 3) {
      //| movaps xmm0, xmm15
      dasm_put(Dst, 307);
#line 133 "codegen_linear.dasc"
    }
    linearProduct(Dst);
    if (!intoSecond) {
      // store into first
      if (i == 0) {
        //| movaps xmm8, xmm0
        dasm_put(Dst, 312);
#line 139 "codegen_linear.dasc"
      } else if (i == 1) {
        //| movaps xmm9, xmm0
        dasm_put(Dst, 317);
#line 141 "codegen_linear.dasc"
      } else if (i == 2) {
        //| movaps xmm10, xmm0
        dasm_put(Dst, 322);
#line 143 "codegen_linear.dasc"
      } else if (i == 3) {
        //| movaps xmm11, xmm0
        dasm_put(Dst, 327);
#line 145 "codegen_linear.dasc"
      }
    } else {
      // store into second
      if (i == 0) {
        //| movaps xmm12, xmm0
        dasm_put(Dst, 332);
#line 150 "codegen_linear.dasc"
      } else if (i == 1) {
        //| movaps xmm13, xmm0
        dasm_put(Dst, 337);
#line 152 "codegen_linear.dasc"
      } else if (i == 2) {
        //| movaps xmm14, xmm0
        dasm_put(Dst, 342);
#line 154 "codegen_linear.dasc"
      } else if (i == 3) {
        //| movaps xmm15, xmm0
        dasm_put(Dst, 348);
#line 156 "codegen_linear.dasc"
      }
    }
  }
}

static void rotateY(dasm_State** Dst, char intoSecond) {
  // requires matrix to be in xmm8-xmm11 (aka. first)
  // will store in either first or second (xmm12-xmm15)
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
  dasm_put(Dst, 354, _MM_SHUFFLE(0, 0, 0, 0), _MM_SHUFFLE(0, 0, 0, 0));
#line 182 "codegen_linear.dasc"
  if (!intoSecond) {
    //| movaps xmm8, xmm3
    //| movaps xmm10, xmm4
    dasm_put(Dst, 423);
#line 185 "codegen_linear.dasc"
  } else {
    //| movaps xmm12, xmm3
    //| movaps xmm13, xmm9
    //| movaps xmm14, xmm4
    //| movaps xmm15, xmm11
    dasm_put(Dst, 432);
#line 190 "codegen_linear.dasc"
  }
}

static void matrix_identity(dasm_State** Dst) {
  // requires matrix to be in first (xmm8-xmm11)
  // will store in first
  //| mov r9, 0x3f800000 // int representation of 1.0f
  //| movd xmm8, r9
  //| movaps xmm9, xmm8
  //| shufps xmm9, xmm9, 0x93
  //| movaps xmm10, xmm9
  //| shufps xmm10, xmm10, 0x93
  //| movaps xmm11, xmm10
  //| shufps xmm11, xmm11, 0x93
  dasm_put(Dst, 452);
#line 204 "codegen_linear.dasc"
}

static void prologue(dasm_State** Dst) {
  //| push rbp
  //| mov rbp, rsp
  dasm_put(Dst, 492);
#line 209 "codegen_linear.dasc"
  // save all non-volatile registers that we use
  //| sub rsp, 4*16*10 // xmm6-xmm15 are non-volatile
  //| movdqu [rsp], xmm6
  //| movdqu [rsp+4*16], xmm7
  //| movdqu [rsp+4*16*2], xmm8
  //| movdqu [rsp+4*16*3], xmm9
  //| movdqu [rsp+4*16*4], xmm10
  //| movdqu [rsp+4*16*5], xmm11
  //| movdqu [rsp+4*16*6], xmm12
  //| movdqu [rsp+4*16*7], xmm13
  //| movdqu [rsp+4*16*8], xmm14
  //| movdqu [rsp+4*16*9], xmm15
  dasm_put(Dst, 497, 4*16*10, 4*16, 4*16*2, 4*16*3, 4*16*4, 4*16*5, 4*16*6, 4*16*7, 4*16*8, 4*16*9);
#line 221 "codegen_linear.dasc"
}

static void epilogue(dasm_State** Dst) {
  //| movdqu xmm6, [rsp]
  //| movdqu xmm7, [rsp+4*16]
  //| movdqu xmm8, [rsp+4*16*2]
  //| movdqu xmm9, [rsp+4*16*3]
  //| movdqu xmm10, [rsp+4*16*4]
  //| movdqu xmm11, [rsp+4*16*5]
  //| movdqu xmm12, [rsp+4*16*6]
  //| movdqu xmm13, [rsp+4*16*7]
  //| movdqu xmm14, [rsp+4*16*8]
  //| movdqu xmm15, [rsp+4*16*9]
  //| add rsp, 4*16*10
  //| mov rsp, rbp
  //| pop rbp
  //| ret
  dasm_put(Dst, 589, 4*16, 4*16*2, 4*16*3, 4*16*4, 4*16*5, 4*16*6, 4*16*7, 4*16*8, 4*16*9, 4*16*10);
#line 238 "codegen_linear.dasc"
}

batch_func_t codegen(const unsigned char* opcodes, int opcodesLength, size_t* codeSize) {
  dasm_State* state;
  dasm_State** Dst = &state;
  int status;
  void* code;
  DWORD dwOld;
  void* global_labels[GLOB__MAX];
  dasm_init(&state, DASM_MAXSECTION);
  dasm_setupglobal(&state, global_labels, GLOB__MAX);
  dasm_setup(&state, actionlist);
  prologue(Dst);
  for (int i = 0; i < opcodesLength; i++) {
    unsigned char opcode = opcodes[i];
    char toSecond = (opcode & OPCODE_MASK_TO_SECOND) != 0;
    switch (opcode) {
    case OPCODE_MATRIX_MUL_MATRIX:
      matrix_mul_matrix(&state, toSecond); break;
    case OPCODE_MATRIX_TRANSPOSE:
      matrix_transpose(&state, toSecond); break;
    case OPCODE_MATRIX_IDENTITY:
      matrix_identity(&state); break;
    case OPCODE_ROTATEY:
      rotateY(&state, toSecond); break;
    case OPCODE_STORE_FIRST:
      store_first(&state); break;
    case OPCODE_STORE_SECOND:
      store_second(&state); break;
    case OPCODE_LOAD_FIRST:
      load_first(&state); break;
    case OPCODE_LOAD_SECOND:
      load_second(&state); break;
    case OPCODE_COPY_FIRST_FROM_SECOND:
      copy_first_from_second(&state); break;
    default:
      break;
    }
  }
  epilogue(Dst);
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
