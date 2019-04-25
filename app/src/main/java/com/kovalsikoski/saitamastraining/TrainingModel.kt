package com.kovalsikoski.saitamastraining

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class TrainingModel(
    @PrimaryKey var id: Int = -1,
    var series: Int = 0, var date: String = "00/00/0000",
    var pushupsRequired: Int = 0, var situpsRequired: Int = 0,
    var squatsRequired: Int = 0, var runningRequired: Int = 0) : RealmObject()