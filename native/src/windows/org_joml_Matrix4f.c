#include <malloc.h>
#include <jni.h>
#include <xmmintrin.h>

JNIEXPORT jlong JNICALL Java_org_joml_Matrix4f_allocate(JNIEnv* env, jclass clazz) {
	return (jlong)(intptr_t)_aligned_malloc(16 << 2, 16);
}

JNIEXPORT void JNICALL Java_org_joml_Matrix4f_free(JNIEnv* env, jclass clazz, jlong mem) {
	_aligned_free((void*)(intptr_t)mem);
}

static void invertNative(jlong m0, jlong m1) {
	const float* src = (const float*)(intptr_t)m0;
	float* dst = (float*)(intptr_t)m1;
	__m128 minor0, minor1, minor2, minor3;
	__m128 row0, row1, row2, row3;
	__m128 det, tmp1;
	tmp1 = _mm_loadh_pi(_mm_loadl_pi(tmp1, (__m64*)(src)), (__m64*)(src + 4));
	row1 = _mm_loadh_pi(_mm_loadl_pi(row1, (__m64*)(src + 8)), (__m64*)(src + 12));
	row0 = _mm_shuffle_ps(tmp1, row1, 0x88);
	row1 = _mm_shuffle_ps(row1, tmp1, 0xDD);
	tmp1 = _mm_loadh_pi(_mm_loadl_pi(tmp1, (__m64*)(src + 2)), (__m64*)(src + 6));
	row3 = _mm_loadh_pi(_mm_loadl_pi(row3, (__m64*)(src + 10)), (__m64*)(src + 14));
	row2 = _mm_shuffle_ps(tmp1, row3, 0x88);
	row3 = _mm_shuffle_ps(row3, tmp1, 0xDD);
	tmp1 = _mm_mul_ps(row2, row3);
	tmp1 = _mm_shuffle_ps(tmp1, tmp1, 0xB1);
	minor0 = _mm_mul_ps(row1, tmp1);
	minor1 = _mm_mul_ps(row0, tmp1);
	tmp1 = _mm_shuffle_ps(tmp1, tmp1, 0x4E);
	minor0 = _mm_sub_ps(_mm_mul_ps(row1, tmp1), minor0);
	minor1 = _mm_sub_ps(_mm_mul_ps(row0, tmp1), minor1);
	minor1 = _mm_shuffle_ps(minor1, minor1, 0x4E);
	tmp1 = _mm_mul_ps(row1, row2);
	tmp1 = _mm_shuffle_ps(tmp1, tmp1, 0xB1);
	minor0 = _mm_add_ps(_mm_mul_ps(row3, tmp1), minor0);
	minor3 = _mm_mul_ps(row0, tmp1);
	tmp1 = _mm_shuffle_ps(tmp1, tmp1, 0x4E);
	minor0 = _mm_sub_ps(minor0, _mm_mul_ps(row3, tmp1));
	minor3 = _mm_sub_ps(_mm_mul_ps(row0, tmp1), minor3);
	minor3 = _mm_shuffle_ps(minor3, minor3, 0x4E);
	tmp1 = _mm_mul_ps(_mm_shuffle_ps(row1, row1, 0x4E), row3);
	tmp1 = _mm_shuffle_ps(tmp1, tmp1, 0xB1);
	row2 = _mm_shuffle_ps(row2, row2, 0x4E);
	minor0 = _mm_add_ps(_mm_mul_ps(row2, tmp1), minor0);
	minor2 = _mm_mul_ps(row0, tmp1);
	tmp1 = _mm_shuffle_ps(tmp1, tmp1, 0x4E);
	minor0 = _mm_sub_ps(minor0, _mm_mul_ps(row2, tmp1));
	minor2 = _mm_sub_ps(_mm_mul_ps(row0, tmp1), minor2);
	minor2 = _mm_shuffle_ps(minor2, minor2, 0x4E);
	tmp1 = _mm_mul_ps(row0, row1);
	tmp1 = _mm_shuffle_ps(tmp1, tmp1, 0xB1);
	minor2 = _mm_add_ps(_mm_mul_ps(row3, tmp1), minor2);
	minor3 = _mm_sub_ps(_mm_mul_ps(row2, tmp1), minor3);
	tmp1 = _mm_shuffle_ps(tmp1, tmp1, 0x4E);
	minor2 = _mm_sub_ps(_mm_mul_ps(row3, tmp1), minor2);
	minor3 = _mm_sub_ps(minor3, _mm_mul_ps(row2, tmp1));
	tmp1 = _mm_mul_ps(row0, row3);
	tmp1 = _mm_shuffle_ps(tmp1, tmp1, 0xB1);
	minor1 = _mm_sub_ps(minor1, _mm_mul_ps(row2, tmp1));
	minor2 = _mm_add_ps(_mm_mul_ps(row1, tmp1), minor2);
	tmp1 = _mm_shuffle_ps(tmp1, tmp1, 0x4E);
	minor1 = _mm_add_ps(_mm_mul_ps(row2, tmp1), minor1);
	minor2 = _mm_sub_ps(minor2, _mm_mul_ps(row1, tmp1));
	tmp1 = _mm_mul_ps(row0, row2);
	tmp1 = _mm_shuffle_ps(tmp1, tmp1, 0xB1);
	minor1 = _mm_add_ps(_mm_mul_ps(row3, tmp1), minor1);
	minor3 = _mm_sub_ps(minor3, _mm_mul_ps(row1, tmp1));
	tmp1 = _mm_shuffle_ps(tmp1, tmp1, 0x4E);
	minor1 = _mm_sub_ps(minor1, _mm_mul_ps(row3, tmp1));
	minor3 = _mm_add_ps(_mm_mul_ps(row1, tmp1), minor3);
	det = _mm_mul_ps(row0, minor0);
	det = _mm_add_ps(_mm_shuffle_ps(det, det, 0x4E), det);
	det = _mm_add_ss(_mm_shuffle_ps(det, det, 0xB1), det);
	tmp1 = _mm_rcp_ss(det);
	det = _mm_sub_ss(_mm_add_ss(tmp1, tmp1), _mm_mul_ss(det, _mm_mul_ss(tmp1, tmp1)));
	det = _mm_shuffle_ps(det, det, 0x00);
	minor0 = _mm_mul_ps(det, minor0);
	_mm_storel_pi((__m64*)(dst), minor0);
	_mm_storeh_pi((__m64*)(dst + 2), minor0);
	minor1 = _mm_mul_ps(det, minor1);
	_mm_storel_pi((__m64*)(dst + 4), minor1);
	_mm_storeh_pi((__m64*)(dst + 6), minor1);
	minor2 = _mm_mul_ps(det, minor2);
	_mm_storel_pi((__m64*)(dst + 8), minor2);
	_mm_storeh_pi((__m64*)(dst + 10), minor2);
	minor3 = _mm_mul_ps(det, minor3);
	_mm_storel_pi((__m64*)(dst + 12), minor3);
	_mm_storeh_pi((__m64*)(dst + 14), minor3);
}

