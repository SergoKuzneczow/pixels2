package com.sergokuzneczow.main_menu.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController

internal class MainMenuViewModel(navController: NavHostController) : ViewModel() {


}

internal class MainMenuViewModelFactory(private val navController: NavHostController) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainMenuViewModel::class.java)) {
            return MainMenuViewModel(navController) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}