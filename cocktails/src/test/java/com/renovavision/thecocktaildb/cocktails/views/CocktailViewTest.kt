package com.renovavision.thecocktaildb.cocktails.views

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.renovavision.thecocktaildb.cocktails.R
import com.renovavision.thecocktaildb.domain.entities.DrinksByQueryEntity
import com.renovavision.ui.testutils.view.ViewTestRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import uk.co.jemos.podam.api.PodamFactoryImpl

@RunWith(AndroidJUnit4::class)
@SmallTest
class CocktailViewTest {

    @Before
    fun setUp() {
        initPicasso()
    }

    @JvmField
    @Rule
    val rule = ViewTestRule.create<CocktailView>()

    @Test
    fun setCocktailShouldDisplayName() {
        val entity = entityFactory()
        rule.runOnView { cocktail = entity }
        onView(withId(R.id.cocktailName)).check(matches(withText(entity.strDrink)))
    }

    private fun entityFactory() =
        PodamFactoryImpl().manufacturePojo(DrinksByQueryEntity.DrinkEntity::class.java)
}