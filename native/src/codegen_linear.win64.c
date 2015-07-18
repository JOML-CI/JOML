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
static const unsigned char actionlist[1181] = {
  65,15,40,192,15,40,208,65,15,40,201,15,198,193,235,15,198,209,235,65,15,40,
  218,15,40,252,235,65,15,40,252,243,15,198,222,235,15,198,252,238,235,15,40,
  200,15,198,195,235,15,40,226,15,198,213,235,15,198,203,235,15,198,230,235,
  255,68,15,40,192,68,15,40,202,68,15,40,209,68,15,40,220,255,68,15,40,224,
  68,15,40,252,234,68,15,40,252,241,68,15,40,252,252,255,69,15,40,196,69,15,
  40,205,69,15,40,214,69,15,40,223,255,69,15,40,224,69,15,40,252,233,69,15,
  40,252,242,69,15,40,252,251,255,76,139,1,72,131,193,8,76,139,9,72,131,193,
  8,65,15,40,0,65,15,41,1,65,15,40,136,233,65,15,41,137,233,65,15,40,144,233,
  65,15,41,145,233,65,15,40,152,233,65,15,41,153,233,255,76,139,1,72,131,193,
  16,255,69,15,40,0,69,15,40,136,233,69,15,40,144,233,69,15,40,152,233,255,
  69,15,40,32,69,15,40,168,233,69,15,40,176,233,69,15,40,184,233,255,69,15,
  41,0,69,15,41,136,233,69,15,41,144,233,69,15,41,152,233,255,69,15,41,32,69,
  15,41,168,233,69,15,41,176,233,69,15,41,184,233,255,15,40,200,15,198,201,
  235,15,40,208,15,198,210,235,15,40,216,15,198,219,235,15,40,224,15,198,228,
  235,65,15,40,192,15,89,193,65,15,40,201,15,89,202,65,15,40,210,15,89,211,
  65,15,40,219,15,89,220,15,88,193,15,88,211,15,88,194,255,65,15,40,196,255,
  65,15,40,197,255,65,15,40,198,255,65,15,40,199,255,15,40,232,255,15,40,252,
  240,255,15,40,252,248,255,68,15,40,197,68,15,40,206,68,15,40,215,68,15,40,
  216,255,68,15,40,224,255,68,15,40,232,255,68,15,40,252,240,255,68,15,40,252,
  248,255,252,243,15,16,1,72,131,193,4,252,243,15,16,9,72,131,193,12,15,198,
  192,235,15,198,201,235,73,199,193,0,0,0,128,102,73,15,110,209,15,198,210,
  0,15,87,208,65,15,40,217,15,89,217,65,15,40,226,15,89,224,15,88,220,65,15,
  40,225,15,89,226,65,15,40,252,234,15,89,252,233,15,88,229,255,68,15,40,203,
  68,15,40,212,255,69,15,40,224,68,15,40,252,235,68,15,40,252,244,69,15,40,
  252,251,255,252,243,15,16,1,72,131,193,4,252,243,15,16,9,72,131,193,12,15,
  198,192,235,15,198,201,235,73,199,193,0,0,0,128,102,73,15,110,209,15,198,
  210,0,15,87,208,65,15,40,216,15,89,217,65,15,40,226,15,89,226,15,88,220,65,
  15,40,224,15,89,224,65,15,40,252,234,15,89,252,233,15,88,229,255,68,15,40,
  195,68,15,40,212,255,68,15,40,227,69,15,40,252,233,68,15,40,252,244,69,15,
  40,252,251,255,252,243,15,16,1,72,131,193,4,252,243,15,16,9,72,131,193,12,
  15,198,192,235,15,198,201,235,73,199,193,0,0,0,128,102,73,15,110,209,15,198,
  210,0,15,87,208,65,15,40,216,15,89,217,65,15,40,225,15,89,224,15,88,220,65,
  15,40,224,15,89,226,65,15,40,252,233,15,89,252,233,15,88,229,255,68,15,40,
  195,68,15,40,204,255,68,15,40,227,68,15,40,252,236,69,15,40,252,242,69,15,
  40,252,251,255,73,199,193,0,0,128,63,69,15,87,192,102,77,15,110,193,69,15,
  40,200,69,15,198,201,235,69,15,40,209,69,15,198,210,235,69,15,40,218,69,15,
  198,219,235,255,15,40,1,72,131,193,16,15,40,200,15,198,201,235,255,68,15,
  89,193,255,65,15,40,208,15,89,209,68,15,40,226,255,68,15,89,201,255,65,15,
  40,209,15,89,209,68,15,40,252,234,255,68,15,89,209,255,65,15,40,210,15,89,
  209,68,15,40,252,242,69,15,40,252,251,255,15,40,1,72,131,193,16,15,40,200,
  15,198,201,235,65,15,40,208,15,89,209,15,40,200,15,198,201,235,65,15,40,217,
  15,89,217,15,88,211,15,40,200,15,198,201,235,65,15,40,218,15,89,217,15,88,
  211,65,15,40,203,15,88,209,255,68,15,40,218,255,69,15,40,224,69,15,40,252,
  233,69,15,40,252,242,68,15,40,252,250,255,76,139,1,72,131,193,16,65,15,40,
  0,73,199,193,0,0,0,128,102,73,15,110,201,15,198,201,0,15,87,193,65,15,41,
  0,255,85,72,137,229,72,129,252,236,239,255,252,243,68,15,127,60,36,252,243,
  68,15,127,180,253,36,233,252,243,68,15,127,172,253,36,233,252,243,68,15,127,
  164,253,36,233,252,243,68,15,127,156,253,36,233,252,243,68,15,127,148,253,
  36,233,252,243,68,15,127,140,253,36,233,252,243,68,15,127,132,253,36,233,
  252,243,15,127,188,253,36,233,252,243,15,127,180,253,36,233,255,252,243,68,
  15,111,60,36,252,243,68,15,111,180,253,36,233,252,243,68,15,111,172,253,36,
  233,252,243,68,15,111,164,253,36,233,252,243,68,15,111,156,253,36,233,252,
  243,68,15,111,148,253,36,233,252,243,68,15,111,140,253,36,233,252,243,68,
  15,111,132,253,36,233,252,243,15,111,188,253,36,233,252,243,15,111,180,253,
  36,233,72,137,252,236,93,195,255
};

