package com.example.simplemeditation

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {

    private var mp: MediaPlayer? = null
    private var currentBell = mutableListOf(R.raw.bell)
    private var countDownTimer : CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startButton = findViewById<Button>(R.id.startButton)
        val resultsTextView = findViewById<TextView>(R.id.resultsTextView)
        val seekBar = findViewById<SeekBar>(R.id.seekBar)
        val selectionTextView = findViewById<TextView>(R.id.selectionTextView)
        selectionTextView.text = seekBar.progress.toString()

        seekBar?.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar,
                                           progress: Int, fromUser: Boolean) {
                val selectionTextView = findViewById<TextView>(R.id.selectionTextView)
                selectionTextView.text = seekBar.progress.toString()
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                val selectionTextView = findViewById<TextView>(R.id.selectionTextView)
                selectionTextView.text = seekBar.progress.toString()
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                val selectionTextView = findViewById<TextView>(R.id.selectionTextView)
                selectionTextView.text = seekBar.progress.toString()
            }
        })
        fun playBell(id: Int) {
            if (mp == null) {
                mp = MediaPlayer.create(this, id)
                Log.d("MainActivity", "ID: ${mp!!.audioSessionId}" )
            }
            mp?.start()
            Log.d("MainActivity", "Duration: ${mp!!.duration/1000} seconds")
        }

        fun startCounter(secondsRemaining: Long) {
            countDownTimer = object : CountDownTimer(secondsRemaining * 60000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    var diff = millisUntilFinished
                    val secondsInMilli: Long = 1000
                    val minutesInMilli = secondsInMilli * 60

                    val elapsedMinutes = diff / minutesInMilli
                    diff %= minutesInMilli

                    val elapsedSeconds = diff / secondsInMilli

                    if (elapsedSeconds < 10 ) {
                        resultsTextView.text = "$elapsedMinutes : 0$elapsedSeconds"
                    } else {
                        resultsTextView.text = "$elapsedMinutes : $elapsedSeconds"
                    }
                }

                override fun onFinish() {
                    resultsTextView.text = "Namaste"
                    playBell(id = currentBell[0])
                }
            }.start()
        }

        fun stopCountDownTimer() {
            if ( countDownTimer != null) {
                countDownTimer!!.cancel()
            }
        }

        startButton.setOnClickListener {
            stopCountDownTimer()
            playBell(id = currentBell[0])
            startCounter(secondsRemaining = seekBar.progress.toLong())
        }


    }
}