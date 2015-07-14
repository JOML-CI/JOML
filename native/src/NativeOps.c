#include <jni.h>
#include "codegen.h"

JNIEXPORT jlong JNICALL Java_org_joml_Jit_jit
  (JNIEnv * env, jclass clazz, jlong opcodesAddr, jint opcodesLength) {
  const char* opcodes = (const char*) (intptr_t) opcodesAddr;
  batch_func_t func = codegen(opcodes, opcodesLength);
  return (jlong) (intptr_t) func;
}

JNIEXPORT void JNICALL Java_org_joml_Sequence_call0
  (JNIEnv * env, jclass clazz, jlong funcAddr, jlong argsAddr) {
  batch_func_t func = (batch_func_t) (intptr_t) funcAddr;
  const void* args = (const void*) (intptr_t) argsAddr;
  func(args);
}

JNIEXPORT jlong JNICALL Java_org_joml_NativeUtil_addressOf
  (JNIEnv * env, jclass clazz, jobject buffer) {
  return (jlong) (intptr_t) (*env)->GetDirectBufferAddress(env, buffer);  
}
