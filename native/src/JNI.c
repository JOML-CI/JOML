#include <jni.h>

int Matrix4f_m00;

JNIEXPORT void JNICALL Java_org_joml_JNI_setMatrix4fm00(JNIEnv* env, jclass clazz, jint m00) {
	Matrix4f_m00 = m00;
}

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
	return JNI_VERSION_1_4;
}