#line 13 "codegen_linear.dasc"

#ifndef _MM_SHUFFLE
#define _MM_SHUFFLE(fp3,fp2,fp1,fp0) \
 (((fp3) << 6) | ((fp2) << 4) | ((fp1) << 2) | (fp0))
#endif

static void matrix_transpose(dasm_State** Dst, char storeIntoSecond) {
  // requires matrix to be in first (xmm8-xmm11)
  // will store in either first or second (xmm12-xmm15)
  //| movaps xmm0, xmm8
  //| movaps xmm2, xmm0
  //| movaps xmm1, xmm9
  //| shufps xmm0, xmm1, _MM_SHUFFLE(2, 0, 2, 0)
  //| shufps xmm2, xmm1, _MM_SHUFFLE(3, 1, 3, 1)
  //| movaps xmm3, xmm10
  //| movaps xmm5, xmm3
  //| movaps xmm6, xmm11
  //| shufps xmm3, xmm6, _MM_SHUFFLE(2, 0, 2, 0)
  //| shufps xmm5, xmm6, _MM_SHUFFLE(3, 1, 3, 1)
  //| movaps xmm1, xmm0
  //| shufps xmm0, xmm3, _MM_SHUFFLE(2, 0, 2, 0)
  //| movaps xmm4, xmm2
  //| shufps xmm2, xmm5, _MM_SHUFFLE(2, 0, 2, 0)
  //| shufps xmm1, xmm3, _MM_SHUFFLE(3, 1, 3, 1)
  //| shufps xmm4, xmm6, _MM_SHUFFLE(3, 1, 3, 1)
  dasm_put(Dst, 0, _MM_SHUFFLE(2, 0, 2, 0), _MM_SHUFFLE(3, 1, 3, 1), _MM_SHUFFLE(2, 0, 2, 0), _MM_SHUFFLE(3, 1, 3, 1), _MM_SHUFFLE(2, 0, 2, 0), _MM_SHUFFLE(2, 0, 2, 0), _MM_SHUFFLE(3, 1, 3, 1), _MM_SHUFFLE(3, 1, 3, 1));
#line 38 "codegen_linear.dasc"
  if (!storeIntoSecond) {
    // store back into first
    //| movaps xmm8, xmm0
    //| movaps xmm9, xmm2
    //| movaps xmm10, xmm1
    //| movaps xmm11, xmm4
    dasm_put(Dst, 64);
#line 44 "codegen_linear.dasc"
  } else {
    // store back into second
    //| movaps xmm12, xmm0
    //| movaps xmm13, xmm2
    //| movaps xmm14, xmm1
    //| movaps xmm15, xmm4
    dasm_put(Dst, 81);
#line 50 "codegen_linear.dasc"
  }
}

