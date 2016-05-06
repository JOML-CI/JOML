#include <malloc.h>
#include <jni.h>
#include <xmmintrin.h>

JNIEXPORT jlong JNICALL Java_org_joml_Matrix3f_allocate(JNIEnv* env, jclass clazz, jint count) {
	return (jlong)(intptr_t)_aligned_malloc((9 << 2) * count, 16);
}

JNIEXPORT void JNICALL Java_org_joml_Matrix3f_free(JNIEnv* env, jclass clazz, jlong mem) {
	_aligned_free((void*)(intptr_t)mem);
}
