package com.kovalsikoski.saitamastraining

import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.cardview_training.view.*

class TrainingAdapter(private val trainingModel: TrainingModel, private val clickListenerInterface: ClickListenerInterface): Item<ViewHolder>(){

    override fun bind(viewHolder: ViewHolder, position: Int) {

        viewHolder.itemView.tv_date.text = "Date: ${trainingModel.date}"
        viewHolder.itemView.tv_series.text = "Series: ${trainingModel.series}"

        viewHolder.itemView.tv_pushups.text = "Push-ups: ${trainingModel.pushupsRequired}"
        viewHolder.itemView.tv_situps.text = "Sit-ups: ${trainingModel.situpsRequired}"

        viewHolder.itemView.tv_squats.text = "Squats: ${trainingModel.squatsRequired}"
        viewHolder.itemView.tv_run.text = "Running (km): ${trainingModel.runningRequired}"

        viewHolder.itemView.iv_delete.setOnClickListener {
            clickListenerInterface.message()
        }

        viewHolder.itemView.iv_delete.setOnLongClickListener {
            clickListenerInterface.delete(trainingModel.id, position)

            true
        }

    }

    override fun getLayout(): Int = R.layout.cardview_training

}