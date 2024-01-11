package com.example.lab7task3

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private lateinit var accelerometer: Sensor
    private lateinit var gyroscope: Sensor
    private lateinit var magnetometer: Sensor

    private lateinit var xTextView: TextView
    private lateinit var yTextView: TextView
    private lateinit var zTextView: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        xTextView = findViewById(R.id.xTextView)
        yTextView = findViewById(R.id.yTextView)
        zTextView = findViewById(R.id.zTextView)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        // Accelerometer
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!!
        checkAndRegisterSensor(accelerometer, "Accelerometer")

        // Gyroscope
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)!!
        checkAndRegisterSensor(gyroscope, "Gyroscope")

        // Magnetometer
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)!!
        checkAndRegisterSensor(magnetometer, "Magnetometer")

    }

    private fun checkAndRegisterSensor(sensor: Sensor?, sensorName: String) {
        if (sensor != null) {
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        } else {
            println("$sensorName not found in device")
        }
    }

    override fun onSensorChanged(event: SensorEvent) {
        when (event.sensor.type) {
            Sensor.TYPE_ACCELEROMETER -> updateTextViews(event.values, "Accelerometer")
            Sensor.TYPE_GYROSCOPE -> updateTextViews(event.values, "Gyroscope")
            Sensor.TYPE_MAGNETIC_FIELD -> updateTextViews(event.values, "Magnetometer")
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // Not used in this example
    }

    private fun updateTextViews(values: FloatArray, sensorName: String) {
        xTextView.text = "$sensorName - X: ${values[0]}"
        yTextView.text = "$sensorName - Y: ${values[1]}"
        zTextView.text = "$sensorName - Z: ${values[2]}"
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onResume() {
        super.onResume()
        checkAndRegisterSensor(accelerometer, "Accelerometer")
        checkAndRegisterSensor(gyroscope, "Gyroscope")
        checkAndRegisterSensor(magnetometer, "Magnetometer")
    }
}