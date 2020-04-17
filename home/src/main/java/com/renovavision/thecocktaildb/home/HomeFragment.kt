package com.renovavision.thecocktaildb.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.renovavision.thecocktaildb.home.databinding.FragmentHomeBinding
import com.renovavision.thecocktaildb.utils.bindingDelegate
import com.renovavision.thecocktaildb.utils.onViewLifecycle
import javax.inject.Inject
import javax.inject.Named

class HomeFragment @Inject constructor(
    @Named("navHomeToIngredientList")
    private val navHomeToIngredientList: () -> Unit,
    @Named("navHomeToCategoryList")
    private val navHomeToCategoryList: () -> Unit
) : Fragment(R.layout.fragment_home) {

    private val binding by bindingDelegate(FragmentHomeBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appSettingPrefs = context?.getSharedPreferences("AppSettingPrefs", 0)
        val sharedPrefsEdit = appSettingPrefs?.edit()
        val isNightModeOn = appSettingPrefs?.getBoolean("NightMode", false)

        onViewLifecycle({ binding.categoriesButton },
            {
                setOnClickListener { navHomeToCategoryList() }
            }, {
                setOnClickListener(null)
            }
        )

        onViewLifecycle({ binding.ingredientsButton },
            {
                setOnClickListener { navHomeToIngredientList() }
            }, {
                setOnClickListener(null)
            }
        )

        onViewLifecycle({ binding.switchBtn },
            {
                isChecked = if (isNightModeOn == true) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    true
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    false
                }

                setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        sharedPrefsEdit?.putBoolean("NightMode", true)
                        sharedPrefsEdit?.apply()
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        sharedPrefsEdit?.putBoolean("NightMode", false)
                        sharedPrefsEdit?.apply()
                    }
                }
            }, { setOnCheckedChangeListener(null) })
    }
}