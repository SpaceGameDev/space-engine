package space.engine.vector;

public class Matrix3f {
	
	public float m11, m12, m13, m21, m22, m23, m31, m32, m33;
	
	public Matrix3f() {
		identity();
	}
	
	@SuppressWarnings("CopyConstructorMissesField")
	public Matrix3f(Matrix3f mat) {
		set(mat);
	}
	
	public Matrix3f(float[] array, int offset) {
		set(array, offset);
	}
	
	public Matrix3f(float m11, float m12, float m13, float m21, float m22, float m23, float m31, float m32, float m33) {
		set(m11, m12, m13, m21, m22, m23, m31, m32, m33);
	}
	
	public Matrix3f set(Matrix3f mat) {
		return set(mat.m11, mat.m12, mat.m13, mat.m21, mat.m22, mat.m23, mat.m31, mat.m32, mat.m33);
	}
	
	public Matrix3f set(float[] array, int offset) {
		return set(
				array[offset], array[offset + 1], array[offset + 2],
				array[offset + 3], array[offset + 4], array[offset + 5],
				array[offset + 6], array[offset + 7], array[offset + 8]
		);
	}
	
	public Matrix3f set(float m11, float m12, float m13, float m21, float m22, float m23, float m31, float m32, float m33) {
		this.m11 = m11;
		this.m12 = m12;
		this.m13 = m13;
		this.m21 = m21;
		this.m22 = m22;
		this.m23 = m23;
		this.m31 = m31;
		this.m32 = m32;
		this.m33 = m33;
		return this;
	}
	
	public Matrix3f identity() {
		return set(
				1, 0, 0,
				0, 1, 0,
				0, 0, 1
		);
	}
	
	public Matrix3f multiply(Matrix3f mat1, Matrix3f mat2) {
		return set(
				mat1.m11 * mat2.m11 + mat1.m12 * mat2.m21 + mat1.m13 * mat2.m31,
				mat1.m11 * mat2.m12 + mat1.m12 * mat2.m22 + mat1.m13 * mat2.m32,
				mat1.m11 * mat2.m13 + mat1.m12 * mat2.m23 + mat1.m13 * mat2.m33,
				mat1.m21 * mat2.m11 + mat1.m22 * mat2.m21 + mat1.m23 * mat2.m31,
				mat1.m21 * mat2.m12 + mat1.m22 * mat2.m22 + mat1.m23 * mat2.m32,
				mat1.m21 * mat2.m13 + mat1.m22 * mat2.m23 + mat1.m23 * mat2.m33,
				mat1.m31 * mat2.m11 + mat1.m32 * mat2.m21 + mat1.m33 * mat2.m31,
				mat1.m31 * mat2.m12 + mat1.m32 * mat2.m22 + mat1.m33 * mat2.m32,
				mat1.m31 * mat2.m13 + mat1.m32 * mat2.m23 + mat1.m33 * mat2.m33
		);
	}
	
	public Matrix3f multiply(float scalar) {
		this.m11 *= scalar;
		this.m12 *= scalar;
		this.m13 *= scalar;
		this.m21 *= scalar;
		this.m22 *= scalar;
		this.m23 *= scalar;
		this.m31 *= scalar;
		this.m32 *= scalar;
		this.m33 *= scalar;
		return this;
	}
	
	public Matrix3f multiply(double scalar) {
		this.m11 *= scalar;
		this.m12 *= scalar;
		this.m13 *= scalar;
		this.m21 *= scalar;
		this.m22 *= scalar;
		this.m23 *= scalar;
		this.m31 *= scalar;
		this.m32 *= scalar;
		this.m33 *= scalar;
		return this;
	}
	
	public float[] write(float[] array, int offset) {
		array[offset] = m11;
		array[offset + 1] = m12;
		array[offset + 2] = m13;
		array[offset + 3] = m21;
		array[offset + 4] = m22;
		array[offset + 5] = m23;
		array[offset + 6] = m31;
		array[offset + 7] = m32;
		array[offset + 8] = m33;
		return array;
	}
	
	public Matrix4f toMatrix4f(Matrix4f mat) {
		return mat.set(
				m11, m12, m13, 0,
				m21, m22, m23, 0,
				m31, m32, m33, 0,
				0, 0, 0, 1
		);
	}
	
	@Override
	public String toString() {
		return "{" + m11 + " " + m12 + " " + m13 + "} {" + m21 + " " + m22 + " " + m23 + "} {" + m31 + " " + m32 + " " + m33 + "}";
	}
}
