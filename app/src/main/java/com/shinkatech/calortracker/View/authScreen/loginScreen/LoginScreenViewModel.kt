package com.shinkatech.calortracker.View.authScreen.loginScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(): ViewModel(){

    var email by mutableStateOf("")
    var password by mutableStateOf("")
    // error message
    var emailError by mutableStateOf<String?>(null)
    var passwordError by mutableStateOf<String?>(null)

    val auth : FirebaseAuth = FirebaseAuth.getInstance()

    fun validateLoginField(
        email: String,
        password: String,
        setEmailError: (String?) -> Unit,
        setPasswordError: (String?) -> Unit,
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

        return isValid
    }


    fun loginWithemailAndPassword(email: String, password: String, onSucess: ()-> Unit, onfailure: (String?)-> Unit){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {task ->
                if(task.isSuccessful){
                    onSucess()
                }else{
                    onfailure(task.exception?.message?:"please check your credential")
                }

            }
    }



}