static void copy(dasm_State** Dst, char storeIntoSecond) {
if (!storeIntoSecond) {
  //| movaps xmm8, xmm12
  //| movaps xmm9, xmm13
  //| movaps xmm10, xmm14
  //| movaps xmm11, xmm15
  dasm_put(Dst, 101);
#line 59 "codegen_linear.dasc"
} else {
  //| movaps xmm12, xmm8
  //| movaps xmm13, xmm9
  //| movaps xmm14, xmm10
  //| movaps xmm15, xmm11
  dasm_put(Dst, 118);
#line 64 "codegen_linear.dasc"
}
}

static void copy_mem(dasm_State** Dst) {
  //| mov r8, [rcx]
  //| add rcx, 8
  //| mov r9, [rcx]
  //| add rcx, 8
  //| movaps xmm0, [r8]
  //| movaps [r9], xmm0
  //| movaps xmm1, [r8+4*4]
  //| movaps [r9+4*4], xmm1
  //| movaps xmm2, [r8+4*8]
  //| movaps [r9+4*8], xmm2
  //| movaps xmm3, [r8+4*12]
  //| movaps [r9+4*12], xmm3
  dasm_put(Dst, 138, 4*4, 4*4, 4*8, 4*8, 4*12, 4*12);
#line 80 "codegen_linear.dasc"
}

static void load(dasm_State** Dst, char storeIntoSecond) {
  //| mov r8, [rcx]
  //| add rcx, 16
  dasm_put(Dst, 191);
#line 85 "codegen_linear.dasc"
if (!storeIntoSecond) {
  //| movaps xmm8, [r8]
  //| movaps xmm9, [r8+4*4]
  //| movaps xmm10, [r8+4*8]
  //| movaps xmm11, [r8+4*12]
  dasm_put(Dst, 199, 4*4, 4*8, 4*12);
#line 90 "codegen_linear.dasc"
} else {
  //| movaps xmm12, [r8]
  //| movaps xmm13, [r8+4*4]
  //| movaps xmm14, [r8+4*8]
  //| movaps xmm15, [r8+4*12]
  dasm_put(Dst, 219, 4*4, 4*8, 4*12);
#line 95 "codegen_linear.dasc"
}
}

static void store(dasm_State** Dst, char fromSecond) {
  //| mov r8, [rcx]
  //| add rcx, 16
  dasm_put(Dst, 191);
#line 101 "codegen_linear.dasc"
if (!fromSecond) {
  //| movaps [r8], xmm8
  //| movaps [r8+4*4], xmm9
  //| movaps [r8+4*8], xmm10
  //| movaps [r8+4*12], xmm11
  dasm_put(Dst, 239, 4*4, 4*8, 4*12);
#line 106 "codegen_linear.dasc"
} else {
  //| movaps [r8], xmm12
  //| movaps [r8+4*4], xmm13
  //| movaps [r8+4*8], xmm14
  //| movaps [r8+4*12], xmm15
  dasm_put(Dst, 259, 4*4, 4*8, 4*12);
#line 111 "codegen_linear.dasc"
}
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
  dasm_put(Dst, 279, _MM_SHUFFLE(0, 0, 0, 0), _MM_SHUFFLE(1, 1, 1, 1), _MM_SHUFFLE(2, 2, 2, 2), _MM_SHUFFLE(3, 3, 3, 3));
#line 135 "codegen_linear.dasc"
}

