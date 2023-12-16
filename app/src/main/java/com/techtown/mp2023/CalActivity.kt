package com.techtown.mp2023

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.TypeConverter
import com.techtown.mp2023.databinding.ActivityCalBinding
import java.util.Calendar
import java.util.Date

class CalActivity : AppCompatActivity() {

    lateinit var binding: ActivityCalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val calendar: Calendar = Calendar.getInstance()

        var date = binding.calendarView.date
        binding.calenderText.text = fromTimestamp(date).toString()

        binding.calendarView.setOnDateChangeListener{ _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            date = calendar.timeInMillis
            binding.calenderText.text = fromTimestamp(date).toString()
//            val date_String = "${year} - ${month + 1} - $dayOfMonth"
//            binding.calenderText.text = date_String
        }
    }

    @TypeConverter
    fun fromTimestamp(value: Long?) : Date? = value?.let { Date(it) }

    @TypeConverter
    fun dateToTimeStamp(date : Date?) : Long? = date?.time
}