JNIEXPORT void JNICALL Java_org_joml_Matrix4f_invertNative(JNIEnv* env, jclass clazz, jlong m0, jlong m1) {
	invertNative(m0, m1);
}
JNIEXPORT void JNICALL JavaCritical_org_joml_Matrix4f_invertNative(jlong m0, jlong m1) {
	invertNative(m0, m1);
}

static void mulNative(jlong m0, jlong m1, jlong dest) {
	const float* a = (const float*)(intptr_t)m0;
	const float* b = (const float*)(intptr_t)m1;
	float* r = (float*)(intptr_t)dest;
	__m128 col1 = _mm_load_ps(&a[0]);
	__m128 col2 = _mm_load_ps(&a[4]);
	__m128 col3 = _mm_load_ps(&a[8]);
	__m128 col4 = _mm_load_ps(&a[12]);
	for (int i = 0; i < 4; i++) {
		__m128 brod1 = _mm_set1_ps(b[i * 4 + 0]);
		__m128 brod2 = _mm_set1_ps(b[i * 4 + 1]);
		__m128 brod3 = _mm_set1_ps(b[i * 4 + 2]);
		__m128 brod4 = _mm_set1_ps(b[i * 4 + 3]);
		__m128 col = _mm_add_ps(
			_mm_add_ps(
				_mm_mul_ps(brod1, col1),
				_mm_mul_ps(brod2, col2)),
			_mm_add_ps(
				_mm_mul_ps(brod3, col3),
				_mm_mul_ps(brod4, col4)));
		_mm_store_ps(&r[i * 4], col);
	}
}

