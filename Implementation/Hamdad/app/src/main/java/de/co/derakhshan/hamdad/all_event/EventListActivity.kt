package de.co.derakhshan.hamdad.all_event

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import de.co.derakhshan.hamdad.R
import de.co.derakhshan.hamdad.database.EventDatabase
import de.co.derakhshan.hamdad.databinding.ActivityEventListBinding
import de.co.derakhshan.hamdad.event.EventDetailActivity
import de.co.derakhshan.hamdad.event.MessageTransformer
import de.co.derakhshan.hamdad.profile.ProfileActivity.Companion.HasUpdatedEvents

class EventListActivity : AppCompatActivity(), MessageTransformer {
    private lateinit var viewModel: AllEventViewModel
    private var filterList: FilterItems? = null
    private val filterRequest = 123
    private lateinit var binding: ActivityEventListBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_event_list)

        val database = EventDatabase.getInstance(this)
        val factory = AllEventFactory(database, this)
        viewModel = ViewModelProvider(this, factory).get(AllEventViewModel::class.java)

        val adapter = AllEventAdapter()
        adapter.eventClickListener = this



        viewModel.filters.observe(this, Observer {
            viewModel.filterData().observe(this, Observer { list ->
                list?.let { info ->
                    adapter.add(info)
                }
            })
        })

        if (!HasUpdatedEvents)
            viewModel.getEvents()
        else {
            binding.recyclerView.visibility = View.VISIBLE
            binding.loading.visibility = View.GONE
        }

        viewModel.updateInfo.observe(this, Observer {
            if (it) {
                binding.recyclerView.visibility = View.VISIBLE
                binding.loading.visibility = View.GONE

                YoYo.with(Techniques.FadeIn)
                    .duration(500)
                    .playOn(binding.recyclerView)
                HasUpdatedEvents = true
            }
        })


        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.adapter = adapter
        binding.executePendingBindings()

        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.filter.setOnClickListener {
            val intent = Intent(this, FilterActivity::class.java)
            if (filterList != null)
                intent.putExtra("filters", filterList)
            startActivityForResult(intent, filterRequest)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
    }

    override fun transform(str: String, where: String) {
        val intent = Intent(this, EventDetailActivity::class.java)
        intent.putExtra("event_id", str)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.no_effect, R.anim.fade_out)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == filterRequest && resultCode == Activity.RESULT_OK) {
            filterList = data?.getParcelableExtra("filters")
            viewModel.filters.value = filterList
        }
    }
}