static void matrix_mul_matrix(dasm_State** Dst, char storeIntoSecond) {
  // requires:
  // left matrix in xmm8-xmm11 (aka. first)
  // right matrix in xmm12-xmm15 (aka. second)
  // will store in either first or second
  for (int i = 0; i < 4; i++) {
    // load column/vector from second
    if (i == 0) {
      //| movaps xmm0, xmm12
      dasm_put(Dst, 345);
#line 146 "codegen_linear.dasc"
    } else if (i == 1) {
      //| movaps xmm0, xmm13
      dasm_put(Dst, 350);
#line 148 "codegen_linear.dasc"
    } else if (i == 2) {
      //| movaps xmm0, xmm14
      dasm_put(Dst, 355);
#line 150 "codegen_linear.dasc"
    } else if (i == 3) {
      //| movaps xmm0, xmm15
      dasm_put(Dst, 360);
#line 152 "codegen_linear.dasc"
    }
    linearProduct(Dst);
    if (!storeIntoSecond) {
      // store into first
      if (i == 0) {
        //| movaps xmm5, xmm0
        dasm_put(Dst, 365);
#line 158 "codegen_linear.dasc"
      } else if (i == 1) {
        //| movaps xmm6, xmm0
        dasm_put(Dst, 369);
#line 160 "codegen_linear.dasc"
      } else if (i == 2) {
        //| movaps xmm7, xmm0
        dasm_put(Dst, 374);
#line 162 "codegen_linear.dasc"
      } else if (i == 3) {
        //| movaps xmm8, xmm5
        //| movaps xmm9, xmm6
        //| movaps xmm10, xmm7
        //| movaps xmm11, xmm0
        dasm_put(Dst, 379);
#line 167 "codegen_linear.dasc"
      }
    } else {
      // store into second
      if (i == 0) {
        //| movaps xmm12, xmm0
        dasm_put(Dst, 396);
#line 172 "codegen_linear.dasc"
      } else if (i == 1) {
        //| movaps xmm13, xmm0
        dasm_put(Dst, 401);
#line 174 "codegen_linear.dasc"
      } else if (i == 2) {
        //| movaps xmm14, xmm0
        dasm_put(Dst, 406);
#line 176 "codegen_linear.dasc"
      } else if (i == 3) {
        //| movaps xmm15, xmm0
        dasm_put(Dst, 412);
#line 178 "codegen_linear.dasc"
      }
    }
  }
}

static void matrix_rotateX(dasm_State** Dst, char storeIntoSecond) {
  // requires matrix to be in xmm8-xmm11 (aka. first)
  // will store in either first or second (xmm12-xmm15)
  //| movss xmm0, dword [rcx] // sin
  //| add rcx, 4
  //| movss xmm1, dword [rcx] // cos
  //| add rcx, 12
  //| shufps xmm0, xmm0, _MM_SHUFFLE(0, 0, 0, 0)
  //| shufps xmm1, xmm1, _MM_SHUFFLE(0, 0, 0, 0)
  //| mov r9, 0x80000000
  //| movd xmm2, r9
  //| shufps xmm2, xmm2, 0x0
  //| xorps xmm2, xmm0 // -sin
  //| movaps xmm3, xmm9
  //| mulps xmm3, xmm1 // m1X * rm11
  //| movaps xmm4, xmm10
  //| mulps xmm4, xmm0 // m2X * rm12
  //| addps xmm3, xmm4
  //| movaps xmm4, xmm9
  //| mulps xmm4, xmm2 // m1X * rm21
  //| movaps xmm5, xmm10
  //| mulps xmm5, xmm1 // m2X * rm22
  //| addps xmm4, xmm5
  dasm_put(Dst, 418, _MM_SHUFFLE(0, 0, 0, 0), _MM_SHUFFLE(0, 0, 0, 0));
#line 206 "codegen_linear.dasc"
  if (!storeIntoSecond) {
    //| movaps xmm9, xmm3
    //| movaps xmm10, xmm4
    dasm_put(Dst, 500);
#line 209 "codegen_linear.dasc"
  } else {
    //| movaps xmm12, xmm8
    //| movaps xmm13, xmm3
    //| movaps xmm14, xmm4
    //| movaps xmm15, xmm11
    dasm_put(Dst, 509);
#line 214 "codegen_linear.dasc"
  }
}

