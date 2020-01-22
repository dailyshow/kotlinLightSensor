package com.cis.kotlinlightsensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

// 조도 센서를 이용하면 주변 밝기를 측정할 수 있다.
class MainActivity : AppCompatActivity() {
    var manager: SensorManager? = null
    var listener: SensorListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        manager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        listener = SensorListener()

        startBtn.setOnClickListener {
            val sensor = manager?.getDefaultSensor(Sensor.TYPE_LIGHT)
            val chk = manager?.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI)

            if (chk == false) {
                tv.text = "조도 센서를 사용할 수 없습니다."
            }
        }

        stopBtn.setOnClickListener {
            manager?.unregisterListener(listener)
        }
    }

    inner class SensorListener: SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

        }

        override fun onSensorChanged(event: SensorEvent?) {
            if (event?.sensor?.type == Sensor.TYPE_LIGHT) {
                tv.text = "주변 밝기 : ${event?.values[0]} lux"
            }
        }

    }
}
