package eu.tutorials.a7_minutesworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import eu.tutorials.a7_minutesworkoutapp.databinding.ActivityHistoryBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

private var binding: ActivityHistoryBinding? = null
class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarHistoryActivity)

        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "HISTORY"
        }
        binding?.toolbarHistoryActivity?.setNavigationOnClickListener(){
            onBackPressed()
        }

        val historyDao = (application as WorkoutApp).db.historyDao()
        getAllCompletedDates(historyDao)


    }

    private fun getAllCompletedDates(historyDao: HistoryDao){
        lifecycleScope.launch {
            historyDao.fetchAllDates().collect {    getAllCompletedDatesList ->
                if (getAllCompletedDatesList.isNotEmpty()){
                    binding?.tvHistory?.visibility = View.VISIBLE
                    binding?.rvHistory?.visibility = View.VISIBLE
                    binding?.tvNoDataAvailable?.visibility = View.INVISIBLE

                    binding?.rvHistory?.layoutManager = LinearLayoutManager(this@HistoryActivity)
                    val dates = ArrayList<String>()
                    for (date in getAllCompletedDatesList){
                        dates.add(date.date)
                    }
                    val historyAdapter = HistoryAdapter(dates)
                    binding?.rvHistory?.adapter = historyAdapter

                }else{
                    binding?.tvHistory?.visibility = View.GONE
                    binding?.rvHistory?.visibility = View.GONE
                    binding?.tvNoDataAvailable?.visibility = View.VISIBLE

                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }


}