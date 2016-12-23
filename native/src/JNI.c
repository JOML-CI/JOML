#include <jni.h>
#include <stdint.h>
JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
	return JNI_VERSION_1_4;
}
JNIEXPORT jobject JNICALL Java_org_joml_MemUtil_00024MemUtilUnsafe_newTestBuffer(JNIEnv *env, jclass clazz) {
	return (*env)->NewDirectByteBuffer(env, (void*) (intptr_t) 0xFEEDBABEDEADBEEFL, 0);
}
JNIEXPORT jint JNICALL Java_org_joml_MemUtil_00024MemUtilUnsafe_getPointerSize(JNIEnv *env, jclass clazz) {
	return (jint) sizeof(void*);
}
