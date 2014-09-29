package cn.edu.mydotabuff.common;

import android.content.Context;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class OrientationHandler implements SensorEventListener {
	float accelX;
	float accelY;
	float accelZ;

	OnMySensorChangedListener onMySensorChangedListener = null;

	public void setOnMySensorChangedListener(
			OnMySensorChangedListener onMySensorChangedListener) {
		this.onMySensorChangedListener = onMySensorChangedListener;
	}

	public OrientationHandler(Context context) {
		SensorManager manager = (SensorManager) context
				.getSystemService(Context.SENSOR_SERVICE);
		if (manager.getSensorList(Sensor.TYPE_ORIENTATION).size() != 0) {
			Sensor accelerometer = manager.getSensorList(
					Sensor.TYPE_ORIENTATION).get(0);
			manager.registerListener(this, accelerometer,
					SensorManager.SENSOR_DELAY_NORMAL);
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		if (event.sensor.getType() != Sensor.TYPE_ORIENTATION
				|| event.values.length < 3)
			return;
		accelX = event.values[SensorManager.DATA_X];
		accelY = event.values[SensorManager.DATA_Y];
		accelZ = event.values[SensorManager.DATA_Z];
		if (onMySensorChangedListener != null) {
			onMySensorChangedListener.OnMySensorChanged(accelX, accelY, accelZ);
		}
	}

}
