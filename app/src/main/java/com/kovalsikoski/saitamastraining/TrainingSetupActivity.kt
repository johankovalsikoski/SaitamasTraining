package com.kovalsikoski.saitamastraining

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.warkiz.tickseekbar.OnSeekChangeListener
import com.warkiz.tickseekbar.SeekParams
import com.warkiz.tickseekbar.TickSeekBar
import kotlinx.android.synthetic.main.activity_training_setup.*
import java.text.SimpleDateFormat
import java.util.*

class TrainingSetupActivity : AppCompatActivity() {

    private lateinit var series: TickSeekBar
    private lateinit var pushups: TickSeekBar
    private lateinit var situps: TickSeekBar
    private lateinit var squats: TickSeekBar
    private lateinit var running: TickSeekBar

    private lateinit var trainingModel: TrainingModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_training_setup)

        series = seekbar_series
        pushups = seekbar_pushups
        situps = seekbar_situps
        squats = seekbar_squats
        running = seekbar_run

        trainingModel = TrainingModel()

        series.onSeekChangeListener = object : OnSeekChangeListener{

            override fun onSeeking(seekParams: SeekParams?) {}

            override fun onStartTrackingTouch(seekBar: TickSeekBar?) {}

            override fun onStopTrackingTouch(seekBar: TickSeekBar?) {
                if(seekBar != null)
                    reconfigureToXSeries(seekBar.progress)
            }
        }

        btn_start.setOnClickListener {

            val dateFormat = SimpleDateFormat.getDateInstance()
            trainingModel.date = dateFormat.format(Calendar.getInstance().time)
            trainingModel.series = series.progress
            trainingModel.pushupsRequired = pushups.progress
            trainingModel.situpsRequired = situps.progress
            trainingModel.squatsRequired = squats.progress
            trainingModel.runningRequired = running.progress

            val app = application as SaitamaTrainingApplication

            app.setTraining(trainingModel)

            startActivity(Intent(this, TrainingActivity::class.java))
            finish()
        }

    }

    private fun reconfigureToXSeries(series: Int){
        when(series){

            2 -> {
                pushups.max = 50f
                pushups.min = 5f
                pushups.tickCount = 10

                situps.max = 50f
                situps.min = 5f
                situps.tickCount = 10

                squats.max = 50f
                squats.min = 5f
                squats.tickCount = 10
            }

            4 -> {
                pushups.max = 25f
                pushups.min = 5f
                pushups.tickCount = 5

                situps.max = 25f
                situps.min = 5f
                situps.tickCount = 5

                squats.max = 25f
                squats.min = 5f
                squats.tickCount = 5
            }

            5 -> {
                pushups.max = 20f
                pushups.min = 4f
                pushups.tickCount = 5

                situps.max = 20f
                situps.min = 4f
                situps.tickCount = 5

                squats.max = 20f
                squats.min = 4f
                squats.tickCount = 5
            }
        }
    }

}