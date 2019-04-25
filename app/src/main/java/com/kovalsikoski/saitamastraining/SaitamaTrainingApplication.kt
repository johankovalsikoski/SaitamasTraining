package com.kovalsikoski.saitamastraining

import android.app.Application
import io.realm.Realm

class SaitamaTrainingApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
    }

    private lateinit var trainingModel: TrainingModel

    fun setTraining(trainingModel: TrainingModel){
        this.trainingModel = trainingModel
    }

    fun getTraining() = trainingModel


}