package de.co.derakhshan.hamdad.event

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import de.co.derakhshan.hamdad.R
import de.co.derakhshan.hamdad.Utils
import de.co.derakhshan.hamdad.database.EventDatabase
import de.co.derakhshan.hamdad.databinding.ActivityEventBinding

class EventDetailActivity : AppCompatActivity(), MessageTransformer {
    lateinit var binding: ActivityEventBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_event)
        val database = EventDatabase.getInstance(this)
        val factory = EventViewModelFactory(database)
        val viewModel = ViewModelProvider(this, factory).get(EventViewModel::class.java)
        viewModel.clickListener = this
        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        intent.getStringExtra("event_id")?.let { id ->
            viewModel.getInfo(id).observe(this, Observer { event ->
                event?.let { data ->
                    viewModel.setInfo(data)
                }
            })
        }


        binding.back.setOnClickListener {
            onBackPressed()
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    override fun transform(str: String, where: String) {
        Utils(this).showSnackBar(
            ContextCompat.getColor(this, R.color.black),
            str, binding.root
        )
    }
}
