package cn.edu.mydotabuff.common;

public enum Orientation_Sensor {
	DEGREE_0("DEGREE_0"), DEGREE_90("DEGREE_90"), DEGREE_180("DEGREE_180"), DEGREE_270(
			"DEGREE_270"), DEGREE_ERROR("DEGREE_ERROR"), ;
	private String TypeCode;

	/**
	 * ����һ������ָ���ַ��ö��ֵ
	 * 
	 * @param inTypeCode
	 */
	private Orientation_Sensor(String inTypecode) {
		this.TypeCode = inTypecode;
	}

	@Override
	public String toString() {
		return TypeCode;
	}

	public static Orientation_Sensor getMsgType(String inTypeCode) {
		for (Orientation_Sensor type : Orientation_Sensor.values()) {
			if (type.TypeCode.equals(inTypeCode))
				return type;
		}
		return null;
	}

	public static Orientation_Sensor getOrientationSensor(float inclination,
			float rotation) {
		Orientation_Sensor mOrientation_Sensor = Orientation_Sensor.DEGREE_ERROR;
		float inaccuracy = 50;
		float inaccuracy_horizontal = 50;
		float boundary = 0;
		if (inaccuracy > 0 && inaccuracy < 90) {
			if (inclination > 0) {
				boundary = 90;
				if (boundary - inaccuracy <= inclination
						&& inclination <= boundary + inaccuracy) {
					mOrientation_Sensor = Orientation_Sensor.DEGREE_180;
				}
			} else {
				boundary = -90;
				if (boundary - inaccuracy <= inclination
						&& inclination <= boundary + inaccuracy) {
					mOrientation_Sensor = Orientation_Sensor.DEGREE_0;
				}
			}
			if (rotation > 0) {
				boundary = 90;
				if (boundary - inaccuracy_horizontal <= rotation
						&& rotation <= boundary + inaccuracy_horizontal) {
					mOrientation_Sensor = Orientation_Sensor.DEGREE_270;
				}
			} else {
				boundary = -90;
				if (boundary - inaccuracy_horizontal <= rotation
						&& rotation <= boundary + inaccuracy_horizontal) {
					mOrientation_Sensor = Orientation_Sensor.DEGREE_90;
				}
			}
		}
		return mOrientation_Sensor;
	}
}
