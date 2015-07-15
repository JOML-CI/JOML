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
  GLOB_rotateY,
  GLOB_rotateX,
  GLOB_rotateZ,
  GLOB_vector_negate,
  GLOB_matrix_rotate_quaternion,
  GLOB_matrix_get,
  GLOB_matrix_identity,
  GLOB__MAX
};
#line 11 "codegen.dasc"
//|.actionlist actionlist
static const unsigned char actionlist[961] = {
  248,10,255,76,139,1,72,131,193,8,255,76,139,9,72,131,193,8,255,65,15,40,0,
  15,40,208,65,15,40,136,233,15,198,193,136,15,198,209,221,65,15,40,152,233,
  15,40,252,235,65,15,40,176,233,15,198,222,136,15,198,252,238,221,15,40,200,
  15,198,195,136,15,40,226,15,198,213,136,15,198,203,221,15,198,230,221,255,
  65,15,41,1,65,15,41,145,233,65,15,41,137,233,65,15,41,161,233,255,15,40,200,
  15,198,201,235,15,40,208,15,198,210,235,15,40,216,15,198,219,235,15,40,224,
  15,198,228,235,65,15,40,0,15,89,193,65,15,40,136,233,15,89,202,65,15,40,144,
  233,15,89,211,65,15,40,152,233,15,89,220,15,88,193,15,88,211,15,88,194,255,
  248,11,255,65,15,40,129,233,255,15,40,232,255,15,40,252,240,255,15,40,252,
  248,255,76,139,1,72,131,193,16,65,15,41,40,65,15,41,176,233,65,15,41,184,
  233,65,15,41,128,233,255,248,12,255,76,139,1,72,131,193,8,65,15,40,0,255,
  15,40,200,15,198,201,235,15,40,208,15,198,210,235,15,40,216,15,198,219,235,
  15,40,224,15,198,228,235,255,65,15,40,1,15,89,193,255,65,15,40,137,233,15,
  89,202,255,65,15,40,145,233,15,89,211,255,65,15,40,153,233,15,89,220,255,
  76,139,1,72,131,193,16,65,15,41,0,255,248,13,255,76,139,1,72,131,193,16,255,
  15,40,1,72,131,193,16,255,15,40,9,72,131,193,16,255,15,40,17,72,131,193,16,
  255,15,40,217,15,88,219,15,40,227,15,89,225,15,40,252,233,15,198,252,237,
  235,15,89,252,235,15,40,252,241,15,40,252,251,15,198,252,246,235,15,198,252,
  255,235,15,89,252,247,255,248,14,76,139,1,72,131,193,8,252,243,15,16,1,72,
  131,193,4,252,243,15,16,9,72,131,193,4,15,198,192,235,15,198,201,235,15,87,
  210,15,92,208,65,15,40,24,15,89,217,65,15,40,160,233,15,89,226,15,88,220,
  65,15,40,32,15,89,224,65,15,40,168,233,15,89,252,233,15,88,229,255,76,139,
  9,72,131,193,16,65,15,41,25,65,15,41,161,233,65,15,40,128,233,65,15,41,129,
  233,65,15,40,136,233,65,15,41,137,233,255,248,15,76,139,1,72,131,193,8,252,
  243,15,16,1,72,131,193,4,252,243,15,16,9,72,131,193,4,15,198,192,235,15,198,
  201,235,15,87,210,15,92,208,65,15,40,152,233,15,89,217,65,15,40,160,233,15,
  89,224,15,88,220,65,15,40,160,233,15,89,226,65,15,40,168,233,15,89,252,233,
  15,88,229,255,76,139,9,72,131,193,16,65,15,41,153,233,65,15,41,161,233,65,
  15,40,0,65,15,41,1,65,15,40,136,233,65,15,41,137,233,255,248,16,76,139,1,
  72,131,193,8,252,243,15,16,1,72,131,193,4,252,243,15,16,9,72,131,193,4,15,
  198,192,235,15,198,201,235,15,87,210,15,92,208,65,15,40,24,15,89,217,65,15,
  40,160,233,15,89,224,15,88,220,65,15,40,32,15,89,226,65,15,40,168,233,15,
  89,252,233,15,88,229,255,248,17,255,76,139,1,72,131,193,8,65,15,40,0,15,87,
  201,15,92,200,255,76,139,1,72,131,193,8,65,15,41,8,255,248,18,255,15,40,200,
  15,88,201,15,40,209,15,89,208,15,40,216,15,198,219,235,15,89,217,15,40,224,
  15,40,252,233,15,198,228,235,15,198,252,237,235,15,89,229,255,248,19,255,
  65,15,40,0,65,15,41,1,65,15,40,136,233,65,15,41,137,233,65,15,40,128,233,
  65,15,41,129,233,65,15,40,136,233,65,15,41,137,233,255,248,20,255,76,139,
  1,72,131,193,16,73,199,193,0,0,128,63,102,73,15,110,193,15,40,200,15,198,
  201,147,15,40,209,15,198,210,147,15,40,218,15,198,219,147,65,15,41,0,65,15,
  41,136,233,65,15,41,144,233,65,15,41,152,233,255,72,199,194,247,255,252,233,
  244,11,255,252,255,226,255,252,233,244,12,255,252,233,244,10,255,252,233,
  244,13,255,252,233,244,16,255,252,233,244,17,255,252,233,244,18,255,252,233,
  244,19,255,252,233,244,20,255,252,233,244,15,255,252,233,244,14,255,249,255,
  195,255
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
  //| add rcx, 8
  dasm_put(Dst, 3);
#line 25 "codegen.dasc"
  // obtain dest matrix
  //| mov r9, [rcx]
  //| add rcx, 8
  dasm_put(Dst, 11);
#line 28 "codegen.dasc"
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
  dasm_put(Dst, 19, 4*4, 8*4, 12*4);
#line 45 "codegen.dasc"
  // store back into dest matrix at [r9]
  //| movaps [r9], xmm0
  //| movaps [r9+4*4], xmm2
  //| movaps [r9+8*4], xmm1
  //| movaps [r9+12*4], xmm4
  dasm_put(Dst, 85, 4*4, 8*4, 12*4);
#line 50 "codegen.dasc"
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
  dasm_put(Dst, 105, _MM_SHUFFLE(0, 0, 0, 0), _MM_SHUFFLE(1, 1, 1, 1), _MM_SHUFFLE(2, 2, 2, 2), _MM_SHUFFLE(3, 3, 3, 3), 4*4, 4*8, 4*12);
#line 73 "codegen.dasc"
  // result is in xmm0
}

