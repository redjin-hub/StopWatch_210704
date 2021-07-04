package com.redjin.stopwatch_210704

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {

    private var time = 0
    private var isRunning = false
    private var timerTask: Timer? = null
    private var lap = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener{
            isRunning = !isRunning

            if(isRunning){
                start()
            }else{
                pause()
            }
        }

        lapButton.setOnClickListener{
            recordLapTime()
        }

        resetFab.setOnClickListener {
            reset()
        }

    }

    private fun pause(){
        fab.setImageResource(R.drawable.ic_play)
        timerTask?.cancel()
    }

    private fun start(){
        fab.setImageResource(R.drawable.ic_pause)

        timerTask = timer(period = 10){
            time++
            val sec = time / 100
            val milli = time % 100
            runOnUiThread {
                secTextView.text = "$sec"
                milliTextView.text = "$milli"
            }
        }
    }

    private fun recordLapTime(){
        val lapTime = this.time
        val textView = TextView(this)
        textView.text = "$lap Lap : ${lapTime / 100}.${lapTime % 100}"

        //맨 위에 랩타임 추가
        lapLayout.addView(textView, 0)
        lap++
    }

    private fun reset(){
        timerTask?.cancel()

        //모든 변수 초기화
        time = 0
        isRunning = false
        fab.setImageResource(R.drawable.ic_play)
        secTextView.text = "0"
        milliTextView.text = "00"

        //모든 랩타임 제거
        lapLayout.removeAllViews()
        lap = 1
    }
}