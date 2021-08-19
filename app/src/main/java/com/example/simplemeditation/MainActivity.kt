package com.example.simplemeditation

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {
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

        fun startCounter(secondsRemaining: Long) {
            object : CountDownTimer(secondsRemaining * 60000, 1000) {
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
                }
            }.start()
        }

//        fun playBell() {
//            var mediaPlayer = MediaPlayer.create(this, R.raw.pet)
//            mediaPlayer.start()
//        }

        startButton.setOnClickListener {
//            playBell()
            startCounter(secondsRemaining = seekBar.progress.toLong())
        }


    }
}