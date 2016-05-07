#include <jni.h>
#include <stdint.h>

#if _WIN32
	#include <intrin.h>
	#define cpuid(info, x)  __cpuidex(info, x, 0)
#else
	//  GCC Intrinsics
	#include <cpuid.h>
	void cpuid(int info[4], int InfoType){
	    __cpuid_count(InfoType, 0, info[0], info[1], info[2], info[3]);
	}
	uint64_t _xgetbv(unsigned int index){
	    uint32_t eax, edx;
	    __asm__ __volatile__("xgetbv" : "=a"(eax), "=d"(edx) : "c"(index));
	    return ((uint64_t)edx << 32) | eax;
	}
	#define _XCR_XFEATURE_ENABLED_MASK  0
#endif

JNIEXPORT jint JNICALL Java_org_joml_JNI_supportedExtensions(JNIEnv* env, jclass clazz) {
	char HW_SSE;
	//char HW_SSE2;
	//char HW_SSE3;
	//char HW_SSSE3;
	//char HW_SSE41;
	//char HW_SSE42;
	//char HW_SSE4a;
	char HW_AVX;
	char HW_AVX2;
	char HW_FMA3;
	char HW_FMA4;
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
		HW_FMA3 = (info[2] & ((int)1 << 12)) != 0;
		HW_AVX = (((info[2] & (1 << 28)) != 0) && ((info[2] & (1 << 27)) != 0));
	}
	if (nIds >= 0x00000007) {
		cpuid(info, 0x00000007);
		HW_AVX2 = (info[1] & ((int)1 << 5)) != 0;
	}
	if (nExIds >= 0x80000001) {
		cpuid(info, 0x80000001);
		//HW_SSE4a = (info[2] & ((int)1 << 6)) != 0;
		HW_FMA4 = (info[2] & ((int)1 << 16)) != 0;
	}
	jint res = 0;
	if (HW_SSE)
		res |= 1;
	if (HW_AVX2)
		res |= 4;
	if (HW_FMA3)
		res |= 8;
	if (HW_FMA4)
		res |= 16;
	if (HW_AVX) {
		unsigned long long xcrFeatureMask = _xgetbv(_XCR_XFEATURE_ENABLED_MASK);
		char AVX = (xcrFeatureMask & 0x6) == 0x6;
		if (AVX)
			res |= 2;
	}
	return res;
}
