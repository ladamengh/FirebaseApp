package com.example.firebaseapplication

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Инициализируем Firebase Auth
        auth = FirebaseAuth.getInstance()

        button_no_account.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }

        button_login.setOnClickListener {
            doLogin()
        }

        btn_forgot_password.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Forgot password?")
            val view = layoutInflater.inflate(R.layout.dialog_forgot_password, null)
            val username = view.findViewById<EditText>(R.id.username)
            builder.setView(view)
            builder.setPositiveButton("Reset", DialogInterface.OnClickListener { _, _->
                forgotPassword(username)
            })
            builder.setNegativeButton("Close", DialogInterface.OnClickListener { _, _-> })
            builder.show()
        }
    }

    private fun forgotPassword(username: EditText) {
        if (username.text.toString().isEmpty()) {
            username.error = "Please enter email"
            username.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(username.text.toString()).matches()) {
            username.error = "Please enter valid email, or register in our app"
            username.requestFocus()
            return
        }

        auth.sendPasswordResetEmail(username.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Email sent.", Toast.LENGTH_LONG).show()
                }
            }
    }


    private fun doLogin() {
        // Проверяем ввод почты и пароля
        if (type_email.text.toString().isEmpty()) {
            type_email.error = "Please enter email"
            type_email.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(type_email.text.toString()).matches()) {
            type_email.error = "Please enter valid email"
            type_email.requestFocus()
            return
        }
        if (type_password.text.toString().isEmpty()) {
            type_password.error = "Please enter password"
            type_password.requestFocus()
            return
        }

        // Вход и верификация пользователя
        auth.signInWithEmailAndPassword(type_email.text.toString(), type_password.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Toast.makeText(baseContext, "Login failed.", Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    public override fun onStart() {
        super.onStart()
        // При запуске проверяем, есть ли вошедший пользователь
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    // Функция верификации пользователя
    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            if (currentUser.isEmailVerified) {
                startActivity(Intent(this, UserCabActivity::class.java))
                finish()
            } else {
                Toast.makeText(baseContext, "Please, verify your email", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(baseContext, "Login failed.", Toast.LENGTH_SHORT).show()
        }
    }
}
