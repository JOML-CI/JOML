package org.joml;

import java.io.Serializable;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public final class Transform {

	static final long serialVersionUID = 1;

	public static final Transform IDENTITY = new Transform();

	private Matrix4f transform = new Matrix4f();
	private Quaternionf rot = new Quaternionf();
	private Vector3f translation = new Vector3f();
	private Vector3f scale = new Vector3f(1, 1, 1);

	public Transform(Vector3f translation, Quaternionf rot) {
		this.translation.set(translation);
		this.rot.set(rot);
	}

	public Transform(Vector3f translation, Quaternionf rot, Vector3f scale) {
		this(translation, rot);
		this.scale.set(scale);
	}

	public Transform(Vector3f translation) {
		this(translation, new Quaternionf());
	}

	public Transform(Quaternionf rot) {
		this(new Vector3f(), rot);
	}

	public Transform() {
		this(new Vector3f(), new Quaternionf());
	}

	/**
	 * Sets this rotation to the given Quaternionf value.
	 * 
	 * @param rot
	 *            The new rotation for this matrix.
	 * @return this
	 */
	public Transform setRotation(Quaternionf rot) {
		this.rot.set(rot);
		return this;
	}

	/**
	 * Sets this translation to the given value.
	 * 
	 * @param trans
	 *            The new translation for this matrix.
	 * @return this
	 */
	public Transform setTranslation(Vector3f trans) {
		this.translation.set(trans);
		return this;
	}

	/**
	 * Return the translation vector in this matrix.
	 * 
	 * @return translation vector.
	 */
	public Vector3f getTranslation() {
		return translation;
	}

	/**
	 * Sets this scale to the given value.
	 * 
	 * @param scale
	 *            The new scale for this matrix.
	 * @return this
	 */
	public Transform setScale(Vector3f scale) {
		this.scale.set(scale);
		return this;
	}

	/**
	 * Sets this scale to the given value.
	 * 
	 * @param scale
	 *            The new scale for this matrix.
	 * @return this
	 */
	public Transform setScale(float scale) {
		this.scale.set(scale, scale, scale);
		return this;
	}

	/**
	 * Return the scale vector in this matrix.
	 * 
	 * @return scale vector.
	 */
	public Vector3f getScale() {
		return scale;
	}

	/**
	 * Stores this translation value into the given vector3f. The value, once
	 * stored, is returned.
	 * 
	 * @param trans
	 *            The store location for this matrix's translation.
	 * @return The value of this matrix's translation.
	 */
	public Vector3f getTranslation(Vector3f trans) {
		trans.set(this.translation);
		return trans;
	}

	/**
	 * Stores this rotation value into the given Quaternionf. The value, once
	 * stored, is returned.
	 * 
	 * @param quat
	 *            The store location for this matrix's rotation.
	 * @return The value of this matrix's rotation.
	 */
	public Quaternionf getRotation(Quaternionf quat) {
		quat.set(rot);
		return quat;
	}

	/**
	 * Return the rotation quaternion in this matrix.
	 * 
	 * @return rotation quaternion.
	 */
	public Quaternionf getRotation() {
		return rot;
	}

	/**
	 * Stores this scale value into the given vector3f. The value, once stored,
	 * is returned.
	 * 
	 * @param scale
	 *            The store location for this matrix's scale.
	 * @return The value of this matrix's scale.
	 */
	public Vector3f getScale(Vector3f scale) {
		scale.set(this.scale);
		return scale;
	}

	/**
	 * Sets this matrix to the interpolation between the first matrix and the
	 * second by delta amount.
	 * 
	 * @param t1
	 *            The begining transform.
	 * @param t2
	 *            The ending transform.
	 * @param delta
	 *            An amount between 0 and 1 representing how far to interpolate
	 *            from t1 to t2.
	 */
	public void interpolateTransforms(Transform t1, Transform t2, float delta) {
		t1.rot.slerp(t2.rot, delta, rot);
		t1.translation.smoothStep(t2.translation, delta, translation);
		t1.scale.smoothStep(t2.scale, delta, scale);
	}

	/**
	 * Changes the values of this matrix acording to it's parent. Very similar
	 * to the concept of Node/Spatial transforms.
	 * 
	 * @param parent
	 *            The parent matrix.
	 * @return This matrix, after combining.
	 */
	public Transform combineWithParent(Transform parent) {
		scale.mul(parent.scale);
		parent.rot.mul(rot, rot);

		translation.mul(parent.scale);
		parent.rot.transform(translation, translation);
		translation.add(parent.translation);
		return this;
	}

	/**
	 * Sets this matrix's translation to the given x,y,z values.
	 * 
	 * @param x
	 *            This matrix's new x translation.
	 * @param y
	 *            This matrix's new y translation.
	 * @param z
	 *            This matrix's new z translation.
	 * @return this
	 */
	public Transform setTranslation(float x, float y, float z) {
		translation.set(x, y, z);
		return this;
	}

	/**
	 * Sets this matrix's scale to the given x,y,z values.
	 * 
	 * @param x
	 *            This matrix's new x scale.
	 * @param y
	 *            This matrix's new y scale.
	 * @param z
	 *            This matrix's new z scale.
	 * @return this
	 */
	public Transform setScale(float x, float y, float z) {
		scale.set(x, y, z);
		return this;
	}

	public Vector3f transformVector(final Vector3f in, Vector3f store) {
		// multiply with scale first, then rotate, finally translate (cf.
		// Eberly)
		rot.transform(store.set(in).mul(scale), store);
		return store.add(translation);
	}

	public Vector3f transformInverseVector(final Vector3f in, Vector3f store) {
		in.sub(translation, store);
		rot.invert().transform(store, store);
		store.div(scale);

		return store;
	}

	public Matrix4f toTransformMatrix() {
		// TODO: find correct JOML function.
		transform.translation(translation).rotate(rot).scale(scale);
		return transform;
	}

	public void fromTransformMatrix(Matrix4f mat) {
		// TODO: find correct JOML functions.
		translation.set(mat.m30, mat.m31, mat.m32);
		mat.get(rot);
		scale.set(1f, 1f, 1f);
	}

	public Transform invert() {
		Transform t = new Transform();
		t.fromTransformMatrix(toTransformMatrix().invert());
		return t;
	}

	/**
	 * Loads the identity. Equal to translation=(0,0,0) scale=(1,1,1)
	 * rot=(0,0,0,1).
	 */
	public void loadIdentity() {
		translation.set(0, 0, 0);
		scale.set(1, 1, 1);
		rot.set(0, 0, 0, 1);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[ " + translation.x + ", "
				+ translation.y + ", " + translation.z + "]\n" + "[ " + rot.x
				+ ", " + rot.y + ", " + rot.z + ", " + rot.w + "]\n" + "[ "
				+ scale.x + " , " + scale.y + ", " + scale.z + "]";
	}

	/**
	 * Sets this matrix to be equal to the given matrix.
	 * 
	 * @param matrixQuat
	 *            The matrix to be equal to.
	 * @return this
	 */
	public Transform set(Transform matrixQuat) {
		this.translation.set(matrixQuat.translation);
		this.rot.set(matrixQuat.rot);
		this.scale.set(matrixQuat.scale);
		return this;
	}
}
