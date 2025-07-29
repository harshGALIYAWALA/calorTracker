package com.shinkatech.calortracker.View.questionsScreens

import androidx.lifecycle.ViewModel
import com.shinkatech.calortracker.data.repository.QuesDataUserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.shinkatech.calortracker.Model.QuesDataUserModel

@HiltViewModel
class QuesScreenViewModel @Inject constructor(
    private val userRepository: QuesDataUserRepo
): ViewModel() {

    fun saveTempData(data: QuesDataUserModel){
        userRepository.saveQuesData(data)
    }

    fun getTempData(): QuesDataUserModel ?= userRepository.getTempQuesData()

    fun deleteTempData() = userRepository.deleteQuesData()

}