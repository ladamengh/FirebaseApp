package com.example.firebaseapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_user_cab.*

class UserCabActivity: AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_cab)

        auth = FirebaseAuth.getInstance()
        var user = auth.currentUser

        setParameters(user)

        btn_change_password.setOnClickListener {
            startActivity(Intent(this, ChangePasswordActivity::class.java))
        }

        btn_change_user_info.setOnClickListener {
            startActivity(Intent(this, ChangeUserInfoActivity::class.java))
        }

    }

    private fun setParameters(user: FirebaseUser?) {
        val nameView = findViewById<TextView>(R.id.view_name)
        val emailView = findViewById<TextView>(R.id.view_email)
        val picView = findViewById<ImageView>(R.id.user_pic)

        user?.let {
            // Name, email address, and profile photo Url
            val name = user.displayName
            val email = user.email
            val photoUrl = user.photoUrl

            if (name == "" || photoUrl == null) {
                emailView.setText(email)
                return
            } else {
                nameView.setText(name)
                emailView.setText(email)
                picView.setImageURI(photoUrl)
            }
        }
    }


}