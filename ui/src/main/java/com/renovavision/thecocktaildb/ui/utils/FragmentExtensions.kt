package com.renovavision.thecocktaildb.ui.utils

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun <T> LiveData<T>.observe(owner: LifecycleOwner, crossinline observer: (T) -> Unit) {
    this.observe(owner, Observer { it?.apply(observer) })
}

inline fun <V : View> Fragment.onViewLifecycle(
    crossinline view: () -> V,
    crossinline onViewCreated: V.() -> Unit = {},
    crossinline onDestroyView: V.() -> Unit = {}
) {
    viewLifecycleOwnerLiveData.observe(this) {
        it?.lifecycle?.addObserver(object : LifecycleObserver {
            private val v = view()

            @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
            private fun onViewCreated(): Unit = v.onViewCreated()

            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            private fun onDestroyView(): Unit = v.onDestroyView()
        })
    }
}

fun <B : ViewBinding> Fragment.bindingDelegate(
    bindingProvider: (view: View) -> B
): BindingDelegate<B> =
    BindingDelegate(
        this,
        bindingProvider
    )

class BindingDelegate<B : ViewBinding>(
    private val fragment: Fragment,
    private val bindingProvider: (view: View) -> B
) : ReadOnlyProperty<Fragment, B>, LifecycleObserver {

    private var binding: B? = null

    init {
        fragment.viewLifecycleOwnerLiveData.observe(fragment) {
            it?.lifecycle?.addObserver(this)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun onDestroyView() {
        binding = null
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): B =
        binding ?: bindingProvider(fragment.requireView()).apply {
            binding = this
        }
}