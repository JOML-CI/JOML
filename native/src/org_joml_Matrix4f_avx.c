#include <jni.h>
#include <xmmintrin.h>
#include <immintrin.h>
#include <math.h>
#include <stdint.h>

#include "Matrix4f.inc"

JNIEXPORT void JNICALL Java_org_joml_Matrix4f_mulNativeAVX(JNIEnv* env, jobject left, jobject right, jobject dest) {
	float* a = (float*)((char*) *(uintptr_t*)left  + 16);
	float* b = (float*)((char*) *(uintptr_t*)right + 16);
	float* r = (float*)((char*) *(uintptr_t*)dest  + 16);
	mul(a, b, r);
}

JNIEXPORT void JNICALL Java_org_joml_Matrix4f_mulAffineNativeAVX(JNIEnv* env, jobject left, jobject right, jobject dest) {
	float* a = (float*)((char*) *(uintptr_t*)left  + 16);
	float* b = (float*)((char*) *(uintptr_t*)right + 16);
	float* r = (float*)((char*) *(uintptr_t*)dest  + 16);	
	mulAffine(a, b, r);
}

JNIEXPORT void JNICALL Java_org_joml_Matrix4f_invertNativeAVX(JNIEnv* env, jobject src, jobject dst) {
	float* a = (float*)((char*) *(uintptr_t*)src + 16);
	float* r = (float*)((char*) *(uintptr_t*)dst + 16);
	invert(a, r);
}
