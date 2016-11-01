#include <jni.h>
#include <xmmintrin.h>
#include <immintrin.h>
#include <math.h>
#include <stdint.h>

#include "Matrix4f.inc"
#include "offsets.h"

JNIEXPORT void JNICALL Java_org_joml_Matrix4f_mulNative(JNIEnv* env, jobject left, jobject right, jobject dest) {
	float* a = (float*)((char*) *(uintptr_t*)left  + Matrix4f_m00);
	float* b = (float*)((char*) *(uintptr_t*)right + Matrix4f_m00);
	float* r = (float*)((char*) *(uintptr_t*)dest  + Matrix4f_m00);
	mul(a, b, r);
}

JNIEXPORT void JNICALL Java_org_joml_Matrix4f_mulAffineNative(JNIEnv* env, jobject left, jobject right, jobject dest) {
	float* a = (float*)((char*) *(uintptr_t*)left  + Matrix4f_m00);
	float* b = (float*)((char*) *(uintptr_t*)right + Matrix4f_m00);
	float* r = (float*)((char*) *(uintptr_t*)dest  + Matrix4f_m00);	
	mulAffine(a, b, r);
}

JNIEXPORT void JNICALL Java_org_joml_Matrix4f_invertNative(JNIEnv* env, jobject src, jobject dst) {
	float* a = (float*)((char*) *(uintptr_t*)src + Matrix4f_m00);
	float* r = (float*)((char*) *(uintptr_t*)dst + Matrix4f_m00);
	invert(a, r);
}
