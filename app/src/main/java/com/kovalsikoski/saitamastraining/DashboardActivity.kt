package com.kovalsikoski.saitamastraining

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val realm = Realm.getDefaultInstance()

        val all = realm.where(TrainingModel::class.java).findAll().sort("id")
        Log.d("abcz", "$all")

        val groupAdapter = GroupAdapter<ViewHolder>()

        all.forEach {
            groupAdapter.add(TrainingAdapter(it, object : ClickListenerInterface {
                override fun delete(id: Int, position: Int) {
                    realm.beginTransaction()
                    realm.where(TrainingModel::class.java).equalTo("id", id).findAll()
                        .deleteAllFromRealm()
                    realm.commitTransaction()

                    groupAdapter.removeGroup(position)
                }

                override fun message() {
                    Toast.makeText(this@DashboardActivity, "Hold to delete", Toast.LENGTH_LONG)
                        .show()
                }
            }))
        }

        recyclerview.layoutManager = GridLayoutManager(this@DashboardActivity, 1)
        recyclerview.adapter = groupAdapter

        fab.setOnClickListener { startActivity(Intent(this, TrainingSetupActivity::class.java)) }

    }
}
