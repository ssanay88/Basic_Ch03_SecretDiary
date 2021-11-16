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
    // 비밀번호 변경 버튼을 한번 누르면 True , 새로운 비밀번호 설정 후 버튼을 누르면 False로 다시 변경
    // 처음에 false 상태에서 저장된 비밀번호와 현재 입력한 비밀번호가 일치하는 지 확인하는 작업 실행 후 변경 작업 시작

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        // 각 자리수는 0~9까지만 허용
        mainBinding.numberPicker1.maxValue = 9
        mainBinding.numberPicker1.minValue = 0

        mainBinding.numberPicker2.maxValue = 9
        mainBinding.numberPicker2.minValue = 0

        mainBinding.numberPicker3.maxValue = 9
        mainBinding.numberPicker3.minValue = 0

        // 다이어리 열기 버튼 클릭 이벤트
        mainBinding.openBtn.setOnClickListener {

            // 비밀번호 변경 중일 경우 , 변경 버튼 빨간 불
            if (changePasswordMode) {
                Toast.makeText(this , "비밀번호를 변경 중입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 저장된 비밀번호를 불러온다.
            val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)

            // User가 입력한 패스워드 변수로 저장
            val passwordFromUser = "${mainBinding.numberPicker1.value}${mainBinding.numberPicker2.value}${mainBinding.numberPicker3.value}"

            // 저장됐던 비밀번호와 입력한 비밀번호가 같을 경우
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

        // 비밀번호 변경하는 버튼 클릭 이벤트
        mainBinding.changePasswordBtn.setOnClickListener {

            // 저장됐던 비밀번호를 불러온다.
            val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)

            // User가 입력한 비밀번호
            val passwordFromUser = "${mainBinding.numberPicker1.value}${mainBinding.numberPicker2.value}${mainBinding.numberPicker3.value}"

            // 번호를 변경하는 상태일 경우, 변경 버튼 빨간 불에서 검은 불로 변경하는 과정 즉 새로운 비밀번호를 저장
            if (changePasswordMode) {
                // 번호를 저장하는 기능
                passwordPreferences.edit(true) {
                    putString("password",passwordFromUser)
                    // commit() -> sharedPreferences 적용하는 두가지 방법 중 하나, 하나는 위에 edit(true)
                }

                changePasswordMode = false    // 비밀번호 변경 후 상태 변경
                mainBinding.changePasswordBtn.setBackgroundColor(Color.BLACK)    // 버튼의 색도 다시 검은색으로 변경


            } else {
                // changePasswordMode가 활성화(false에서 True로 변함) -> 비밀번호가 맞는지 확인
                if (passwordPreferences.getString("password","000").equals(passwordFromUser))    // 키와 디폴트값 선언
                {
                    changePasswordMode = true    // 비밀번호를 변경하는 상태로 변환
                    Toast.makeText(this, "번경할 패스워드를 입력해주세요", Toast.LENGTH_SHORT).show()
                    mainBinding.changePasswordBtn.setBackgroundColor(Color.RED)    // 버튼의 배경 빨간색으로 변경

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