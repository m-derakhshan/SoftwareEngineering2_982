package de.co.derakhshan.hamdad.archive

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import de.co.derakhshan.hamdad.R
import de.co.derakhshan.hamdad.database.EventDatabase
import de.co.derakhshan.hamdad.databinding.ActivityArchiveBinding
import de.co.derakhshan.hamdad.profile.ProfileActivity.Companion.HasUpdatedArchive

class ArchiveActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArchiveBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_archive)

        val database = EventDatabase.getInstance(this).archiveDao
        val factory = ArchiveFactory(database, this)
        val viewModel = ViewModelProvider(this, factory).get(ArchiveViewModel::class.java)
        val adapter = ArchiveAdapter()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter
        binding.back.setOnClickListener {
            onBackPressed()
        }


        if (HasUpdatedArchive) {
            binding.loading.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
        } else
            viewModel.update()
        viewModel.events.observe(this, Observer {
            adapter.add(it)
            if (!viewModel.isDataUpdate)
                return@Observer
            binding.loading.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
            HasUpdatedArchive = true

        })


    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}