package de.co.derakhshan.hamdad.all_event

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import de.co.derakhshan.hamdad.R
import kotlinx.android.synthetic.main.activity_filter.*

class FilterActivity : AppCompatActivity() {

    private var filterList: FilterItems? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)

        filterList = intent.getParcelableExtra("filters")

        filterList?.let {
            it.name?.let { filter ->
                search.setText(filter)
            }
            it.justOpen?.let { check ->
                open.isChecked = check
            }
            it.justParticipated?.let { check ->
                registered.isChecked = check
            }
        }

        back.setOnClickListener {
            onBackPressed()
        }

    }


    override fun onBackPressed() {
        filterList = FilterItems(
            name = search.text?.let { search.text.toString() },
            justOpen = if (open.isChecked) true else null,
            justParticipated = if (registered.isChecked) true else null
        )
        val intent = Intent(this, EventListActivity::class.java)
        intent.putExtra("filters", filterList)
        setResult(Activity.RESULT_OK, intent)
        finish()
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        super.onBackPressed()
    }
}
