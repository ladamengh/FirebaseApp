package com.example.firebaseapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignUpActivity: AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Инициализируем Firebase Auth
        auth = FirebaseAuth.getInstance()

        button_reg.setOnClickListener {
            signUpUser()
        }
    }

    private fun signUpUser() {
        // Проверяем ввод почты и пароля
        if (reg_email.text.toString().isEmpty()) {
            reg_email.error = "Please enter email"
            reg_email.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(reg_email.text.toString()).matches()) {
            reg_email.error = "Please enter valid email"
            reg_email.requestFocus()
            return
        }
        if (reg_password.text.toString().isEmpty()) {
            reg_password.error = "Please enter password"
            reg_password.requestFocus()
            return
        }

        // Создание пользователя в FireBase
        auth.createUserWithEmailAndPassword(reg_email.text.toString(), reg_password.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.sendEmailVerification()
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                startActivity(Intent(this, MainActivity::class.java))
                                finish()
                                Toast.makeText(baseContext, "Authentication complete. Please verify your email address.",
                                    Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    Toast.makeText(baseContext, "Authentication failed. Try again",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}