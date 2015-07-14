#include <jni.h>
#include <malloc.h>
#include <string.h>
#include <Windows.h>

#include "codegen.h"

typedef struct vector_ {
  float elems[4];
  sequence_func_t jittedFunc;
} vector;

typedef struct matrix_ {
  float elems[16];
  mul_matrix_vector_func_t mul_matrix_vector;
} matrix;

/*
 * Class:     org_joml_NativeVector4f
 * Method:    alloc
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_joml_NativeVector4f_alloc
  (JNIEnv * env, jclass clazz) {
  void* ptr = _aligned_malloc(sizeof(vector), sizeof(float[4]));
  memset(ptr, 0, sizeof(vector));
  return (jlong) (intptr_t) ptr;
}

/*
 * Class:     org_joml_NativeMatrix4f
 * Method:    alloc
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_joml_NativeMatrix4f_alloc
  (JNIEnv * env, jclass clazz) {
  void* ptr = _aligned_malloc(sizeof(matrix), sizeof(float[16]));
  memset(ptr, 0, sizeof(matrix));
  return (jlong) (intptr_t) ptr;
}

/*
 * Class:     org_joml_NativeMatrix4f
 * Method:    mulVector
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_org_joml_NativeMatrix4f_mulVector
  (JNIEnv * env, jclass clazz, jlong matrixId, jlong vectorId) {
  matrix* mat = (matrix*) (intptr_t) matrixId;
  vector* vec = (vector*) (intptr_t) vectorId;
  mat->mul_matrix_vector(mat->elems, vec->elems);
}

JNIEXPORT void JNICALL Java_org_joml_NativeVector4f_get
  (JNIEnv * env, jclass clazz, jlong id, jlong bufferAddr) {
  memcpy((void*) (intptr_t) bufferAddr, (const void*) (intptr_t) id, sizeof(float[4]));
}
JNIEXPORT void JNICALL Java_org_joml_NativeVector4f_set
  (JNIEnv * env, jclass clazz, jlong id, jlong bufferAddr) {
  memcpy((void*) (intptr_t) id, (const void*) (intptr_t) bufferAddr, sizeof(float[4]));
}
JNIEXPORT void JNICALL Java_org_joml_NativeMatrix4f_get
  (JNIEnv * env, jclass clazz, jlong id, jlong bufferAddr) {
  memcpy((void*) (intptr_t) bufferAddr, (const void*) (intptr_t) id, sizeof(float[16]));
}
JNIEXPORT void JNICALL Java_org_joml_NativeMatrix4f_set
  (JNIEnv * env, jclass clazz, jlong id, jlong bufferAddr) {
  memcpy((void*) (intptr_t) id, (const void*) (intptr_t) bufferAddr, sizeof(float[16]));
}

/*
 * Class:     org_joml_NativeMatrix4f
 * Method:    jit
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_org_joml_NativeMatrix4f_jit
  (JNIEnv * env, jclass clazz, jlong id) {
  matrix* mat = (matrix*) (intptr_t) id;
  mul_matrix_vector_func_t func = codegen();
  mat->mul_matrix_vector = func;
}

JNIEXPORT jlong JNICALL Java_org_joml_NativeVector4f_addressOf
  (JNIEnv * env, jclass clazz, jobject buffer) {
  return (jlong) (intptr_t) (*env)->GetDirectBufferAddress(env, buffer);  
}

JNIEXPORT jlong JNICALL Java_org_joml_NativeMatrix4f_addressOf
  (JNIEnv * env, jclass clazz, jobject buffer) {
  return (jlong) (intptr_t) (*env)->GetDirectBufferAddress(env, buffer);  
}
