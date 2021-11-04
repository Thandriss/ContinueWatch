package ru.spbstu.icc.kspt.lab2.continuewatch

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var secondsElapsed: Int = 0
    lateinit var textSecondsElapsed: TextView
    private lateinit var prefs: SharedPreferences
    var flagOfStop: Boolean = false
    var backgroundThread = Thread {
        while (true) {
            if (!flagOfStop){
                Thread.sleep(1000)
                textSecondsElapsed.post {
                    textSecondsElapsed.text = String.format(getString(R.string.seconds), secondsElapsed++)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //prefs = getPreferences(Context.MODE_PRIVATE)//закомментировать если нужно решение для onSaveInstanceState/onRestoreInstanceState
        //secondsElapsed = prefs.getInt(TAG, Context.MODE_PRIVATE) //закомментировать если нужно решение для onSaveInstanceState/onRestoreInstanceState
        setContentView(R.layout.activity_main)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        backgroundThread.start()
        Log.i(TAG, "onCreate")
    }

    override fun onStop() {
        flagOfStop = true
        super.onStop()
        //prefs.edit().putInt(TAG, secondsElapsed).apply()//закомментировать если нужно решение для onSaveInstanceState/onRestoreInstanceState
        Log.i(TAG, "onStop")
        //Log.i(TAG, "${prefs.getInt(TAG, Context.MODE_PRIVATE)}")//закомментировать если нужно решение для onSaveInstanceState/onRestoreInstanceState
        Log.i(TAG, "$secondsElapsed")
    }

    override fun onStart() {
        flagOfStop = false
        //secondsElapsed = prefs.getInt(TAG, Context.MODE_PRIVATE)//закомментировать если нужно решение для onSaveInstanceState/onRestoreInstanceState
        super.onStart()
    }

    companion object {
        const val TAG = "MainActivity"
    }

    //для решения с onSaveInstanceState/onRestoreInstanceState раскомментировать

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run { putInt(TAG, secondsElapsed) }
        Log.i(TAG, outState.getInt(TAG).toString())
        super.onSaveInstanceState(outState)
        Log.i(TAG, "onSaveInstanceState")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        Log.i(TAG, savedInstanceState.getInt(TAG).toString())
        secondsElapsed = savedInstanceState.getInt(TAG)
        super.onRestoreInstanceState(savedInstanceState)
        Log.i(TAG, "onRestoreInstanceState")
    }
}
