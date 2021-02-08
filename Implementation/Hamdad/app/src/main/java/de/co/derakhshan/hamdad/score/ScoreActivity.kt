package de.co.derakhshan.hamdad.score

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import de.co.derakhshan.hamdad.R
import de.co.derakhshan.hamdad.database.EventDatabase
import de.co.derakhshan.hamdad.databinding.ActivityScoreBinding
import de.co.derakhshan.hamdad.event.EventDetailActivity
import de.co.derakhshan.hamdad.event.MessageTransformer
import de.co.derakhshan.hamdad.profile.ProfileActivity.Companion.HasUpdatedScores

class ScoreActivity : AppCompatActivity(), MessageTransformer {
    lateinit var binding: ActivityScoreBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = EventDatabase.getInstance(this).bestEvenDao
        val factory = ScoreFactory(database = database, context = this)
        val viewModel = ViewModelProvider(this, factory).get(ScoreViewModel::class.java)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_score)
        val eventAdapter = ScoreAdapter()
        val executorAdapter = ScoreAdapter()
        eventAdapter.clickListener = this
        executorAdapter.clickListener = this

        binding.eventRecycler.adapter = eventAdapter
        binding.eventRecycler.setHasFixedSize(true)
        binding.eventRecycler.setItemViewCacheSize(10)
        binding.eventRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)


        binding.executorRecycler.adapter = executorAdapter
        binding.executorRecycler.setHasFixedSize(true)
        binding.executorRecycler.setItemViewCacheSize(10)
        binding.executorRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        if (HasUpdatedScores) {
            binding.loading.visibility = View.GONE
            binding.mainHolder.visibility = View.VISIBLE
        } else
            viewModel.update()
        viewModel.events.observe(this, Observer {
            HasUpdatedScores = true
            binding.loading.visibility = View.GONE
            binding.mainHolder.visibility = View.VISIBLE
            eventAdapter.add(it)
        })
        viewModel.executor.observe(this, Observer {
            executorAdapter.add(it)
        })

        binding.back.setOnClickListener {
            onBackPressed()
        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    override fun transform(str: String, where: String) {
        if (where != "event")
            return
        val intent = Intent(this, EventDetailActivity::class.java)
        intent.putExtra("event_id", str)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)

    }
}
