package com.qw73.weatherapptask.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding

import kotlinx.coroutines.launch

abstract class BaseFragment<Binding : ViewBinding, UiEvent : Event> : Fragment() {

    protected abstract val viewModel: BaseViewModel<UiEvent>

    private var _binding: Binding? = null
    val binding get() = _binding!!

    protected abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getViewBinding(inflater, container)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observeData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    open fun setupViews() {}

    @CallSuper
    open fun observeData() {
        with(viewModel)
        {
            viewLifecycleOwner.lifecycleScope.launch {
                eventsFlow.flowWithLifecycle(lifecycle)
                    .collect {
                        handleEvent(it)
                    }
            }
        }
    }

    protected fun navigateTo(directions: NavDirections) {
        findNavController().navigate(directions)
    }

    abstract fun handleEvent(event: UiEvent)

}