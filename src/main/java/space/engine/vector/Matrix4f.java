package space.engine.vector;

/**
 * a row major ordered matrix
 */
public class Matrix4f {
	
	public float m11, m12, m13, m14, m21, m22, m23, m24, m31, m32, m33, m34, m41, m42, m43, m44;
	
	public Matrix4f() {
		identity();
	}
	
	@SuppressWarnings("CopyConstructorMissesField")
	public Matrix4f(Matrix4f mat) {
		set(mat);
	}
	
	public Matrix4f(float[] array, int offset) {
		set(array, offset);
	}
	
	public Matrix4f(float m11, float m12, float m13, float m14, float m21, float m22, float m23, float m24, float m31, float m32, float m33, float m34, float m41, float m42, float m43, float m44) {
		set(m11, m12, m13, m14, m21, m22, m23, m24, m31, m32, m33, m34, m41, m42, m43, m44);
	}
	
	public Matrix4f set(Matrix4f mat) {
		set(mat.m11, mat.m12, mat.m13, mat.m14, mat.m21, mat.m22, mat.m23, mat.m24, mat.m31, mat.m32, mat.m33, mat.m34, mat.m41, mat.m42, mat.m43, mat.m44);
		return this;
	}
	
	public Matrix4f set(float[] array, int offset) {
		return set(
				array[offset], array[offset + 1], array[offset + 2], array[offset + 3],
				array[offset + 4], array[offset + 5], array[offset + 6], array[offset + 7],
				array[offset + 8], array[offset + 9], array[offset + 10], array[offset + 11],
				array[offset + 12], array[offset + 13], array[offset + 14], array[offset + 15]
		);
	}
	
	public Matrix4f set(float m11, float m12, float m13, float m14, float m21, float m22, float m23, float m24, float m31, float m32, float m33, float m34, float m41, float m42, float m43, float m44) {
		this.m11 = m11;
		this.m12 = m12;
		this.m13 = m13;
		this.m14 = m14;
		this.m21 = m21;
		this.m22 = m22;
		this.m23 = m23;
		this.m24 = m24;
		this.m31 = m31;
		this.m32 = m32;
		this.m33 = m33;
		this.m34 = m34;
		this.m41 = m41;
		this.m42 = m42;
		this.m43 = m43;
		this.m44 = m44;
		return this;
	}
	
	public Matrix4f identity() {
		return set(
				1, 0, 0, 0,
				0, 1, 0, 0,
				0, 0, 1, 0,
				0, 0, 0, 1
		);
	}
	
	public Matrix4f modelOffset(Vector3f vector) {
		this.m14 = vector.x;
		this.m24 = vector.y;
		this.m34 = vector.z;
		return this;
	}
	
	public Matrix4f modelScale(Vector3f vector) {
		this.m11 *= vector.x;
		this.m12 *= vector.x;
		this.m13 *= vector.x;
		this.m21 *= vector.y;
		this.m22 *= vector.y;
		this.m23 *= vector.y;
		this.m31 *= vector.z;
		this.m32 *= vector.z;
		this.m33 *= vector.z;
		return this;
	}
	
	public Matrix4f multiply(Matrix4f mat) {
		return multiply(this, mat);
	}
	
