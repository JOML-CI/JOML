#include <malloc.h>
#include <jni.h>
#include <xmmintrin.h>
#include <math.h>

JNIEXPORT jlong JNICALL Java_org_joml_Matrix4f_allocate(JNIEnv* env, jclass clazz, jint count) {
	return (jlong)(intptr_t)_aligned_malloc((16 << 2) * count, 16);
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

JNIEXPORT void JNICALL Java_org_joml_Matrix4f_mulBatchedNative(JNIEnv* env, jclass clazz, jint count, jlong m0, jlong m1, jlong dest) {
	for (int i = 0; i < count; i++)
		mulNative(m0 + (16 << 2)*i, m1 + (16 << 2)*i, dest + (16 << 2)*i);
}
JNIEXPORT void JNICALL JavaCritical_org_joml_Matrix4f_mulBatchedNative(jint count, jlong m0, jlong m1, jlong dest) {
	for (int i = 0; i < count; i++)
		mulNative(m0 + (16 << 2)*i, m1 + (16 << 2)*i, dest + (16 << 2)*i);
}

static void mulAffineNative(jlong m0, jlong m1, jlong dest) {
	const float* a = (const float*)(intptr_t)m0;
	const float* b = (const float*)(intptr_t)m1;
	float* r = (float*)(intptr_t)dest;
	__m128 col1 = _mm_load_ps(&a[0]);
	__m128 col2 = _mm_load_ps(&a[4]);
	__m128 col3 = _mm_load_ps(&a[8]);
	__m128 col4 = _mm_load_ps(&a[12]);
	{
		__m128 brod1 = _mm_set1_ps(b[0 * 4 + 0]);
		__m128 brod2 = _mm_set1_ps(b[0 * 4 + 1]);
		__m128 brod3 = _mm_set1_ps(b[0 * 4 + 2]);
		__m128 col = _mm_add_ps(
			_mm_add_ps(
				_mm_mul_ps(brod1, col1),
				_mm_mul_ps(brod2, col2)),
			_mm_mul_ps(brod3, col3));
		_mm_store_ps(&r[0 * 4], col);
	}
	{
		__m128 brod1 = _mm_set1_ps(b[1 * 4 + 0]);
		__m128 brod2 = _mm_set1_ps(b[1 * 4 + 1]);
		__m128 brod3 = _mm_set1_ps(b[1 * 4 + 2]);
		__m128 col = _mm_add_ps(
			_mm_add_ps(
				_mm_mul_ps(brod1, col1),
				_mm_mul_ps(brod2, col2)),
			_mm_mul_ps(brod3, col3));
		_mm_store_ps(&r[1 * 4], col);
	}
	{
		__m128 brod1 = _mm_set1_ps(b[2 * 4 + 0]);
		__m128 brod2 = _mm_set1_ps(b[2 * 4 + 1]);
		__m128 brod3 = _mm_set1_ps(b[2 * 4 + 2]);
		__m128 col = _mm_add_ps(
			_mm_add_ps(
				_mm_mul_ps(brod1, col1),
				_mm_mul_ps(brod2, col2)),
			_mm_mul_ps(brod3, col3));
		_mm_store_ps(&r[2 * 4], col);
	}
	{
		__m128 brod1 = _mm_set1_ps(b[3 * 4 + 0]);
		__m128 brod2 = _mm_set1_ps(b[3 * 4 + 1]);
		__m128 brod3 = _mm_set1_ps(b[3 * 4 + 2]);
		__m128 col = _mm_add_ps(
			_mm_add_ps(
				_mm_mul_ps(brod1, col1),
				_mm_mul_ps(brod2, col2)),
			_mm_add_ps(_mm_mul_ps(brod3, col3), col4));
		_mm_store_ps(&r[3 * 4], col);
	}
}

JNIEXPORT void JNICALL Java_org_joml_Matrix4f_mulAffineNative(JNIEnv* env, jclass clazz, jlong m0, jlong m1, jlong dest) {
	mulAffineNative(m0, m1, dest);
}
JNIEXPORT void JNICALL JavaCritical_org_joml_Matrix4f_mulAffineNative(jlong m0, jlong m1, jlong dest) {
	mulAffineNative(m0, m1, dest);
}

JNIEXPORT void JNICALL Java_org_joml_Matrix4f_mulAffineBatchedNative(JNIEnv* env, jclass clazz, jint count, jlong m0, jlong m1, jlong dest) {
	for (int i = 0; i < count; i++)
		mulAffineNative(m0 + (16 << 2)*i, m1 + (16 << 2)*i, dest + (16 << 2)*i);
}
JNIEXPORT void JNICALL JavaCritical_org_joml_Matrix4f_mulAffineBatchedNative(jint count, jlong m0, jlong m1, jlong dest) {
	for (int i = 0; i < count; i++)
		mulAffineNative(m0 + (16 << 2)*i, m1 + (16 << 2)*i, dest + (16 << 2)*i);
}

static void rotateAngleXYZ(float angle, float x, float y, float z, const float* src, float* dst) {
	float s = (float)sin(angle), c = (float)cos(angle);
	float C = 1.0f - c;
	float xx = x * x, xy = x * y, xz = x * z;
	float yy = y * y, yz = y * z;
	float zz = z * z;
	float rn00 = xx * C + c;
	float rn01 = xy * C + z * s;
	float rn02 = xz * C - y * s;
	float rn10 = xy * C - z * s;
	float rn11 = yy * C + c;
	float rn12 = yz * C + x * s;
	float rn20 = xz * C + y * s;
	float rn21 = yz * C - x * s;
	float rn22 = zz * C + c;
	__m128 col1 = _mm_load_ps(&src[0]);
	__m128 col2 = _mm_load_ps(&src[4]);
	__m128 col3 = _mm_load_ps(&src[8]);
	{
		__m128 brod1 = _mm_set1_ps(rn00);
		__m128 brod2 = _mm_set1_ps(rn01);
		__m128 brod3 = _mm_set1_ps(rn02);
		__m128 col = _mm_add_ps(
			_mm_add_ps(
				_mm_mul_ps(brod1, col1),
				_mm_mul_ps(brod2, col2)),
			_mm_mul_ps(brod3, col3));
		_mm_store_ps(&dst[0 * 4], col);
	}
	{
		__m128 brod1 = _mm_set1_ps(rn10);
		__m128 brod2 = _mm_set1_ps(rn11);
		__m128 brod3 = _mm_set1_ps(rn12);
		__m128 col = _mm_add_ps(
			_mm_add_ps(
				_mm_mul_ps(brod1, col1),
				_mm_mul_ps(brod2, col2)),
			_mm_mul_ps(brod3, col3));
		_mm_store_ps(&dst[1 * 4], col);
	}
	{
		__m128 brod1 = _mm_set1_ps(rn20);
		__m128 brod2 = _mm_set1_ps(rn21);
		__m128 brod3 = _mm_set1_ps(rn22);
		__m128 col = _mm_add_ps(
			_mm_add_ps(
				_mm_mul_ps(brod1, col1),
				_mm_mul_ps(brod2, col2)),
			_mm_mul_ps(brod3, col3));
		_mm_store_ps(&dst[2 * 4], col);
	}
	__m128 mem = _mm_load_ps(&src[3 * 4]);
	_mm_store_ps(&dst[3 * 4], mem);
}

JNIEXPORT void JNICALL Java_org_joml_Matrix4f_rotateAngleXYZ(JNIEnv* env, jclass clazz, jfloat angle, jfloat x, jfloat y, jfloat z, jlong src, jlong dst) {
	rotateAngleXYZ((float)angle, (float)x, (float)y, (float)z, (const float*)(intptr_t)src, (float*)(intptr_t)dst);
}
JNIEXPORT void JNICALL JavaCritical_org_joml_Matrix4f_rotateAngleXYZ(jfloat angle, jfloat x, jfloat y, jfloat z, jlong src, jlong dst) {
	rotateAngleXYZ((float)angle, (float)x, (float)y, (float)z, (const float*)(intptr_t)src, (float*)(intptr_t)dst);
}
