package com.example.basic_ch03_secretdiary

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener
import com.example.basic_ch03_secretdiary.databinding.ActivityDiaryBinding

class DiaryActivity : AppCompatActivity() {

    private lateinit var diaryBinding: ActivityDiaryBinding

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        diaryBinding = ActivityDiaryBinding.inflate(layoutInflater)
        setContentView(diaryBinding.root)

        val detailPreferences = getSharedPreferences("diary", Context.MODE_PRIVATE)



        val runnable = Runnable {
            getSharedPreferences("diary", Context.MODE_PRIVATE).edit() {
                putString("detail",diaryBinding.diaryEditText.text.toString())
            }
        }

        diaryBinding.diaryEditText.setText(detailPreferences.getString("detail",""))

        diaryBinding.diaryEditText.addTextChangedListener {
            handler.removeCallbacks(runnable)
            handler.postDelayed(runnable, 500)  // 아무 입력이 없고 0.5초 후 자동으로 저장
        }

    }

}

/*
Handler
Thread


 */