static void matrix_rotateY(dasm_State** Dst, char storeIntoSecond) {
  // requires matrix to be in xmm8-xmm11 (aka. first)
  // will store in either first or second (xmm12-xmm15)
  //| movss xmm0, dword [rcx] // sin
  //| add rcx, 4
  //| movss xmm1, dword [rcx] // cos
  //| add rcx, 12
  //| shufps xmm0, xmm0, _MM_SHUFFLE(0, 0, 0, 0)
  //| shufps xmm1, xmm1, _MM_SHUFFLE(0, 0, 0, 0)
  //| mov r9, 0x80000000
  //| movd xmm2, r9
  //| shufps xmm2, xmm2, 0x0
  //| xorps xmm2, xmm0 // -sin
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
  dasm_put(Dst, 529, _MM_SHUFFLE(0, 0, 0, 0), _MM_SHUFFLE(0, 0, 0, 0));
#line 240 "codegen_linear.dasc"
  if (!storeIntoSecond) {
    //| movaps xmm8, xmm3
    //| movaps xmm10, xmm4
    dasm_put(Dst, 611);
#line 243 "codegen_linear.dasc"
  } else {
    //| movaps xmm12, xmm3
    //| movaps xmm13, xmm9
    //| movaps xmm14, xmm4
    //| movaps xmm15, xmm11
    dasm_put(Dst, 620);
#line 248 "codegen_linear.dasc"
  }
}

static void matrix_rotateZ(dasm_State** Dst, char storeIntoSecond) {
  // requires matrix to be in xmm8-xmm11 (aka. first)
  // will store in either first or second (xmm12-xmm15)
  //| movss xmm0, dword [rcx] // sin
  //| add rcx, 4
  //| movss xmm1, dword [rcx] // cos
  //| add rcx, 12
  //| shufps xmm0, xmm0, _MM_SHUFFLE(0, 0, 0, 0)
  //| shufps xmm1, xmm1, _MM_SHUFFLE(0, 0, 0, 0)
  //| mov r9, 0x80000000
  //| movd xmm2, r9
  //| shufps xmm2, xmm2, 0x0
  //| xorps xmm2, xmm0 // -sin
  //| movaps xmm3, xmm8
  //| mulps xmm3, xmm1 // m0X * rm00
  //| movaps xmm4, xmm9
  //| mulps xmm4, xmm0 // m1X * rm01
  //| addps xmm3, xmm4
  //| movaps xmm4, xmm8
  //| mulps xmm4, xmm2 // m0X * rm10
  //| movaps xmm5, xmm9
  //| mulps xmm5, xmm1 // m1X * rm11
  //| addps xmm4, xmm5
  dasm_put(Dst, 640, _MM_SHUFFLE(0, 0, 0, 0), _MM_SHUFFLE(0, 0, 0, 0));
#line 274 "codegen_linear.dasc"
  if (!storeIntoSecond) {
    //| movaps xmm8, xmm3
    //| movaps xmm9, xmm4
    dasm_put(Dst, 722);
#line 277 "codegen_linear.dasc"
  } else {
    //| movaps xmm12, xmm3
    //| movaps xmm13, xmm4
    //| movaps xmm14, xmm10
    //| movaps xmm15, xmm11
    dasm_put(Dst, 731);
#line 282 "codegen_linear.dasc"
  }
}

static void matrix_identity(dasm_State** Dst) {
  // requires matrix to be in first (xmm8-xmm11)
  // will store in first
  //| mov r9, 0x3f800000 // int representation of 1.0f
  //| xorps xmm8, xmm8 // zero it out first
  //| movd xmm8, r9
  //| movaps xmm9, xmm8
  //| shufps xmm9, xmm9, _MM_SHUFFLE(2, 1, 0, 3)
  //| movaps xmm10, xmm9
  //| shufps xmm10, xmm10, _MM_SHUFFLE(2, 1, 0, 3)
  //| movaps xmm11, xmm10
  //| shufps xmm11, xmm11, _MM_SHUFFLE(2, 1, 0, 3)
  dasm_put(Dst, 751, _MM_SHUFFLE(2, 1, 0, 3), _MM_SHUFFLE(2, 1, 0, 3), _MM_SHUFFLE(2, 1, 0, 3));
#line 297 "codegen_linear.dasc"
}

