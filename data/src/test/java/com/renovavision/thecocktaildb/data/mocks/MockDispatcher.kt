package com.renovavision.thecocktaildb.data.mocks

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

internal object MockDispatcher : Dispatcher() {

    override fun dispatch(request: RecordedRequest): MockResponse =
        when (request.method) {
            "GET" -> when (request.path) {
                "/list.php?c=list" -> ok("categories".json())
                else -> badRequest()
            }
            else -> badRequest()
        }

    private fun ok(body: String? = null) = response(200, body)

    private fun badRequest() = response(400)

    private fun response(code: Int = 200, body: String? = null) = MockResponse().apply {
        setResponseCode(code)
        body?.let { setBody(body) }
    }

    private fun String.json(): String =
        MockDispatcher::class.java.getResource("/$this.json").readText()

}