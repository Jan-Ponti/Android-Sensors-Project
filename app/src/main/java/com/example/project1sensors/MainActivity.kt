package com.example.project1sensors

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.project1sensors.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var light: Sensor? = null

    //Values used for the accelerometer
    private var x:Float = 0.0f
    private var y:Float = 0.0f
    private var z:Float = 0.0f

    //Value used for the light sensor
    private var lightVal:Float = 0.0f

    //Detecting whether sensor info changed
    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            when(event?.sensor) {
                accelerometer -> {
                    val x = event.values[0]
                    val y = event.values[1]
                    val z = event.values[2]
                }
                light -> {
                    val lightVal = event.values[0]
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        accelerometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
        light?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(binding.lightTextView)
        //setContentView(R.layout.activity_main)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        //accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        //light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        //val accelView = findViewById<TextView>(R.id.accelerometerTextView)
        //val lightView = findViewById<TextView>(R.id.lightTextView)
        //Display accelerometer info if detected

        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        //Output accelerometer info
        if (accelerometer != null) {
            val accelView = ("Accelerometer is Present\n"
                    + "Range: ${accelerometer?.maximumRange} m/s^2\n"
                    + "Resolution: ${accelerometer?.resolution} m/s^2\n"
                    + "Delay: ${accelerometer?.minDelay} microsecond(s)"
                    + "X:$x, Y:$y, Z:$z")

            binding.accelerometerTextView.text = accelView
        } else {
            binding.accelerometerTextView.text = "Accelerometer is not Present\n"
        }

        //Output light sensor info
        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        if (light != null) {
            val lightView = ("Light Sensor is Present\n"
                    + "Range: ${light?.resolution} 1x\n"
                    + "Resolution: ${light?.resolution} 1x\n"
                    + "Delay: ${light?.minDelay} microsecond(s)"
                    + "Light Value: $lightVal 1x")

            binding.lightTextView.text = lightView
        } else {
            binding.lightTextView.text = "Light Sensor is not Present\n"
        }

    }

    override fun onAccuracyChanged(event: Sensor?, accuracy: Int) {
        //This isn't needed
    }
}