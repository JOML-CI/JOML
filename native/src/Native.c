#include <jni.h>
#include <stdint.h>
#include "codegen.h"

JNIEXPORT jlong JNICALL Java_org_joml_Native_jit
  (JNIEnv * env, jclass clazz, jlong opcodesAddr, jint opcodesLength, jlong codeSizeAddr) {
  const char* opcodes = (const char*) (intptr_t) opcodesAddr;
  size_t* codeSize = (size_t*) (intptr_t) codeSizeAddr;
  batch_func_t func = codegen(opcodes, opcodesLength, codeSize);
  return (jlong) (intptr_t) func;
}

JNIEXPORT void JNICALL Java_org_joml_Native_call
  (JNIEnv * env, jclass clazz, jlong funcAddr, jlong argsAddr) {
  batch_func_t func = (batch_func_t) (intptr_t) funcAddr;
  const void* args = (const void*) (intptr_t) argsAddr;
  func(args);
}

JNIEXPORT jlong JNICALL Java_org_joml_Native_addressOf
  (JNIEnv * env, jclass clazz, jobject buffer) {
  return (jlong) (intptr_t) (*env)->GetDirectBufferAddress(env, buffer);  
}

JNIEXPORT jint JNICALL Java_org_joml_Native_free
  (JNIEnv * env, jclass clazz, jlong codeAddr, jint codeSize) {
  return (jint) free_code((void*) (intptr_t) codeAddr, codeSize);
}
