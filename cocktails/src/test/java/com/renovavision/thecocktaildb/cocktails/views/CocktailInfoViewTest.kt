package com.renovavision.thecocktaildb.cocktails.views

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.renovavision.thecocktaildb.cocktails.R
import com.renovavision.thecocktaildb.domain.entities.CocktailInfoEntity
import com.renovavision.ui.testutils.view.ViewTestRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import uk.co.jemos.podam.api.PodamFactoryImpl

@RunWith(AndroidJUnit4::class)
@SmallTest
class CocktailInfoViewTest {

    @JvmField
    @Rule
    val rule = ViewTestRule.create<CocktailInfoView>()

    @Before
    fun setUp() {
        initPicasso()
    }

    @Test
    fun setCocktailShouldDisplayName() {
        val entity = entityFactory()
        rule.runOnView { info = entity }
        onView(withId(R.id.categoryText)).check(matches(withText("Category: ${entity.strCategory}")))
        onView(withId(R.id.alcoholicText)).check(matches(withText("Alcoholic: ${entity.strAlcoholic}")))
        onView(withId(R.id.ingredientsText)).check(matches(withText(entity.getIngredients())))
        onView(withId(R.id.instructionText)).check(matches(withText("Instruction: ${entity.strInstructions}")))
    }

    private fun entityFactory() =
        PodamFactoryImpl().manufacturePojo(CocktailInfoEntity.CocktailEntity::class.java)

}