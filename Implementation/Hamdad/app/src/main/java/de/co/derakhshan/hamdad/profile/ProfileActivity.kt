package de.co.derakhshan.hamdad.profile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import de.co.derakhshan.hamdad.R
import de.co.derakhshan.hamdad.Utils
import de.co.derakhshan.hamdad.all_event.EventListActivity
import de.co.derakhshan.hamdad.archive.ArchiveActivity
import de.co.derakhshan.hamdad.database.EventDatabase
import de.co.derakhshan.hamdad.databinding.ActivityProfileBinding
import de.co.derakhshan.hamdad.event.EventDetailActivity
import de.co.derakhshan.hamdad.event.MessageTransformer
import de.co.derakhshan.hamdad.score.ScoreActivity

class ProfileActivity : AppCompatActivity(), MessageTransformer {

    companion object {
        var HasUpdatedEvents = false
        var HasUpdatedScores = false
        var HasUpdatedArchive = false
    }

    private lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)

        val adapter = ReminderAdapter()
        adapter.eventClickListener = this

        val database = EventDatabase.getInstance(context = this)
        val factory = ProfileViewModelFactory(
            context = this,
            database = database
        )
        val viewModel = ViewModelProvider(this, factory).get(ProfileViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.status = this
        viewModel.reminderItems.observe(this, Observer {
            it?.let {
                adapter.addData(it)
            }

        })
        viewModel.userInfo.observe(this, Observer {
            it?.let {
                viewModel.setUserData()
            }
        })
        viewModel.updateReminder()



        binding.content.reminderPager.adapter = adapter
        binding.content.reminderPager.isNestedScrollingEnabled = false
        binding.content.reminderPager.getChildAt(0)?.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        binding.content.reminderPager.setPageTransformer(DepthPageTransformer())
        binding.content.dots.setViewPager2(binding.content.reminderPager)
        binding.content.reminderPager.offscreenPageLimit = 10
        binding.content.reminderPager.isClickable = false




        binding.profile.setOnClickListener {
            Utils(this).isLoggedIn = false
        }
        binding.events.setOnClickListener {
            startActivity(Intent(this, EventListActivity::class.java))
            overridePendingTransition(R.anim.fade_in, R.anim.no_effect)
        }
        binding.content.archive.setOnClickListener {
            startActivity(Intent(this, ArchiveActivity::class.java))
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
        binding.content.score.setOnClickListener {
            startActivity(Intent(this, ScoreActivity::class.java))
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }


    }

    override fun transform(str: String, where: String) {
        when (str) {
            "500" -> {
                Utils(this).showSnackBar(
                    ContextCompat.getColor(this, R.color.black),
                    "خطا در بروزرسانی اطلاعات...", binding.root
                )
                YoYo.with(Techniques.FadeOut)
                    .duration(500)
                    .playOn(binding.loading)
                binding.loading.alpha = 0F
            }
            "200" -> {
                YoYo.with(Techniques.FadeOut)
                    .duration(500)
                    .playOn(binding.loading)
                binding.loading.alpha = 0F
            }
            else -> {
                val intent = Intent(this, EventDetailActivity::class.java)
                intent.putExtra("event_id", str)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
            }
        }
    }
}