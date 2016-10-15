#include <jni.h>
#include <xmmintrin.h>
#include <immintrin.h>
#include <math.h>
#include <stdint.h>

#include "Matrix4f.inc"

JNIEXPORT void JNICALL Java_org_joml_Matrix4f_mulNative(JNIEnv* env, jobject left, jobject right, jobject dest) {
	float* a = (float*)((char*) *(uintptr_t*)left  + 12);
	float* b = (float*)((char*) *(uintptr_t*)right + 12);
	float* r = (float*)((char*) *(uintptr_t*)dest  + 12);
	mul(a, b, r);
}

JNIEXPORT void JNICALL Java_org_joml_Matrix4f_mulAffineNative(JNIEnv* env, jobject left, jobject right, jobject dest) {
	float* a = (float*)((char*) *(uintptr_t*)left  + 12);
	float* b = (float*)((char*) *(uintptr_t*)right + 12);
	float* r = (float*)((char*) *(uintptr_t*)dest  + 12);	
	mulAffine(a, b, r);
}

JNIEXPORT void JNICALL Java_org_joml_Matrix4f_invertNative(JNIEnv* env, jobject src, jobject dst) {
	float* a = (float*)((char*) *(uintptr_t*)src + 12);
	float* r = (float*)((char*) *(uintptr_t*)dst + 12);
	invert(a, r);
}
