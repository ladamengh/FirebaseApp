package com.example.firebaseapplication.util

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.NullPointerException

object FireStoreUtil {

   /* private var auth = FirebaseAuth.getInstance()

    // it will be set in this property only once we need it
    private val fireStoreInstance: FirebaseFirestore
            by lazy { FirebaseFirestore.getInstance()}

    // each user will have their own document
    private val currentUserDocRef: DocumentReference
        get() = fireStoreInstance.document("users/${FirebaseAuth.getInstance().uid
            ?: throw NullPointerException("UID is null")}")

    fun initCurrentUserIfFirstTime(onComplete: () -> Unit) {
        currentUserDocRef.get().addOnSuccessListener { documentSnapshot ->
            if (!documentSnapshot.exists()) {
                val newUser = User(auth.currentUser?.displayName ?: "",
                    "", null)
                currentUserDocRef.set(newUser).addOnSuccessListener {
                    OnComplete()
                }
            }
            else
                onComplete()
        }
    }

    fun updateCurrentUser(name: String = "", bio: String = "", profilePicturePath: String? = null) {
        val userFieldMap = mutableMapOf<String, Any>()
        if (name.isNotBlank()) userFieldMap["name"] = name
        if (bio.isNotBlank()) userFieldMap["bio"] = bio
        if (profilePicturePath != null)
            userFieldMap["profilePicturePath"] = profilePicturePath
        currentUserDocRef.update(userFieldMap)
    }

    fun getCurrentUser(onComplete: (User) -> Unit) {
        currentUserDocRef.get()
            .addOnSuccessListener {
                onComplete(it.toObject(User::class.java)) // -it- is an implicit parameter of type document snapshot
            }
    }*/
}