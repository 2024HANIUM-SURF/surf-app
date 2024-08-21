package com.example.ict08

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // XML에서 뷰를 찾기
        val emailEditText: EditText = findViewById(R.id.emailEditText)
        val passwordEditText: EditText = findViewById(R.id.passwordEditText)
        val loginButton: Button = findViewById(R.id.loginButton)
        val signUpText: TextView = findViewById(R.id.signUpText)


        // 로그인 버튼 클릭 리스너
        loginButton.setOnClickListener {
            val email = emailEditText.text?.toString()?.trim() ?: ""
            val password = passwordEditText.text?.toString()?.trim() ?: ""


            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "이메일과 비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val dbHelper = DBHelper(this)
            if (dbHelper.checkUserpass(email, password)) {
                Toast.makeText(this, "로그인에 성공했습니다!", Toast.LENGTH_SHORT).show()
                // 네비게이션 또는 다음 화면으로 이동
                // findNavController(R.id.nav_host_fragment).navigate(R.id.mainScreenFragment)
            } else {
                Toast.makeText(this, "잘못된 이메일 또는 비밀번호입니다", Toast.LENGTH_SHORT).show()
            }
        }

        // 회원가입 클릭 리스너
        signUpText.setOnClickListener {
            Log.d("MainActivity", "Sign Up button clicked")
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}