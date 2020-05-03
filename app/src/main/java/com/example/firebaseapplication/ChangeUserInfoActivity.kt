package com.example.firebaseapplication

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_change_user_info.*
import kotlinx.android.synthetic.main.activity_user_cab.*
import kotlinx.android.synthetic.main.activity_user_cab.user_pic


class ChangeUserInfoActivity: AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_user_info)

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        user_pic.setOnClickListener {
            checkingPermissions()
        }

        button_change_user_info.setOnClickListener {
            changeUserInfo(user)
            startActivity(Intent(this, UserCabActivity::class.java))
            finish()
        }
    }

    private fun checkingPermissions() {
        //check runtime permission
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
            PackageManager.PERMISSION_DENIED
        ) {
            //permission denied
            val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
            //show popup to request runtime permission
            requestPermissions(permissions, PERMISSION_CODE);
        } else {
            //permission already granted
            pickImageFromGallery();
        }
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000;
        //Permission code
        private val PERMISSION_CODE = 1001;
    }

    //handle requested permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickImageFromGallery()
                }
                else{
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            val user = auth.currentUser
            val picView = findViewById<ImageView>(R.id.user_pic)
            var photoUrl = user?.photoUrl
            picView.setImageURI(data?.data)
            photoUrl
        }
    }

    fun changeUserInfo(user: FirebaseUser?) {
        val nameView = findViewById<EditText>(R.id.view_name)
        val emailView = findViewById<EditText>(R.id.view_email)


    }
}