package com.shinkatech.calortracker.View.authScreen.signInScreen

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.shinkatech.calortracker.data.repository.QuesDataUserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SignInScreenViewModel @Inject constructor(
    private val userRepository: QuesDataUserRepo
) : ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")
    // error message
    var emailError by mutableStateOf<String?>(null)
    var passwordError by mutableStateOf<String?>(null)
    var confirmPasswordError by mutableStateOf<String?>(null)

    val auth : FirebaseAuth = FirebaseAuth.getInstance()

     fun validateFields(
        email: String,
        password: String,
        confirmPassword: String,
        setEmailError: (String?) -> Unit,
        setPasswordError: (String?) -> Unit,
        setConfirmPasswordError: (String?) -> Unit
    ): Boolean {
        var isValid = true

        // validate email
        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-z]{2,6}$")
        if (email.isBlank()) {
            setEmailError("Email is required")
            isValid = false
            if (!emailRegex.matches(email)) {
                setEmailError("Invalid email format")
                isValid = false
            }
        }

        //validate password
        if (password.isBlank()) {
            setPasswordError("Password is required")
            isValid = false
            if (password.length < 6) {
                setPasswordError("Password must be at least 6 characters")
                isValid = false
                if (password.length > 15) {
                    setPasswordError("Password must be less than 15 characters")
                    isValid = false
                }
            }
        }

        //validate confirm password
        if (confirmPassword.isBlank()) {
            setConfirmPasswordError("this field is empty")
            isValid = false
            if (confirmPassword != password) {
                setConfirmPasswordError("Passwords do not match")
                isValid = false
            }
        }


        return isValid
    }

    fun getUserData() = userRepository.getTempQuesData()

    fun deleteUserData() = userRepository.deleteQuesData()

    fun saveUserDataToFireStore(uid:String, onSuccess: () -> Unit, onFailure: (String?) -> Unit, email: String, password: String){
        val data  = getUserData()
        Log.d("finalData", "datat: $data")
        val finalData = data?.copy(
            email = email,
            password = password
        )
        Log.d("finalData", "final data: $finalData")
        if (finalData != null){
            userRepository.saveQuesDataToFireStore(uid, finalData)
                .addOnSuccessListener {
                    onSuccess()
                }
                .addOnFailureListener {
                    onFailure(it.message)
                }
        }else {
            onFailure("User data is missing")
        }
    }

    // save user email and password in firebase
    fun usersigIn(email:String, password:String, onSuccess: () -> Unit, onFailure: (String?) -> Unit){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task->
                if(task.isSuccessful){
                    onSuccess()
                } else{
                    onFailure(task.exception?.message ?: "Sign-in failed")
                }
            }
    }

}