	public Matrix4f multiply(Matrix4f mat1, Matrix4f mat2) {
		return set(
				mat1.m11 * mat2.m11 + mat1.m12 * mat2.m21 + mat1.m13 * mat2.m31 + mat1.m14 * mat2.m41,
				mat1.m11 * mat2.m12 + mat1.m12 * mat2.m22 + mat1.m13 * mat2.m32 + mat1.m14 * mat2.m42,
				mat1.m11 * mat2.m13 + mat1.m12 * mat2.m23 + mat1.m13 * mat2.m33 + mat1.m14 * mat2.m43,
				mat1.m11 * mat2.m14 + mat1.m12 * mat2.m24 + mat1.m13 * mat2.m34 + mat1.m14 * mat2.m44,
				mat1.m21 * mat2.m11 + mat1.m22 * mat2.m21 + mat1.m23 * mat2.m31 + mat1.m24 * mat2.m41,
				mat1.m21 * mat2.m12 + mat1.m22 * mat2.m22 + mat1.m23 * mat2.m32 + mat1.m24 * mat2.m42,
				mat1.m21 * mat2.m13 + mat1.m22 * mat2.m23 + mat1.m23 * mat2.m33 + mat1.m24 * mat2.m43,
				mat1.m21 * mat2.m14 + mat1.m22 * mat2.m24 + mat1.m23 * mat2.m34 + mat1.m24 * mat2.m44,
				mat1.m31 * mat2.m11 + mat1.m32 * mat2.m21 + mat1.m33 * mat2.m31 + mat1.m34 * mat2.m41,
				mat1.m31 * mat2.m12 + mat1.m32 * mat2.m22 + mat1.m33 * mat2.m32 + mat1.m34 * mat2.m42,
				mat1.m31 * mat2.m13 + mat1.m32 * mat2.m23 + mat1.m33 * mat2.m33 + mat1.m34 * mat2.m43,
				mat1.m31 * mat2.m14 + mat1.m32 * mat2.m24 + mat1.m33 * mat2.m34 + mat1.m34 * mat2.m44,
				mat1.m41 * mat2.m11 + mat1.m42 * mat2.m21 + mat1.m43 * mat2.m31 + mat1.m44 * mat2.m41,
				mat1.m41 * mat2.m12 + mat1.m42 * mat2.m22 + mat1.m43 * mat2.m32 + mat1.m44 * mat2.m42,
				mat1.m41 * mat2.m13 + mat1.m42 * mat2.m23 + mat1.m43 * mat2.m33 + mat1.m44 * mat2.m43,
				mat1.m41 * mat2.m14 + mat1.m42 * mat2.m24 + mat1.m43 * mat2.m34 + mat1.m44 * mat2.m44
		);
	}
	
	public Matrix4f multiply(float scalar) {
		this.m11 *= scalar;
		this.m12 *= scalar;
		this.m13 *= scalar;
		this.m14 *= scalar;
		this.m21 *= scalar;
		this.m22 *= scalar;
		this.m23 *= scalar;
		this.m24 *= scalar;
		this.m31 *= scalar;
		this.m32 *= scalar;
		this.m33 *= scalar;
		this.m34 *= scalar;
		this.m41 *= scalar;
		this.m42 *= scalar;
		this.m43 *= scalar;
		this.m44 *= scalar;
		return this;
	}
	
	public Matrix4f multiply(double scalar) {
		this.m11 *= scalar;
		this.m12 *= scalar;
		this.m13 *= scalar;
		this.m14 *= scalar;
		this.m21 *= scalar;
		this.m22 *= scalar;
		this.m23 *= scalar;
		this.m24 *= scalar;
		this.m31 *= scalar;
		this.m32 *= scalar;
		this.m33 *= scalar;
		this.m34 *= scalar;
		this.m41 *= scalar;
		this.m42 *= scalar;
		this.m43 *= scalar;
		this.m44 *= scalar;
		return this;
	}
	
	public float[] write(float[] array, int offset) {
		array[offset] = m11;
		array[offset + 1] = m12;
		array[offset + 2] = m13;
		array[offset + 3] = m14;
		array[offset + 4] = m21;
		array[offset + 5] = m22;
		array[offset + 6] = m23;
		array[offset + 7] = m24;
		array[offset + 8] = m31;
		array[offset + 9] = m32;
		array[offset + 10] = m33;
		array[offset + 11] = m34;
		array[offset + 12] = m41;
		array[offset + 13] = m42;
		array[offset + 14] = m43;
		array[offset + 15] = m44;
		return array;
	}
	
	@Override
	public String toString() {
		return "{" + m11 + " " + m12 + " " + m13 + " " + m14 + "} {" + m21 + " " + m22 + " " + m23 + " " + m34 + "} {" + m31 + " " + m32 + " " + m33 + " " + m34 + "} {" + m41 + " " + m42 + " " + m43 + " " + m44 + "}";
	}
}
