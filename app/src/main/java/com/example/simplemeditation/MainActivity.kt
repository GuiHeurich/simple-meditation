package com.example.simplemeditation

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var mp: MediaPlayer
    private var currentBell = mutableListOf(R.raw.bell)
    private var countDownTimer : CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val beginButton: Button = findViewById<Button>(R.id.beginButton)
        val counterTextView: TextView = findViewById<TextView>(R.id.counterTextView)
        val minutesBar: SeekBar = findViewById<SeekBar>(R.id.minutesBar)

        minutesBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar,
                                           progress: Int, fromUser: Boolean) {
                val selectedValue = minutesBar.progress.toString()
                counterTextView.text = "$selectedValue : 00"
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                val selectedValue = minutesBar.progress.toString()
                counterTextView.text = "$selectedValue : 00"
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                val selectedValue = minutesBar.progress.toString()
                counterTextView.text = "$selectedValue : 00"
            }
        })
        fun playBell(id: Int) {
            mp = MediaPlayer.create(this, id)
            mp.start()
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
                        counterTextView.text = "$elapsedMinutes : 0$elapsedSeconds"
                    } else {
                        counterTextView.text = "$elapsedMinutes : $elapsedSeconds"
                    }
                }

                override fun onFinish() {
                    counterTextView.text = "Namaste"
                    playBell(id = currentBell[0])
                }
            }.start()
        }

        fun stopCountDownTimer() {
            if ( countDownTimer != null) {
                countDownTimer!!.cancel()
            }
        }

        beginButton.setOnClickListener {
            stopCountDownTimer()
            playBell(id = currentBell[0])
            startCounter(secondsRemaining = minutesBar.progress.toLong())
        }


    }
}