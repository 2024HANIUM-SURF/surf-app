package com.example.ict08

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast

class RegisterActivity : AppCompatActivity() {
    private lateinit var usernameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var userTypeRadioGroup: RadioGroup
    private lateinit var studentRadioButton: RadioButton
    private lateinit var parentRadioButton: RadioButton
    private lateinit var backButton: Button
    private lateinit var signUpButton: Button

    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        Log.d("RegisterActivity", "RegisterActivity started")

        // Initialize views
        usernameEditText = findViewById(R.id.usernameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText)
        userTypeRadioGroup = findViewById(R.id.userTypeRadioGroup)
        studentRadioButton = findViewById(R.id.studentRadioButton)
        parentRadioButton = findViewById(R.id.parentRadioButton)
        backButton = findViewById(R.id.backButton)
        signUpButton = findViewById(R.id.signUpButton)

        dbHelper = DBHelper(this)

        signUpButton.setOnClickListener {
            handleSignUp()
        }

        backButton.setOnClickListener {
            finish() // Close the activity and go back
        }
    }

    private fun handleSignUp() {
        val username = usernameEditText.text.toString()
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        val confirmPassword = confirmPasswordEditText.text.toString()
        val selectedUserTypeId = userTypeRadioGroup.checkedRadioButtonId
        val userType = if (selectedUserTypeId == R.id.studentRadioButton) {
            "Student"
        } else {
            "Parent"
        }

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != confirmPassword) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return
        }

        if (dbHelper.checkUser(username)) {
            Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show()
            return
        }

        if (dbHelper.checkEmail(email)) {
            Toast.makeText(this, "Email already exists", Toast.LENGTH_SHORT).show()
            return
        }

        val isInserted = dbHelper.insertData(username, password, email, userType)
        if (isInserted) {
            Toast.makeText(this, "Sign-up successful!", Toast.LENGTH_SHORT).show()
            finish() // Close the activity and go back to the previous screen
        } else {
            Toast.makeText(this, "Sign-up failed", Toast.LENGTH_SHORT).show()
        }
    }
}