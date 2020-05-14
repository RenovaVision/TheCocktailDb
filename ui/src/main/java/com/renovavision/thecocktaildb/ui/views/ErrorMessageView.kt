package com.renovavision.thecocktaildb.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.renovavision.thecocktaildb.ui.databinding.ErrorMessageViewBinding

class ErrorMessageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding = ErrorMessageViewBinding.inflate(LayoutInflater.from(context), this)

    @get:JvmSynthetic
    var errorMessage: String
        get() = binding.errorMessageView.text.toString()
        set(value) {
            binding.errorMessageView.text = value
        }

    @get:JvmSynthetic
    var clickListener: OnClickListener?
        get() = throw UnsupportedOperationException()
        set(value) {
            binding.retryButton.setOnClickListener(value)
        }
}