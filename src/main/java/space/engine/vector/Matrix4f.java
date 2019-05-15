package space.engine.vector;

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
	
	public Matrix4f multiply(Matrix4f mat) {
		return this.set(
				this.m11 * mat.m11 + this.m12 * mat.m21 + this.m13 * mat.m31 + this.m14 * mat.m41,
				this.m11 * mat.m12 + this.m12 * mat.m22 + this.m13 * mat.m32 + this.m14 * mat.m42,
				this.m11 * mat.m13 + this.m12 * mat.m23 + this.m13 * mat.m33 + this.m14 * mat.m43,
				this.m11 * mat.m14 + this.m12 * mat.m24 + this.m13 * mat.m34 + this.m14 * mat.m44,
				this.m21 * mat.m11 + this.m22 * mat.m21 + this.m23 * mat.m31 + this.m24 * mat.m41,
				this.m21 * mat.m12 + this.m22 * mat.m22 + this.m23 * mat.m32 + this.m24 * mat.m42,
				this.m21 * mat.m13 + this.m22 * mat.m23 + this.m23 * mat.m33 + this.m24 * mat.m43,
				this.m21 * mat.m14 + this.m22 * mat.m24 + this.m23 * mat.m34 + this.m24 * mat.m44,
				this.m31 * mat.m11 + this.m32 * mat.m21 + this.m33 * mat.m31 + this.m34 * mat.m41,
				this.m31 * mat.m12 + this.m32 * mat.m22 + this.m33 * mat.m32 + this.m34 * mat.m42,
				this.m31 * mat.m13 + this.m32 * mat.m23 + this.m33 * mat.m33 + this.m34 * mat.m43,
				this.m31 * mat.m14 + this.m32 * mat.m24 + this.m33 * mat.m34 + this.m34 * mat.m44,
				this.m41 * mat.m11 + this.m42 * mat.m21 + this.m43 * mat.m31 + this.m44 * mat.m41,
				this.m41 * mat.m12 + this.m42 * mat.m22 + this.m43 * mat.m32 + this.m44 * mat.m42,
				this.m41 * mat.m13 + this.m42 * mat.m23 + this.m43 * mat.m33 + this.m44 * mat.m43,
				this.m41 * mat.m14 + this.m42 * mat.m24 + this.m43 * mat.m34 + this.m44 * mat.m44
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