static void matrix_scale(dasm_State** Dst, char storeIntoSecond) {
  // requires matrix to be in xmm8-xmm11 (aka. first)
  // will store in either first or second (xmm12-xmm15)
  // get scale factor (x, y, z, 1)
  //| movaps xmm0, [rcx]
  //| add rcx, 16
  //| movaps xmm1, xmm0
  //| shufps xmm1, xmm1, _MM_SHUFFLE(0, 0, 0, 0)
  dasm_put(Dst, 795, _MM_SHUFFLE(0, 0, 0, 0));
#line 307 "codegen_linear.dasc"
  if (!storeIntoSecond) {
    //| mulps xmm8, xmm1
    dasm_put(Dst, 810);
#line 309 "codegen_linear.dasc"
  } else {
    //| movaps xmm2, xmm8
    //| mulps xmm2, xmm1
    //| movaps xmm12, xmm2
    dasm_put(Dst, 815);
#line 313 "codegen_linear.dasc"
  }
  //| movaps xmm1, xmm0
  //| shufps xmm1, xmm1, _MM_SHUFFLE(1, 1, 1, 1)
  dasm_put(Dst, 802, _MM_SHUFFLE(1, 1, 1, 1));
#line 316 "codegen_linear.dasc"
  if (!storeIntoSecond) {
    //| mulps xmm9, xmm1
    dasm_put(Dst, 827);
#line 318 "codegen_linear.dasc"
  } else {
    //| movaps xmm2, xmm9
    //| mulps xmm2, xmm1
    //| movaps xmm13, xmm2
    dasm_put(Dst, 832);
#line 322 "codegen_linear.dasc"
  }
  //| movaps xmm1, xmm0
  //| shufps xmm1, xmm1, _MM_SHUFFLE(2, 2, 2, 2)
  dasm_put(Dst, 802, _MM_SHUFFLE(2, 2, 2, 2));
#line 325 "codegen_linear.dasc"
  if (!storeIntoSecond) {
    //| mulps xmm10, xmm1
    dasm_put(Dst, 845);
#line 327 "codegen_linear.dasc"
  } else {
    //| movaps xmm2, xmm10
    //| mulps xmm2, xmm1
    //| movaps xmm14, xmm2
    //| movaps xmm15, xmm11
    dasm_put(Dst, 850);
#line 332 "codegen_linear.dasc"
  }
}

static void matrix_translate(dasm_State** Dst, char storeIntoSecond) {
  // requires matrix to be in xmm8-xmm11 (aka. first)
  // will store in either first or second (xmm12-xmm15)
  // get translation (x, y, z, 0)
  //| movaps xmm0, [rcx]
  //| add rcx, 16
  //| movaps xmm1, xmm0
  //| shufps xmm1, xmm1, _MM_SHUFFLE(0, 0, 0, 0)
  //| movaps xmm2, xmm8
  //| mulps xmm2, xmm1 // m0X * x
  //| movaps xmm1, xmm0
  //| shufps xmm1, xmm1, _MM_SHUFFLE(1, 1, 1, 1)
  //| movaps xmm3, xmm9
  //| mulps xmm3, xmm1 // m1X * y
  //| addps xmm2, xmm3 // +
  //| movaps xmm1, xmm0
  //| shufps xmm1, xmm1, _MM_SHUFFLE(2, 2, 2, 2)
  //| movaps xmm3, xmm10
  //| mulps xmm3, xmm1 // m2X * z
  //| addps xmm2, xmm3 // +
  //| movaps xmm1, xmm11
  //| addps xmm2, xmm1 // + m3N
  dasm_put(Dst, 868, _MM_SHUFFLE(0, 0, 0, 0), _MM_SHUFFLE(1, 1, 1, 1), _MM_SHUFFLE(2, 2, 2, 2));
#line 357 "codegen_linear.dasc"
  if (!storeIntoSecond) {
    //| movaps xmm11, xmm2
    dasm_put(Dst, 931);
#line 359 "codegen_linear.dasc"
  } else {
    //| movaps xmm12, xmm8
    //| movaps xmm13, xmm9
    //| movaps xmm14, xmm10
    //| movaps xmm15, xmm2
    dasm_put(Dst, 936);
#line 364 "codegen_linear.dasc"
  }
}

