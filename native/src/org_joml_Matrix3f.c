#include <jni.h>
#include <stdint.h>

#ifdef _WIN32
	#include <malloc.h>
	JNIEXPORT jlong JNICALL Java_org_joml_Matrix3f_allocate(JNIEnv* env, jclass clazz, jint count) {
		return (jlong)(intptr_t)_aligned_malloc((9 << 2) * count, 16);
	}
	JNIEXPORT void JNICALL Java_org_joml_Matrix3f_free(JNIEnv* env, jclass clazz, jlong mem) {
		_aligned_free((void*)(intptr_t)mem);
	}
#else
	#include <stdlib.h>
	JNIEXPORT jlong JNICALL Java_org_joml_Matrix3f_allocate(JNIEnv* env, jclass clazz, jint count) {
		void* ptr;
		posix_memalign(&ptr, 16, 9 << 2);
		return (jlong)(intptr_t)ptr;
	}
	JNIEXPORT void JNICALL Java_org_joml_Matrix3f_free(JNIEnv* env, jclass clazz, jlong mem) {
		free((void*)(intptr_t)mem);
	}
#endif