static void mul_matrix_matrix(dasm_State** Dst) {
  //|->mul_matrix_matrix:
  dasm_put(Dst, 174);
#line 78 "codegen.dasc"
  // obtain first matrix
  //| mov r8, [rcx]
  //| add rcx, 8
  dasm_put(Dst, 3);
#line 81 "codegen.dasc"
  // obtain second matrix
  //| mov r9, [rcx]
  //| add rcx, 8
  dasm_put(Dst, 11);
#line 84 "codegen.dasc"
  for (int i = 0; i < 4; i++) {
    // get i-th column of second matrix
    //| movaps xmm0, [r9+4*4*i]
    dasm_put(Dst, 177, 4*4*i);
#line 87 "codegen.dasc"
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
      dasm_put(Dst, 183);
#line 98 "codegen.dasc"
    } else if (i == 1) {
      //| movaps xmm6, xmm0
      dasm_put(Dst, 187);
#line 100 "codegen.dasc"
    } else if (i == 2) {
      //| movaps xmm7, xmm0
      dasm_put(Dst, 192);
#line 102 "codegen.dasc"
    } else if (i == 3) {
      // obtain dest matrix
      //| mov r8, [rcx]
      //| add rcx, 16
      //| movaps [r8], xmm5
      //| movaps [r8+4*4], xmm6
      //| movaps [r8+8*4], xmm7
      //| movaps [r8+12*4], xmm0
      dasm_put(Dst, 197, 4*4, 8*4, 12*4);
#line 110 "codegen.dasc"
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
  dasm_put(Dst, 224);
#line 122 "codegen.dasc"
  // obtain vector
  //| mov r8, [rcx]
  //| add rcx, 8
  //| movaps xmm0, [r8]
  dasm_put(Dst, 227);
#line 126 "codegen.dasc"

  // create xmm1-xmm4 containing the vectors to be multiplied
  //| movaps xmm1, xmm0
  //| shufps xmm1, xmm1, _MM_SHUFFLE(0, 0, 0, 0)
  //| movaps xmm2, xmm0
  //| shufps xmm2, xmm2, _MM_SHUFFLE(1, 1, 1, 1)
  //| movaps xmm3, xmm0
  //| shufps xmm3, xmm3, _MM_SHUFFLE(2, 2, 2, 2)
  //| movaps xmm4, xmm0
  //| shufps xmm4, xmm4, _MM_SHUFFLE(3, 3, 3, 3)
  dasm_put(Dst, 239, _MM_SHUFFLE(0, 0, 0, 0), _MM_SHUFFLE(1, 1, 1, 1), _MM_SHUFFLE(2, 2, 2, 2), _MM_SHUFFLE(3, 3, 3, 3));
#line 136 "codegen.dasc"

  // obtain matrix
  //| mov r9, [rcx]
  //| add rcx, 8
  dasm_put(Dst, 11);
#line 140 "codegen.dasc"

  // load first matrix column and multiply with xmm1
  //| movaps xmm0, [r9]
  //| mulps xmm0, xmm1
  dasm_put(Dst, 268);
#line 144 "codegen.dasc"
  // load second matrix column and multiply with xmm2
  //| movaps xmm1, [r9+4*4]
  //| mulps xmm1, xmm2
  dasm_put(Dst, 276, 4*4);
#line 147 "codegen.dasc"
  // load third matrix column and multiply with xmm3
  //| movaps xmm2, [r9+4*8]
  //| mulps xmm2, xmm3
  dasm_put(Dst, 285, 4*8);
#line 150 "codegen.dasc"
  // load fourth matrix column and multiply with xmm4
  //| movaps xmm3, [r9+4*12]
  //| mulps xmm3, xmm4
  dasm_put(Dst, 294, 4*12);
#line 153 "codegen.dasc"
  // now the results are in xmm0-xmm3 and need to be added
  //| addps xmm0, xmm1
  //| addps xmm2, xmm3
  //| addps xmm0, xmm2
  dasm_put(Dst, 164);
#line 157 "codegen.dasc"

  // write final result in xmm0 to dest vector
  //| mov r8, [rcx]
  //| add rcx, 16
  //| movaps [r8], xmm0
  dasm_put(Dst, 303);
#line 162 "codegen.dasc"
}

static void translation_rotate_scale(dasm_State** Dst) {
  //|->translation_rotate_scale:
  dasm_put(Dst, 315);
#line 166 "codegen.dasc"
  // obtain matrix
  //| mov r8, [rcx]
  //| add rcx, 16
  dasm_put(Dst, 318);
#line 169 "codegen.dasc"
  // store translation in xmm0
  //| movaps xmm0, [rcx]
  //| add rcx, 16
  dasm_put(Dst, 326);
#line 172 "codegen.dasc"
  // store quaternion in xmm1
  //| movaps xmm1, [rcx]
  //| add rcx, 16
  dasm_put(Dst, 334);
#line 175 "codegen.dasc"
  // store scale in xmm2
  //| movaps xmm2, [rcx]
  //| add rcx, 16
  dasm_put(Dst, 342);
#line 178 "codegen.dasc"

  // TODO: Here we need to actually do what Matrix4f.translationRotateScale() does,
  //       just only better.
  //| movaps xmm3, xmm1
  //| addps xmm3, xmm3 // dqX
  //| movaps xmm4, xmm3
  //| mulps xmm4, xmm1 // qNN
  //| movaps xmm5, xmm1
  //| shufps xmm5, xmm5, _MM_SHUFFLE(1, 2, 3, 0)
  //| mulps xmm5, xmm3 // q0N
  //| movaps xmm6, xmm1
  //| movaps xmm7, xmm3
  //| shufps xmm6, xmm6, _MM_SHUFFLE(2, 3, 3, 2)
  //| shufps xmm7, xmm7, _MM_SHUFFLE(1, 1, 2, 2)
  //| mulps xmm6, xmm7 // q12-23
  dasm_put(Dst, 350, _MM_SHUFFLE(1, 2, 3, 0), _MM_SHUFFLE(2, 3, 3, 2), _MM_SHUFFLE(1, 1, 2, 2));
#line 193 "codegen.dasc"
  // xmm1, xmm3, xmm7 are free here!
  // the above covers the computation of the qXX floats
  // from the Java sources.
  // now comes the computation of the final matrix elements
  /*
        m00 = sx - (q11 + q22) * sx;
        m01 = 0  + (q01 + q23) * sx;
        m02 = 0  + (q02 - q13) * sx;
        m03 = 0  + (q02 + q13) * 0;
  */
  // TODO: store result in dest matrix
  //| mov r8, [rcx]
  //| add rcx, 16
  dasm_put(Dst, 318);
#line 206 "codegen.dasc"
}

static void rotateY(dasm_State** Dst) {
/* Java code:
        float rm00 = cos;
        float rm02 = -sin;
        float rm20 = sin;
        float rm22 = cos;
        float nm00 = m00 * rm00 + m20 * rm02;
        float nm01 = m01 * rm00 + m21 * rm02;
        float nm02 = m02 * rm00 + m22 * rm02;
        float nm03 = m03 * rm00 + m23 * rm02;
        dest.m20 = m00 * rm20 + m20 * rm22;
        dest.m21 = m01 * rm20 + m21 * rm22;
        dest.m22 = m02 * rm20 + m22 * rm22;
        dest.m23 = m03 * rm20 + m23 * rm22;
*/
  //|->rotateY:
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
  //| movaps xmm4, [r8+4*8]
  //| mulps xmm4, xmm2 // m2X * rm02
  //| addps xmm3, xmm4
  //| movaps xmm4, [r8]
  //| mulps xmm4, xmm0 // m0X * rm20
  //| movaps xmm5, [r8+4*8]
  //| mulps xmm5, xmm1 // m2X * rm22
  //| addps xmm4, xmm5
  dasm_put(Dst, 398, _MM_SHUFFLE(0, 0, 0, 0), _MM_SHUFFLE(0, 0, 0, 0), 4*8, 4*8);
#line 244 "codegen.dasc"
  // obtain dest matrix
  //| mov r9, [rcx]
  //| add rcx, 16
  //| movaps [r9], xmm3
  //| movaps [r9+4*8], xmm4
  //| movaps xmm0, [r8+4*4]
  //| movaps [r9+4*4], xmm0
  //| movaps xmm1, [r8+4*12]
  //| movaps [r9+4*12], xmm1
  dasm_put(Dst, 477, 4*8, 4*4, 4*4, 4*12, 4*12);
#line 253 "codegen.dasc"
}

static void rotateX(dasm_State** Dst) {
/* Java code:
        float rm11 = cos;
        float rm12 = sin;
        float rm21 = -sin;
        float rm22 = cos;
        float nm10 = m10 * rm11 + m20 * rm12;
        float nm11 = m11 * rm11 + m21 * rm12;
        float nm12 = m12 * rm11 + m22 * rm12;
        float nm13 = m13 * rm11 + m23 * rm12;
        dest.m20 = m10 * rm21 + m20 * rm22;
        dest.m21 = m11 * rm21 + m21 * rm22;
        dest.m22 = m12 * rm21 + m22 * rm22;
        dest.m23 = m13 * rm21 + m23 * rm22;
*/
  //|->rotateX:
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
  //| movaps xmm3, [r8+4*4]
  //| mulps xmm3, xmm1 // m1X * rm11
  //| movaps xmm4, [r8+4*8]
  //| mulps xmm4, xmm0 // m2X * rm12
  //| addps xmm3, xmm4
  //| movaps xmm4, [r8+4*4]
  //| mulps xmm4, xmm2 // m1X * rm21
  //| movaps xmm5, [r8+4*8]
  //| mulps xmm5, xmm1 // m2X * rm22
  //| addps xmm4, xmm5
  dasm_put(Dst, 514, _MM_SHUFFLE(0, 0, 0, 0), _MM_SHUFFLE(0, 0, 0, 0), 4*4, 4*8, 4*4, 4*8);
#line 291 "codegen.dasc"
  // obtain dest matrix
  //| mov r9, [rcx]
  //| add rcx, 16
  //| movaps [r9+4*4], xmm3
  //| movaps [r9+4*8], xmm4
  //| movaps xmm0, [r8]
  //| movaps [r9], xmm0
  //| movaps xmm1, [r8+4*12]
  //| movaps [r9+4*12], xmm1
  dasm_put(Dst, 595, 4*4, 4*8, 4*12, 4*12);
#line 300 "codegen.dasc"
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
  dasm_put(Dst, 631, _MM_SHUFFLE(0, 0, 0, 0), _MM_SHUFFLE(0, 0, 0, 0), 4*4, 4*4);
#line 324 "codegen.dasc"
  // obtain dest matrix
  //| mov r9, [rcx]
  //| add rcx, 16
  //| movaps [r9], xmm3
  //| movaps [r9+4*4], xmm4
  //| movaps xmm0, [r8+4*8]
  //| movaps [r9+4*8], xmm0
  //| movaps xmm1, [r8+4*12]
  //| movaps [r9+4*12], xmm1
  dasm_put(Dst, 477, 4*4, 4*8, 4*8, 4*12, 4*12);
#line 333 "codegen.dasc"
}

static void vector_negate(dasm_State** Dst) {
  //|->vector_negate:
  dasm_put(Dst, 710);
#line 337 "codegen.dasc"
  // obtain vector
  //| mov r8, [rcx]
  //| add rcx, 8
  //| movaps xmm0, [r8]
  //| xorps xmm1, xmm1 // make it zero
  //| subps xmm1, xmm0 // xmm1 = xmm1 - xmm0
  dasm_put(Dst, 713);
#line 343 "codegen.dasc"
  // obtain dest vector
  //| mov r8, [rcx]
  //| add rcx, 8
  //| movaps [r8], xmm1
  dasm_put(Dst, 731);
#line 347 "codegen.dasc"
}

static void matrix_rotate_quaternion(dasm_State** Dst) {
  //|->matrix_rotate_quaternion:
  dasm_put(Dst, 743);
#line 351 "codegen.dasc"
  // obtain matrix
  //| mov r8, [rcx]
  //| add rcx, 16
  dasm_put(Dst, 318);
#line 354 "codegen.dasc"
  // obtain quaternion (x,y,z,w)
  //| movaps xmm0, [rcx]
  //| add rcx, 16
  dasm_put(Dst, 326);
#line 357 "codegen.dasc"
  // compute the qXX values
  //| movaps xmm1, xmm0
  //| addps xmm1, xmm1 // dqX
  //| movaps xmm2, xmm1
  //| mulps xmm2, xmm0 // qNN
  //| movaps xmm3, xmm0
  //| shufps xmm3, xmm3, _MM_SHUFFLE(1, 2, 3, 0)
  //| mulps xmm3, xmm1 // q0N
  //| movaps xmm4, xmm0
  //| movaps xmm5, xmm1
  //| shufps xmm4, xmm4, _MM_SHUFFLE(2, 3, 3, 2)
  //| shufps xmm5, xmm5, _MM_SHUFFLE(1, 1, 2, 2)
  //| mulps xmm4, xmm5 // q12-23
  dasm_put(Dst, 746, _MM_SHUFFLE(1, 2, 3, 0), _MM_SHUFFLE(2, 3, 3, 2), _MM_SHUFFLE(1, 1, 2, 2));
#line 370 "codegen.dasc"
  // xmm0, xmm1, xmm6, xmm7 are free here
  // compute final matrix elements
  
  /*
        float rm00 = 1.0f - q11 - q22;
        float rm01 = 0.0f + q01 + q23;
        float rm02 = 0.0f + q02 - q13;
        float rm0_ = ---;
  */
  // puh...
}

static void matrix_get(dasm_State** Dst) {
  //|->matrix_get:
  dasm_put(Dst, 788);
#line 384 "codegen.dasc"
  // obtain matrix address
  //| mov r8, [rcx]
  //| add rcx, 8
  dasm_put(Dst, 3);
#line 387 "codegen.dasc"
  // obtain destination buffer address
  //| mov r9, [rcx]
  //| add rcx, 8
  dasm_put(Dst, 11);
#line 390 "codegen.dasc"
  // use movaps to copy the 4 columns
  //| movaps xmm0, [r8]
  //| movaps [r9], xmm0
  //| movaps xmm1, [r8+4*4]
  //| movaps [r9+4*4], xmm1
  //| movaps xmm0, [r8+4*8]
  //| movaps [r9+4*8], xmm0
  //| movaps xmm1, [r8+4*12]
  //| movaps [r9+4*12], xmm1
  dasm_put(Dst, 791, 4*4, 4*4, 4*8, 4*8, 4*12, 4*12);
#line 399 "codegen.dasc"
}

static void matrix_identity(dasm_State** Dst) {
  //|->matrix_identity:
  dasm_put(Dst, 830);
#line 403 "codegen.dasc"
  // obtain matrix address
  //| mov r8, [rcx]
  //| add rcx, 16
  //| mov r9, 0x3f800000 // int representation of 1.0f
  //| movd xmm0, r9
  //| movaps xmm1, xmm0
  //| shufps xmm1, xmm1, 0x93
  //| movaps xmm2, xmm1
  //| shufps xmm2, xmm2, 0x93
  //| movaps xmm3, xmm2
  //| shufps xmm3, xmm3, 0x93
  //| movaps [r8], xmm0
  //| movaps [r8+4*4], xmm1
  //| movaps [r8+4*8], xmm2
  //| movaps [r8+4*12], xmm3
  dasm_put(Dst, 833, 4*4, 4*8, 4*12);
#line 418 "codegen.dasc"
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
  char op_generated[] =
  {0, 0, 0, 0, 0,
   0, 0, 0, 0, 0, 
   0, 0};
  dasm_init(&state, DASM_MAXSECTION);
  dasm_setupglobal(&state, global_labels, GLOB__MAX);
  dasm_setup(&state, actionlist);
  dasm_growpc(&state, opcodesLength);
  for (int i = 0; i < opcodesLength; i++) {
    //| mov rdx, =>next_pc
    dasm_put(Dst, 893, next_pc);
#line 439 "codegen.dasc"
    switch (opcodes[i]) {
    case 0x01: // OPCODE_MATRIX_MUL_MATRIX
      //| jmp ->mul_matrix_matrix
      dasm_put(Dst, 898);
#line 442 "codegen.dasc"
      if (!op_generated[0]) {
        mul_matrix_matrix(&state);
        //| jmp rdx
        dasm_put(Dst, 903);
#line 445 "codegen.dasc"
        op_generated[0] = 1;
      }
      break;
    case 0x02: // OPCODE_MATRIX_MUL_VECTOR
      //| jmp ->mul_matrix_vector
      dasm_put(Dst, 907);
#line 450 "codegen.dasc"
      if (!op_generated[1]) {
        mul_matrix_vector(&state);
        //| jmp rdx
        dasm_put(Dst, 903);
#line 453 "codegen.dasc"
        op_generated[1] = 1;
      }
      break;
    case 0x03: // OPCODE_MATRIX_TRANSPOSE
      //| jmp ->matrix_transpose
      dasm_put(Dst, 912);
#line 458 "codegen.dasc"
      if (!op_generated[2]) {
        matrix_transpose(&state);
        //| jmp rdx
        dasm_put(Dst, 903);
#line 461 "codegen.dasc"
        op_generated[2] = 1;
      }
      break;
    case 0x04: // OPCODE_MATRIX_INVERT
      // Not yet implemented!
      break;
    case 0x05: // OPCODE_TRANSLATION_ROTATE_SCALE
      //| jmp ->translation_rotate_scale
      dasm_put(Dst, 917);
#line 469 "codegen.dasc"
      if (!op_generated[4]) {
        translation_rotate_scale(&state);
        //| jmp rdx
        dasm_put(Dst, 903);
#line 472 "codegen.dasc"
        op_generated[4] = 1;
      }
      break;
    case 0x06: // OPCODE_ROTATEZ
      //| jmp ->rotateZ
      dasm_put(Dst, 922);
#line 477 "codegen.dasc"
      if (!op_generated[5]) {
        rotateZ(&state);
        //| jmp rdx
        dasm_put(Dst, 903);
#line 480 "codegen.dasc"
        op_generated[5] = 1;
      }
      break;
    case 0x07: // OPCODE_VECTOR_NEGATE
      //| jmp ->vector_negate
      dasm_put(Dst, 927);
#line 485 "codegen.dasc"
      if (!op_generated[6]) {
        vector_negate(&state);
        //| jmp rdx
        dasm_put(Dst, 903);
#line 488 "codegen.dasc"
        op_generated[6] = 1;
      }
      break;
    case 0x08: // OPCODE_MATRIX_ROTATE_QUATERNION
      //| jmp ->matrix_rotate_quaternion
      dasm_put(Dst, 932);
#line 493 "codegen.dasc"
      if (!op_generated[7]) {
        matrix_rotate_quaternion(&state);
        //| jmp rdx
        dasm_put(Dst, 903);
#line 496 "codegen.dasc"
        op_generated[7] = 1;
      }
      break;
    case 0x09: // OPCODE_MATRIX_GET
      //| jmp ->matrix_get
      dasm_put(Dst, 937);
#line 501 "codegen.dasc"
      if (!op_generated[8]) {
        matrix_get(&state);
        //| jmp rdx
        dasm_put(Dst, 903);
#line 504 "codegen.dasc"
        op_generated[8] = 1;
      }
      break;
    case 0x0A: // OPCODE_MATRIX_IDENTITY
      //| jmp ->matrix_identity
      dasm_put(Dst, 942);
#line 509 "codegen.dasc"
      if (!op_generated[9]) {
        matrix_identity(&state);
        //| jmp rdx
        dasm_put(Dst, 903);
#line 512 "codegen.dasc"
        op_generated[9] = 1;
      }
      break;
    case 0x0B: // OPCODE_ROTATEX
      //| jmp ->rotateX
      dasm_put(Dst, 947);
#line 517 "codegen.dasc"
      if (!op_generated[10]) {
        rotateX(&state);
        //| jmp rdx
        dasm_put(Dst, 903);
#line 520 "codegen.dasc"
        op_generated[10] = 1;
      }
      break;
    case 0x0C: // OPCODE_ROTATEY
      //| jmp ->rotateY
      dasm_put(Dst, 952);
#line 525 "codegen.dasc"
      if (!op_generated[11]) {
        rotateY(&state);
        //| jmp rdx
        dasm_put(Dst, 903);
#line 528 "codegen.dasc"
        op_generated[11] = 1;
      }
      break;
    default:
      break;
    }
    //|=>next_pc:
    dasm_put(Dst, 957, next_pc);
#line 535 "codegen.dasc"
    next_pc++;
  }
  //| ret
  dasm_put(Dst, 959);
#line 538 "codegen.dasc"
  status = dasm_link(&state, &code_size);
  code = VirtualAlloc(0, code_size, MEM_RESERVE | MEM_COMMIT, PAGE_READWRITE);
  status = dasm_encode(&state, code);
  VirtualProtect(code, code_size, PAGE_EXECUTE, &dwOld);
  FlushInstructionCache(GetCurrentProcess(), code, code_size);
  dasm_free(&state);
  return (batch_func_t) code;
}
