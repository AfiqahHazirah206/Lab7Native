package com.example.lab7task2b;

import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager senseMan;
    Sensor lightSensor, proximitySensor, humiditySensor;
    TextView lightTextView, proximityTextView, humidityTextView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lightTextView = findViewById(R.id.lightTextView);
        proximityTextView = findViewById(R.id.proximityTextView);
        humidityTextView = findViewById(R.id.humidityTextView);

        senseMan = (SensorManager) getSystemService(SENSOR_SERVICE);

        // Light Sensor
        lightSensor = senseMan.getDefaultSensor(Sensor.TYPE_LIGHT);

        // Proximity Sensor
        proximitySensor = senseMan.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        // Relative Humidity Sensor
        humiditySensor = senseMan.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);

        checkAndRegisterSensor(lightSensor, "Light Sensor");
        checkAndRegisterSensor(proximitySensor, "Proximity Sensor");
        checkAndRegisterSensor(humiditySensor, "Humidity Sensor");
    }

    private void checkAndRegisterSensor(Sensor sensor, String sensorName) {
        if (sensor != null) {
            Toast.makeText(this, sensorName + " found", Toast.LENGTH_LONG).show();
            senseMan.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Toast.makeText(this, sensorName + " not found in device", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        switch (sensorEvent.sensor.getType()) {
            case Sensor.TYPE_LIGHT:
                lightTextView.setText("Light: " + sensorEvent.values[0]);
                break;
            case Sensor.TYPE_PROXIMITY:
                proximityTextView.setText("Proximity: " + sensorEvent.values[0]);
                break;
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                humidityTextView.setText("Humidity: " + sensorEvent.values[0]);
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not used in this example
    }

    @Override
    protected void onPause() {
        super.onPause();
        senseMan.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkAndRegisterSensor(lightSensor, "Light Sensor");
        checkAndRegisterSensor(proximitySensor, "Proximity Sensor");
        checkAndRegisterSensor(humiditySensor, "Humidity Sensor");
    }
}