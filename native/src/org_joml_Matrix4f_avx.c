#include <jni.h>
#include <xmmintrin.h>
#include <immintrin.h>
#include <math.h>
#include <stdint.h>

#include "Matrix4f.inc"

JNIEXPORT void JNICALL Java_org_joml_Matrix4f_mulNativeAVX(JNIEnv* env, jclass clazz, jlong a, jlong b, jlong r) {
	mul((const float*) a, (const float*) b, (float*) r);
}

JNIEXPORT void JNICALL Java_org_joml_Matrix4f_mulAffineNativeAVX(JNIEnv* env, jclass clazz, jlong a, jlong b, jlong r) {
	mulAffine((const float*) a, (const float*) b, (float*) r);
}

JNIEXPORT void JNICALL Java_org_joml_Matrix4f_invertNativeAVX(JNIEnv* env, jclass clazz, jlong a, jlong r) {
	invert((const float*) a, (float*) r);
}
