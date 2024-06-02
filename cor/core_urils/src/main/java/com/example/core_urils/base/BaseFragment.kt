package com.example.core_urils.base

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.core_urils.Resource
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseFragment(
    @LayoutRes layoutRes: Int,
) : Fragment(layoutRes) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("YourFragment123", "onCreateView() вызван")
        initialize()
        setupRequests()
        setupSubscribers()
        setupListeners()
        initSubscribers()
    }

    open fun initialize() {}

    open fun setupRequests() {}

    open fun setupSubscribers() {}

    open fun setupListeners() {}
    open fun initSubscribers() {}

    /**
     * for @
     */

    /**
     * For what
     *
     * @param state FSf
     * @param onSuccess fs
     */

    protected fun <T> StateFlow<Resource<T>>.collectUIState(
        state: ((Resource<T>) -> Unit)? = null,
        onSuccess: (data: T) -> Unit,
        onError: ((error: Throwable?) -> Unit)? = null
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                this@collectUIState.collect { resource ->
                    state?.invoke(resource)
                    when (resource) {
                        is Resource.Empty -> { /* Обработка пустого состояния */}
                        is Resource.Loading -> { /* Обработка загрузки */}
                        is Resource.Error -> {
                            val errorMessage: String? = resource.message
                            val errorThrowable: Throwable? = null // Передаем null, так как причина ошибки неизвестна
                            onError?.invoke(errorThrowable)
                            errorMessage?.let { message ->
                                // Ваша логика обработки ошибки
                                android.widget.Toast.makeText(requireContext(), message, android.widget.Toast.LENGTH_SHORT).show()
                            }
                        }
                        is Resource.Success -> {
                            resource.data?.let(onSuccess)
                        }
                    }
                }
            }
        }
    }
}