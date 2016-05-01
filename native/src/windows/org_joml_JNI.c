#include <jni.h>

#define cpuid(info, x)  __cpuidex(info, x, 0)

JNIEXPORT jint JNICALL Java_org_joml_JNI_supportedExtensions(JNIEnv* env, jclass clazz) {
	char HW_SSE;
	//char HW_SSE2;
	//char HW_SSE3;
	//char HW_SSSE3;
	//char HW_SSE41;
	//char HW_SSE42;
	//char HW_SSE4a;
	char HW_AVX;
	int info[4];
	cpuid(info, 0);
	int nIds = info[0];
	cpuid(info, 0x80000000);
	unsigned nExIds = info[0];
	if (nIds >= 0x00000001) {
		cpuid(info, 0x00000001);
		HW_SSE = (info[3] & ((int)1 << 25)) != 0;
		//HW_SSE2 = (info[3] & ((int)1 << 26)) != 0;
		//HW_SSE3 = (info[2] & ((int)1 << 0)) != 0;
		//HW_SSSE3 = (info[2] & ((int)1 << 9)) != 0;
		//HW_SSE41 = (info[2] & ((int)1 << 19)) != 0;
		//HW_SSE42 = (info[2] & ((int)1 << 20)) != 0;
		HW_AVX = (info[2] & ((int)1 << 28)) != 0;
	}
	if (nExIds >= 0x80000001) {
		cpuid(info, 0x80000001);
		//HW_SSE4a = (info[2] & ((int)1 << 6)) != 0;
	}
	jint res = 0;
	if (HW_SSE)
		res |= 1;
	if (HW_AVX)
		res |= 2;
	return res;
}
