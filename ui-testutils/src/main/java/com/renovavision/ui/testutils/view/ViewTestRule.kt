package com.renovavision.ui.testutils.view

import android.content.Intent
import android.view.View
import androidx.test.rule.ActivityTestRule

class ViewTestRule<V : View> constructor(viewClass: Class<out V>) :
    ActivityTestRule<ViewTestActivity>(
        ViewTestActivity::class.java, false, true
    ) {

    companion object {
        inline fun <reified V : View> create() = ViewTestRule(V::class.java)
    }

    @Suppress("UNCHECKED_CAST") // Type safety ensured by this class' logic.
    val view: V
        get() = activity?.view as V? ?: throw IllegalStateException("The view isn't attached yet.")

    private val viewTestActivityIntent: Intent = ViewTestActivity.newIntent(viewClass)

    fun runOnView(block: V.() -> Unit) = runOnUiThread { block(view) }

    override fun getActivityIntent(): Intent = viewTestActivityIntent
}
