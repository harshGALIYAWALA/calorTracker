package com.shinkatech.calortracker.data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.shinkatech.calortracker.Model.QuesDataUserModel
import com.shinkatech.calortracker.data.remote.FirebaseDataAuth
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuesDataUserRepo @Inject constructor(
    private val firebaseDataAuth : FirebaseDataAuth
) {

    // temp save data when app is still running
    private var tempQuesData : QuesDataUserModel?= null

    //save data
    fun saveQuesData(data: QuesDataUserModel){
        tempQuesData = data
    }

    //fetch data
    fun getTempQuesData() : QuesDataUserModel?= tempQuesData

    //delete data
    fun deleteQuesData() {
        tempQuesData = null
    }

    //save data in cloud database
    fun saveQuesDataToFireStore(uid: String, data: QuesDataUserModel): Task<Void> {
        return firebaseDataAuth.saveDataToFireStore(uid, data)
    }
}