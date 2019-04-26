package com.kovalsikoski.saitamastraining

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_training.*
import java.util.concurrent.TimeUnit

class TrainingActivity : AppCompatActivity() {

    private lateinit var trainingModel: TrainingModel
    private var isOver = false
    private var isLast = false
    private var round = 1
    private var exercise = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_training)

        val app = application as SaitamaTrainingApplication

        trainingModel = app.getTraining()

        tv_exercise_message.text = "Do: ${trainingModel.pushupsRequired} Push-ups \n serie: $round/${trainingModel.series}"

        btn_start.setOnClickListener {
            btn_start.isEnabled = false

            if(!isOver)
                startTimer(exercise)
            else
                save()
        }

    }

    private fun save(){

        val realm = Realm.getDefaultInstance()

        val realmId = realm.where(TrainingModel::class.java).max("id")
        val nextId = if(realmId == null){
            0
        } else {
            realmId.toInt() + 1
        }

        realm.beginTransaction()

        val realmObject = realm.createObject(TrainingModel::class.java, nextId)
        realmObject.series = trainingModel.series
        realmObject.date = trainingModel.date
        realmObject.pushupsRequired = trainingModel.pushupsRequired
        realmObject.situpsRequired = trainingModel.situpsRequired
        realmObject.squatsRequired = trainingModel.squatsRequired
        realmObject.runningRequired = trainingModel.runningRequired

        realm.commitTransaction()

        Toast.makeText(this, "SAVED",Toast.LENGTH_LONG).show()
        startActivity(Intent(this, DashboardActivity::class.java))
        finish()

    }

    private fun startTimer(e: Int) {

        var time = 0L
        var initTimer = false

        if(round <= trainingModel.series){

            initTimer = true

            when(exercise) {

                1 -> {

                    time = (4000 * trainingModel.pushupsRequired).toLong()

                }

                2 -> {

                    time = (4000 * trainingModel.pushupsRequired).toLong()

                }

                3 -> {

                    time = (4000 * trainingModel.pushupsRequired).toLong()

                }

                4 -> {

                    time = ( (60 * 6 ) * trainingModel.runningRequired).toLong()

                }
            }
        }

        if(initTimer){

            object: CountDownTimer(time,1000){
                override fun onTick(millisUntilFinished: Long) {
                    chronometer.text = String.format("%02d : %02d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))
                    )
                    crpv.percent = ( ( ( millisUntilFinished * 100 / time ) - 100 ) * -1 ).toFloat()
                }

                override fun onFinish() {
                    crpv.percent = 100F
                    chronometer.text = "Done!"

                    btn_start.isEnabled = true

                    if (isLast) {
                        isOver = true
                        tv_exercise_message.text = "Go get some rest :)"
                        btn_start.text = "Save & Exit"
                    } else {

                        var run = false

                        if (this@TrainingActivity.round == trainingModel.series && e == 3) {
                            run = true
                        } else if (this@TrainingActivity.round != trainingModel.series && e == 3) {
                            btn_start.text = "Continue"
                            exercise = 1
                            this@TrainingActivity.round++
                        } else {
                            btn_start.text = "Continue"
                            exercise++
                        }

                        if (!run) {
                            val eMessage = when (exercise) {
                                1 -> "Push-ups"
                                2 -> "Sit-ups"
                                else -> "Squats"
                            }

                            val qMessage = when (exercise) {
                                1 -> trainingModel.pushupsRequired
                                2 -> trainingModel.situpsRequired
                                else -> trainingModel.squatsRequired
                            }

                            tv_exercise_message.text =
                                "Do: $qMessage $eMessage \n serie: ${this@TrainingActivity.round}/${trainingModel.series}"

                        } else if (run && !isLast) {
                            tv_exercise_message.text = "Run ${trainingModel.runningRequired}km"
                            exercise = 4
                            isLast = true
                        }

                    }
                }
            }.start()

        }

    }
}