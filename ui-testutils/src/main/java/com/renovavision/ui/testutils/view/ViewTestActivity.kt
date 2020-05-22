package com.renovavision.ui.testutils.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.renovavision.ui.testutils.R

class ViewTestActivity : AppCompatActivity() {

    companion object {
        private const val ARG_VIEW_CLASS = "argViewClass"

        fun newIntent(viewClass: Class<out View>) =
            Intent().apply { putExtra(ARG_VIEW_CLASS, viewClass.name) }
    }

    internal lateinit var view: View
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val className = intent.getStringExtra(ARG_VIEW_CLASS)
            ?: throw IllegalStateException("View class argument must be supplied!")
        val constructor = Class.forName(className).getConstructor(Context::class.java)
        val view = (constructor.newInstance(this) as View)
            .apply { id = R.id.view_test_activity_vut }
        setContentView(view)
        this.view = view
    }
}
