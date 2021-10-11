package ru.spbstu.icc.kspt.lab2.continuewatch

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var secondsElapsed: Int = 0
    lateinit var textSecondsElapsed: TextView
    private lateinit var prefs: SharedPreferences
    var backgroundThread = Thread {
        while (true) {
            Thread.sleep(1000)
            textSecondsElapsed.post {
                textSecondsElapsed.setText("Seconds elapsed: " + secondsElapsed++)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = getPreferences(Context.MODE_PRIVATE)
        secondsElapsed = prefs.getInt(TAG, 1)
        setContentView(R.layout.activity_main)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        backgroundThread.start()
        Log.i(TAG, "onCreate")
    }

    override fun onRestart() {
        secondsElapsed = prefs.getInt(TAG, 1)
        super.onRestart()
        Log.i(TAG, "onRestart")
    }

    override fun onStop() {
        super.onStop()
        prefs.edit().putInt(TAG, secondsElapsed).apply()
        Log.i(TAG, "onStop")
        Log.i(TAG, "${prefs.getInt(TAG, 1)}")
        Log.i(TAG, "$secondsElapsed")
    }

    companion object {
        const val TAG = "MainActivity"
    }
}