static void vector_negate(dasm_State** Dst) {
  //| mov r8, [rcx]
  //| add rcx, 16
  //| movaps xmm0, [r8]
  //| mov r9, 0x80000000
  //| movd xmm1, r9
  //| shufps xmm1, xmm1, 0x0
  //| xorps xmm0, xmm1
  //| movaps [r8], xmm0
  dasm_put(Dst, 956);
#line 376 "codegen_linear.dasc"
}

static void prologue(dasm_State** Dst) {
  //| push rbp
  //| mov rbp, rsp
  //| sub rsp, 16*10
  dasm_put(Dst, 991, 16*10);
#line 382 "codegen_linear.dasc"
  // save all non-volatile registers that we use
  //| movdqu [rsp], xmm15
  //| movdqu [rsp+4*4], xmm14
  //| movdqu [rsp+4*4*2], xmm13
  //| movdqu [rsp+4*4*3], xmm12
  //| movdqu [rsp+4*4*4], xmm11
  //| movdqu [rsp+4*4*5], xmm10
  //| movdqu [rsp+4*4*6], xmm9
  //| movdqu [rsp+4*4*7], xmm8
  //| movdqu [rsp+4*4*8], xmm7
  //| movdqu [rsp+4*4*9], xmm6
  dasm_put(Dst, 1001, 4*4, 4*4*2, 4*4*3, 4*4*4, 4*4*5, 4*4*6, 4*4*7, 4*4*8, 4*4*9);
#line 393 "codegen_linear.dasc"
}

static void epilogue(dasm_State** Dst) {
  //| movdqu xmm15, [rsp]
  //| movdqu xmm14, [rsp+4*4]
  //| movdqu xmm13, [rsp+4*4*2]
  //| movdqu xmm12, [rsp+4*4*3]
  //| movdqu xmm11, [rsp+4*4*4]
  //| movdqu xmm10, [rsp+4*4*5]
  //| movdqu xmm9, [rsp+4*4*6]
  //| movdqu xmm8, [rsp+4*4*7]
  //| movdqu xmm7, [rsp+4*4*8]
  //| movdqu xmm6, [rsp+4*4*9]
  //| mov rsp, rbp
  //| pop rbp
  //| ret
  dasm_put(Dst, 1088, 4*4, 4*4*2, 4*4*3, 4*4*4, 4*4*5, 4*4*6, 4*4*7, 4*4*8, 4*4*9);
#line 409 "codegen_linear.dasc"
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
    char second = (opcode & OPCODE_MASK_SECOND) == OPCODE_MASK_SECOND;
    opcode &= 0x7F;
    switch (opcode) {
    case OPCODE_MATRIX_MUL_MATRIX:
      matrix_mul_matrix(&state, second); break;
    case OPCODE_MATRIX_TRANSPOSE:
      matrix_transpose(&state, second); break;
    case OPCODE_MATRIX_IDENTITY:
      matrix_identity(&state); break;
    case OPCODE_MATRIX_ROTATEX:
      matrix_rotateX(&state, second); break;
    case OPCODE_MATRIX_ROTATEY:
      matrix_rotateY(&state, second); break;
    case OPCODE_MATRIX_ROTATEZ:
      matrix_rotateZ(&state, second); break;
    case OPCODE_MATRIX_TRANSLATE:
      matrix_translate(&state, second); break;
    case OPCODE_MATRIX_SCALE:
      matrix_scale(&state, second); break;
    case OPCODE_VECTOR_NEGATE:
      vector_negate(&state); break;
    case OPCODE_STORE:
      store(&state, second); break;
    case OPCODE_LOAD:
      load(&state, second); break;
    case OPCODE_COPY:
      copy(&state, second); break;
    case OPCODE_COPY_MEM:
      copy_mem(&state); break;
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
