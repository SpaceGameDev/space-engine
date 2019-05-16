package space.engine.vector;

import static java.lang.Math.*;

/**
 * The Quaternion is always normalized.
 */
public class Quaternionf {
	
	public float x, y, z, w;
	
	public Quaternionf() {
		identity();
	}
	
	@SuppressWarnings("CopyConstructorMissesField")
	public Quaternionf(Quaternionf quaternion) {
		set(quaternion);
	}
	
	public Quaternionf(float[] array, int offset) {
		set(array, offset);
	}
	
	public Quaternionf(float x, float y, float z, float w) {
		set(x, y, z, w);
	}
	
	public Quaternionf set(Quaternionf q) {
		return set(x, y, z, w);
	}
	
	public Quaternionf set(float[] array, int offset) {
		return set(array[offset], array[offset + 1], array[offset + 2], array[offset + 3]);
	}
	
	public Quaternionf set(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		return this;
	}
	
	public Quaternionf identity() {
		return set(0, 0, 0, 1);
	}
	
	public Quaternionf conj() {
		x = -x;
		y = -y;
		z = -z;
		return this;
	}
	
	public Quaternionf multiply(AxisAndAnglef axisAndAngle) {
		return multiply(axisAndAngle.toQuaternion(new Quaternionf()));
	}
	
	public Quaternionf multiply(Quaternionf quaternion) {
		return set(
				this.x * quaternion.w + this.w * quaternion.x + this.y * quaternion.z - this.z * quaternion.y,
				this.y * quaternion.w + this.w * quaternion.y + this.z * quaternion.x - this.x * quaternion.z,
				this.z * quaternion.w + this.w * quaternion.z + this.x * quaternion.y - this.y * quaternion.x,
				this.w * quaternion.w - this.x * quaternion.x - this.y * quaternion.y - this.z * quaternion.z
		);
	}
	
	public Matrix3f toMatrix3(Matrix3f mat) {
		float xx = x * x * 2;
		float xy = x * y * 2;
		float xz = x * z * 2;
		float xw = x * w * 2;
		float yy = y * y * 2;
		float yz = y * z * 2;
		float yw = y * w * 2;
		float zz = z * z * 2;
		float zw = z * w * 2;
		
		return mat.set(
				1 - yy - zz, xy - zw, xz + yw,
				xy + zw, 1 - xx - zz, yz - xw,
				xz - yw, yz + xw, 1 - xx - yy
		);
	}
	
	public Matrix4f toMatrix4(Matrix4f mat) {
		float xx = x * x * 2;
		float xy = x * y * 2;
		float xz = x * z * 2;
		float xw = x * w * 2;
		float yy = y * y * 2;
		float yz = y * z * 2;
		float yw = y * w * 2;
		float zz = z * z * 2;
		float zw = z * w * 2;
		
		return mat.set(
				1 - yy - zz, xy - zw, xz + yw, 0,
				xy + zw, 1 - xx - zz, yz - xw, 0,
				xz - yw, yz + xw, 1 - xx - yy, 0,
				0, 0, 0, 1
		);
	}
	
	public static float dot(Quaternionf q1, Quaternionf q2) {
		return q1.w * q2.w + q1.x * q2.x + q1.y * q2.y + q1.z * q2.z;
	}
	
	public static final float SLERP_THRESHOLD = 0.001f;
	
	public Quaternionf slerp(Quaternionf q1, Quaternionf q2, float t) {
		float cosHalfAngle = dot(q1, q2);
		if (abs(cosHalfAngle) >= 1) {
			//quaternions are equal, so no change
			if (this != q1 && this != q2)
				set(q1);
			return this;
		}
		
		float halfAngle = (float) acos(cosHalfAngle);
		float sinHalfAngle = (float) sqrt(1 - (cosHalfAngle * cosHalfAngle));
		
		float m1;
		float m2;
		if (abs(sinHalfAngle) < SLERP_THRESHOLD) {
			m1 = 0.5f;
			m2 = 0.5f;
		} else {
			m1 = (float) sin((1 - t) * halfAngle) / sinHalfAngle;
			m2 = (float) sin(t * halfAngle) / sinHalfAngle;
		}
		
		return set(
				q1.x * m1 + q2.x * m2,
				q1.y * m1 + q2.y * m2,
				q1.z * m1 + q2.z * m2,
				q1.w * m1 + q2.w * m2
		);
	}
	
	public float[] write(float[] array, int offset) {
		array[offset] = x;
		array[offset + 1] = y;
		array[offset + 2] = z;
		array[offset + 3] = w;
		return array;
	}
	
	@Override
	public String toString() {
		return "{q " + x + " " + y + " " + z + " " + w + "}";
	}
}
