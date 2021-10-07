package com.example.basic_ch03_secretdiary

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.edit
import com.example.basic_ch03_secretdiary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    private var changePasswordMode = false    // 비밀번호 변경 버튼 클릭 후 다른 처리를 막는 예외 처리용

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        mainBinding.numberPicker1.maxValue = 9
        mainBinding.numberPicker1.minValue = 0

        mainBinding.numberPicker2.maxValue = 9
        mainBinding.numberPicker2.minValue = 0

        mainBinding.numberPicker3.maxValue = 9
        mainBinding.numberPicker3.minValue = 0

        mainBinding.openBtn.setOnClickListener {

            if (changePasswordMode) {
                Toast.makeText(this , "비밀번호를 변경 중입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)

            val passwordFromUser = "${mainBinding.numberPicker1.value}${mainBinding.numberPicker2.value}${mainBinding.numberPicker3.value}"

            if (passwordPreferences.getString("password","000").equals(passwordFromUser))    // 키와 디폴트값 선언
            {
                // 패스워드 성공
                // Todo 다이어리 페이지 작성 후에 넘겨주어야함
                startActivity(Intent(this,DiaryActivity::class.java))
            } else {
                // 패스워드 실패
                showErrorAlertDialog()
            }
        }

        mainBinding.changePasswordBtn.setOnClickListener {

            val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)
            val passwordFromUser = "${mainBinding.numberPicker1.value}${mainBinding.numberPicker2.value}${mainBinding.numberPicker3.value}"

            if (changePasswordMode) {
                // 번호를 저장하는 기능


                passwordPreferences.edit(true) {
                    putString("password",passwordFromUser)
                    // commit() -> sharedPreferences 적용하는 두가지 방법 중 하나, 하나는 위에 edit(true)
                }

                changePasswordMode = false
                mainBinding.changePasswordBtn.setBackgroundColor(Color.BLACK)


            } else {
                // changePasswordMode가 활성화(false에서 True로 변함) -> 비밀번호가 맞는지 확인
                if (passwordPreferences.getString("password","000").equals(passwordFromUser))    // 키와 디폴트값 선언
                {
                    changePasswordMode = true
                    Toast.makeText(this, "번경할 패스워드를 입력해주세요", Toast.LENGTH_SHORT).show()
                    mainBinding.changePasswordBtn.setBackgroundColor(Color.RED)

                } else {
                    // 패스워드 실패
                    showErrorAlertDialog()
                }
            }
        }
    }

    private fun showErrorAlertDialog() {
        AlertDialog.Builder(this)
            .setTitle("실패!!")
            .setMessage("비밀번호가 잘못되었습니다.")
            .setPositiveButton("확인") { dialog, which ->
            }.show()
    }

}