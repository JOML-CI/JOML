#include <malloc.h>
#include <jni.h>
#include <immintrin.h>

JNIEXPORT jlong JNICALL Java_org_joml_Matrix4d_allocate(JNIEnv* env, jclass clazz) {
	return (jlong)(intptr_t)_aligned_malloc(16 << 3, 32);
}

JNIEXPORT void JNICALL Java_org_joml_Matrix4d_free(JNIEnv* env, jclass clazz, jlong mem) {
	_aligned_free((void*)(intptr_t)mem);
}

static void mulNative(jlong m0, jlong m1, jlong dest) {
	const double* a = (const double*)(intptr_t)m0;
	const double* b = (const double*)(intptr_t)m1;
	double* r = (double*)(intptr_t)dest;
	__m256d col1 = _mm256_load_pd(&a[0]);
	__m256d col2 = _mm256_load_pd(&a[4]);
	__m256d col3 = _mm256_load_pd(&a[8]);
	__m256d col4 = _mm256_load_pd(&a[12]);
	for (int i = 0; i < 4; i++) {
		__m256d brod1 = _mm256_set1_pd(b[i * 4 + 0]);
		__m256d brod2 = _mm256_set1_pd(b[i * 4 + 1]);
		__m256d brod3 = _mm256_set1_pd(b[i * 4 + 2]);
		__m256d brod4 = _mm256_set1_pd(b[i * 4 + 3]);
		__m256d col = _mm256_add_pd(
			_mm256_add_pd(
				_mm256_mul_pd(brod1, col1),
				_mm256_mul_pd(brod2, col2)),
			_mm256_add_pd(
				_mm256_mul_pd(brod3, col3),
				_mm256_mul_pd(brod4, col4)));
		_mm256_store_pd(&r[i * 4], col);
	}
}

JNIEXPORT void JNICALL Java_org_joml_Matrix4d_mulNative(JNIEnv* env, jclass clazz, jlong m0, jlong m1, jlong dest) {
	mulNative(m0, m1, dest);
}
JNIEXPORT void JNICALL JavaCritical_org_joml_Matrix4d_mulNative(jlong m0, jlong m1, jlong dest) {
	mulNative(m0, m1, dest);
}

static void mulAffineNative(jlong m0, jlong m1, jlong dest) {
	const double* a = (const double*)(intptr_t)m0;
	const double* b = (const double*)(intptr_t)m1;
	double* r = (double*)(intptr_t)dest;
	__m256d col1 = _mm256_load_pd(&a[0]);
	__m256d col2 = _mm256_load_pd(&a[4]);
	__m256d col3 = _mm256_load_pd(&a[8]);
	__m256d col4 = _mm256_load_pd(&a[12]);
	{
		__m256d brod1 = _mm256_set1_pd(b[0 * 4 + 0]);
		__m256d brod2 = _mm256_set1_pd(b[0 * 4 + 1]);
		__m256d brod3 = _mm256_set1_pd(b[0 * 4 + 2]);
		__m256d col = _mm256_add_pd(
			_mm256_add_pd(
				_mm256_mul_pd(brod1, col1),
				_mm256_mul_pd(brod2, col2)),
			_mm256_mul_pd(brod3, col3));
		_mm256_store_pd(&r[0 * 4], col);
	}
	{
		__m256d brod1 = _mm256_set1_pd(b[1 * 4 + 0]);
		__m256d brod2 = _mm256_set1_pd(b[1 * 4 + 1]);
		__m256d brod3 = _mm256_set1_pd(b[1 * 4 + 2]);
		__m256d col = _mm256_add_pd(
			_mm256_add_pd(
				_mm256_mul_pd(brod1, col1),
				_mm256_mul_pd(brod2, col2)),
			_mm256_mul_pd(brod3, col3));
		_mm256_store_pd(&r[1 * 4], col);
	}
	{
		__m256d brod1 = _mm256_set1_pd(b[2 * 4 + 0]);
		__m256d brod2 = _mm256_set1_pd(b[2 * 4 + 1]);
		__m256d brod3 = _mm256_set1_pd(b[2 * 4 + 2]);
		__m256d col = _mm256_add_pd(
			_mm256_add_pd(
				_mm256_mul_pd(brod1, col1),
				_mm256_mul_pd(brod2, col2)),
			_mm256_mul_pd(brod3, col3));
		_mm256_store_pd(&r[2 * 4], col);
	}
	{
		__m256d brod1 = _mm256_set1_pd(b[3 * 4 + 0]);
		__m256d brod2 = _mm256_set1_pd(b[3 * 4 + 1]);
		__m256d brod3 = _mm256_set1_pd(b[3 * 4 + 2]);
		__m256d col = _mm256_add_pd(
			_mm256_add_pd(
				_mm256_mul_pd(brod1, col1),
				_mm256_mul_pd(brod2, col2)),
			_mm256_add_pd(_mm256_mul_pd(brod3, col3), col4));
		_mm256_store_pd(&r[3 * 4], col);
	}
}

JNIEXPORT void JNICALL Java_org_joml_Matrix4d_mulAffineNative(JNIEnv* env, jclass clazz, jlong m0, jlong m1, jlong dest) {
	mulAffineNative(m0, m1, dest);
}
JNIEXPORT void JNICALL JavaCritical_org_joml_Matrix4d_mulAffineNative(jlong m0, jlong m1, jlong dest) {
	mulAffineNative(m0, m1, dest);
}
