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

        // EditText에 적힌 Text를 저장하는 runnable
        val runnable = Runnable {
            getSharedPreferences("diary", Context.MODE_PRIVATE).edit() {
                putString("detail",diaryBinding.diaryEditText.text.toString())
            }
        }

        diaryBinding.diaryEditText.setText(detailPreferences.getString("detail",""))

        // EditText에서 Text가 변경될 때 마다 불러오는 Listener
        diaryBinding.diaryEditText.addTextChangedListener {
            // 기존 runnable을 삭제 후 0.5초 뒤 텍스트를 저장할 runnable을 실행
            // Text를 계속 입력할 경우 저장하지 않고 runnable을 삭제하면서 실시간 저장을 막아준다.
            // Text를 다 입력하고 0.5초 동안 addTextChangedListener가 불리지 않을 경우 runnable 실행
            handler.removeCallbacks(runnable)
            handler.postDelayed(runnable, 500)  // 아무 입력이 없고 0.5초 후 자동으로 저장
        }

    }

}

/*
Handler
Thread


 */