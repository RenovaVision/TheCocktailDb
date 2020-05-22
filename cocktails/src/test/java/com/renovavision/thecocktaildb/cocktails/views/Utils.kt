package com.renovavision.thecocktaildb.cocktails.views

import androidx.test.core.app.ApplicationProvider
import com.squareup.picasso.Downloader
import com.squareup.picasso.Picasso
import okhttp3.Response

internal fun initPicasso() {
    try {
        Picasso.setSingletonInstance(
            Picasso.Builder(ApplicationProvider.getApplicationContext())
                .downloader(object : Downloader {
                    override fun shutdown(): Unit = error("MOCK!")
                    override fun load(request: okhttp3.Request): Response = error("MOCK!")
                })
                .build()
        )
    } catch (e: IllegalStateException) {
        // Singleton instance already exists
    }
}