JNIEXPORT void JNICALL Java_org_joml_Matrix4f_mulNative(JNIEnv* env, jclass clazz, jlong m0, jlong m1, jlong dest) {
	mulNative(m0, m1, dest);
}
JNIEXPORT void JNICALL JavaCritical_org_joml_Matrix4f_mulNative(jlong m0, jlong m1, jlong dest) {
	mulNative(m0, m1, dest);
}

static void identity(jlong m) {
	float* a = (float*)(intptr_t)m;
	float val = 1.0f;
	__m128 mem = _mm_load_ss(&val);
	_mm_store_ps(&a[0], mem);
	mem = _mm_shuffle_ps(mem, mem, _MM_SHUFFLE(2, 1, 0, 3));
	_mm_store_ps(&a[4], mem);
	mem = _mm_shuffle_ps(mem, mem, _MM_SHUFFLE(2, 1, 0, 3));
	_mm_store_ps(&a[8], mem);
	mem = _mm_shuffle_ps(mem, mem, _MM_SHUFFLE(2, 1, 0, 3));
	_mm_store_ps(&a[12], mem);
}

JNIEXPORT void JNICALL Java_org_joml_Matrix4f_identity(JNIEnv* env, jclass clazz, jlong m) {
	identity(m);
}

JNIEXPORT void JNICALL JavaCritical_org_joml_Matrix4f_identity(jlong m) {
	identity(m);
}

static void copy(jlong src, jlong dst) {
	const float* a = (const float*)(intptr_t)src;
	float* b = (float*)(intptr_t)dst;
	__m128 mem = _mm_load_ps(&a[0]);
	_mm_store_ps(&b[0], mem);
	mem = _mm_load_ps(&a[4]);
	_mm_store_ps(&b[4], mem);
	mem = _mm_load_ps(&a[8]);
	_mm_store_ps(&b[8], mem);
	mem = _mm_load_ps(&a[12]);
	_mm_store_ps(&b[12], mem);
}

JNIEXPORT void JNICALL Java_org_joml_Matrix4f_copy(JNIEnv* env, jclass clazz, jlong src, jlong dst) {
	copy(src, dst);
}

JNIEXPORT void JNICALL JavaCritical_org_joml_Matrix4f_copy(jlong src, jlong dst) {
	copy(src, dst);
}

static void zero(jlong m) {
	float* a = (float*)(intptr_t)m;
	__m128 zero;
	_mm_xor_ps(zero, zero);
	_mm_store_ps(a, zero);
	_mm_store_ps(&a[4], zero);
	_mm_store_ps(&a[8], zero);
	_mm_store_ps(&a[12], zero);
}

JNIEXPORT void JNICALL Java_org_joml_Matrix4f_zero(JNIEnv* env, jclass clazz, jlong m) {
	zero(m);
}
JNIEXPORT void JNICALL JavaCritical_org_joml_Matrix4f_zero(jlong m) {
	zero(m);
}
