package com.example.myapplication

import android.annotation.SuppressLint
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var senseMan: SensorManager
    private lateinit var lv: ListView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lv = findViewById(R.id.listview)
        senseMan = getSystemService(SENSOR_SERVICE) as SensorManager

        val sensorList: List<Sensor> = senseMan.getSensorList(Sensor.TYPE_ALL)

        lv.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, sensorList)
    }
}