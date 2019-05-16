package space.engine.vector;

public class Vector3f {
	
	public float x;
	public float y;
	public float z;
	
	public Vector3f() {
	}
	
	@SuppressWarnings("CopyConstructorMissesField")
	public Vector3f(Vector3f vector) {
		set(vector);
	}
	
	public Vector3f(float[] array, int offset) {
		set(array, offset);
	}
	
	public Vector3f(float x, float y, float z) {
		set(x, y, z);
	}
	
	public Vector3f set(Vector3f vec) {
		return set(vec.x, vec.y, vec.z);
	}
	
	public Vector3f set(float[] array, int offset) {
		return set(array[offset], array[offset + 1], array[offset + 2]);
	}
	
	public Vector3f set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}
	
	public Vector3f clear() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
		return this;
	}
	
	public Vector3f add(Vector3f vec) {
		this.x += vec.x;
		this.y += vec.y;
		this.z += vec.z;
		return this;
	}
	
	public Vector3f add(float x, float y, float z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}
	
	public Vector3f sub(Vector3f vec) {
		this.x -= vec.x;
		this.y -= vec.y;
		this.z -= vec.z;
		return this;
	}
	
	public Vector3f sub(float x, float y, float z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
		return this;
	}
	
	public Vector3f multiply(float scalar) {
		this.x *= scalar;
		this.y *= scalar;
		this.z *= scalar;
		return this;
	}
	
	public Vector3f multiply(Matrix3f mat) {
		return set(
				mat.m11 * x + mat.m12 * y + mat.m13 * z,
				mat.m21 * x + mat.m22 * y + mat.m23 * z,
				mat.m31 * x + mat.m32 * y + mat.m33 * z
		);
	}
	
	public Vector3f multiply(Matrix4f mat) {
		float w = mat.m41 * x + mat.m42 * y + mat.m43 * z + mat.m44;
		return set(
				(mat.m11 * x + mat.m12 * y + mat.m13 * z + mat.m14) / w,
				(mat.m21 * x + mat.m22 * y + mat.m23 * z + mat.m24) / w,
				(mat.m31 * x + mat.m32 * y + mat.m33 * z + mat.m34) / w
		);
	}
	
	public Vector3f divide(float scalar) {
		this.x /= scalar;
		this.y /= scalar;
		this.z /= scalar;
		return this;
	}
	
	public Vector3f inverse() {
		x = -x;
		y = -y;
		z = -z;
		return this;
	}
	
	public Vector3f abs() {
		x = Math.abs(x);
		y = Math.abs(y);
		z = Math.abs(z);
		return this;
	}
	
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}
	
	public float lengthSquared() {
		return x * x + y * y + z * z;
	}
	
	public Vector3f normalize() {
		return divide(length());
	}
	
	public Vector3f cross(Vector3f vec1, Vector3f vec2) {
		return set(
				vec1.y * vec2.z - vec2.y * vec1.z,
				vec1.z * vec2.x - vec2.z * vec1.x,
				vec1.x * vec2.y - vec2.x * vec1.y
		);
	}
	
	public static float distance(Vector3f from, Vector3f to) {
		float x = to.x - from.x;
		float y = to.y - from.y;
		float z = to.z - from.z;
		return (float) Math.sqrt(x * x + y * y + z * z);
	}
	
	public static float dot(Vector3f vec1, Vector3f vec2) {
		return vec1.x * vec2.x + vec1.y * vec2.y + vec1.z * vec2.z;
	}
	
	public float[] write(float[] array, int offset) {
		array[offset] = x;
		array[offset + 1] = y;
		array[offset + 2] = z;
		return array;
	}
	
	@Override
	public String toString() {
		return "{" + x + " " + y + " " + z + "}";
	}
}
