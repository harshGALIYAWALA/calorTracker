package com.shinkatech.calortracker.data.remote

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.shinkatech.calortracker.Model.QuesDataUserModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseDataAuth @Inject constructor(){

    private val firestore = FirebaseFirestore.getInstance()

    fun saveDataToFireStore(uid: String, data: QuesDataUserModel): Task<Void> {
        return firestore.collection("Users").document(uid).set(data)
    }

}