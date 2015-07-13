#include <jni.h>
#include <malloc.h>
#include <string.h>
#include <Windows.h>

#include "codegen.h"

typedef struct vector_ {
  float elems[4];
  sequence_func_t jittedFunc;
} vector;

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
 * Class:     org_joml_NativeVector4f
 * Method:    callSequence
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_org_joml_NativeVector4f_callSequence
  (JNIEnv * env, jclass clazz, jlong id) {
  vector* vec = (vector*) (intptr_t) id;
  return (jint) vec->jittedFunc(vec->elems);
}

/*
 * Class:     org_joml_NativeVector4f
 * Method:    get
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_org_joml_NativeVector4f_get
  (JNIEnv * env, jclass clazz, jlong id, jlong bufferAddr) {
  memcpy((void*) (intptr_t) bufferAddr, (const void*) (intptr_t) id, sizeof(float[4]));
}

/*
 * Class:     org_joml_NativeVector4f
 * Method:    set
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_org_joml_NativeVector4f_set
  (JNIEnv * env, jclass clazz, jlong id, jlong bufferAddr) {
  memcpy((void*) (intptr_t) id, (const void*) (intptr_t) bufferAddr, sizeof(float[4]));
}

/*
 * Class:     org_joml_NativeVector4f
 * Method:    jit
 * Signature: (JJI)V
 */
JNIEXPORT void JNICALL Java_org_joml_NativeVector4f_jit
  (JNIEnv * env, jclass clazz, jlong id, jlong opsAddr, int opsLen) {
  vector* v = (vector*) (intptr_t) id;
  const char* opcodes = (const char*) (intptr_t) opsAddr;
  sequence_func_t func = codegen();
  v->jittedFunc = func;
}

/*
 * Class:     org_joml_NativeVector4f
 * Method:    addressOf
 * Signature: (Ljava/nio/Buffer;)J
 */
JNIEXPORT jlong JNICALL Java_org_joml_NativeVector4f_addressOf
  (JNIEnv * env, jclass clazz, jobject buffer) {
  return (jlong) (intptr_t) (*env)->GetDirectBufferAddress(env, buffer);  
}
