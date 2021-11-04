package ru.spbstu.icc.kspt.lab2.continuewatch

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivityOnSRInstance {
    class MainActivity : AppCompatActivity() {
        var secondsElapsed: Int = 0
        lateinit var textSecondsElapsed: TextView
        var save: Int = 0
        var backgroundThread = Thread {
            while (true) {
                Thread.sleep(1000)
                textSecondsElapsed.post {
                    textSecondsElapsed.text = String.format(getString(R.string.seconds), secondsElapsed++)
                }
            }
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
            backgroundThread.start()
            Log.i(TAG, "onCreate")
        }

        override fun onRestart() {
            super.onRestart()
            Log.i(TAG, "onRestart")
        }

        override fun onStart() {
            super.onStart()
            Log.i(TAG,"onStart")
        }

        override fun onSaveInstanceState(outState: Bundle) {
            outState.run { putInt(TAG, secondsElapsed) }
            Log.i(TAG, outState.getInt(TAG).toString())
            save = outState.getInt(TAG)
            super.onSaveInstanceState(outState)
            Log.i(TAG, "onSaveInstanceState")
        }

        override fun onStop() {
            super.onStop()
            Log.i(TAG, "onStop")
        }

        override fun onResume() {
            secondsElapsed = save
            super.onResume()
            Log.i(TAG, "onResume")
        }

        override fun onDestroy() {
            super.onDestroy()
            Log.i(TAG, "onDestroy")
        }

        override fun onPause() {
            save = secondsElapsed
            super.onPause()
            Log.i(TAG, "onPause")
        }

        companion object {
            const val TAG = "MainActivity"
        }

        override fun onRestoreInstanceState(savedInstanceState: Bundle) {
            Log.i(TAG, savedInstanceState.getInt(TAG).toString())
            save = savedInstanceState.getInt(TAG)
            super.onRestoreInstanceState(savedInstanceState)
            Log.i(TAG, "onRestoreInstanceState")
        }

    }

}