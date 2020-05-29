package com.renovavision.thecocktaildb.home

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import com.renovavision.thecocktaildb.ui.uni.Action
import com.renovavision.thecocktaildb.ui.uni.Dispatchable
import javax.inject.Inject

object ChangeTheme : Action

class HomeViewModel @Inject constructor() : ViewModel() {

    fun dispatch(dispatchable: Dispatchable) {
        when (dispatchable) {
            is ChangeTheme -> changeTheme()
        }
    }

    private fun changeTheme() {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }
}