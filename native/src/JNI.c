#include <jni.h>
#ifdef _WIN32
#define cpuid(info, x) __cpuidex(info, x, 0)
#else
#include <cpuid.h>
static void cpuid(int info[4], int InfoType) {
  __cpuid_count(InfoType, 0, info[0], info[1], info[2], info[3]);
}
#endif
static char fma3() {
  int info[4];
  cpuid(info, 0);
  int nIds = info[0];
  if (nIds >= 1) {
    cpuid(info, 1);
    return (info[2] & 1 << 12) != 0;
  }
  return 0;
}
JNIEXPORT jboolean JNICALL Java_org_joml_internal_Runtime_hasCpuFma3(JNIEnv* env, jclass clazz) {
  return (jboolean) fma3();
}
