package com.example.firebaseapplication

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_change_password.*

class ChangePasswordActivity: AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        auth = FirebaseAuth.getInstance()

        button_change_password.setOnClickListener {
            changePassword()
        }
    }

    private fun changePassword() {
        // Проверяем заполнены ли все поля
        if (current_password.text.toString().isNotEmpty() &&
            new_password.text.toString().isNotEmpty() &&
            confirm_password.text.toString().isNotEmpty()) {
                matchingPasswords(new_password, current_password) // сравниваем пароли
        } else {
            Toast.makeText(this, "Please enter all required fields", Toast.LENGTH_LONG).show()
        }
    }

    // Функция проверки нового пароля
    private fun matchingPasswords(newPassword: EditText, confirmPassword: EditText) {
        // Совпадают ли новый пароль и его подтверждение
        if (newPassword.text.toString().equals(confirmPassword.text.toString())) {
            val user = auth.currentUser
            // Проверяем авторизирован ли пользователь
            if (user != null && user.email != null) {
                reAuthentication(user) // переавторизация пользователя
            } else {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        } else {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show()
        }
    }

    // Функция переавторизации
    private fun reAuthentication(user: FirebaseUser) {
        val credential = EmailAuthProvider
            .getCredential(user.email!!, current_password.text.toString())
        // Просим пользователя ввести свои данные заново
        user.reauthenticate(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(this, "Re-authentication has completed", Toast.LENGTH_LONG).show()
                user.updatePassword(new_password.text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "You've changed you password successfully", Toast.LENGTH_LONG).show()
                            auth.signOut()
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                    }
            } else {
                Toast.makeText(this, "Re-authentication has failed", Toast.LENGTH_LONG).show()
            }
        }
    }
}