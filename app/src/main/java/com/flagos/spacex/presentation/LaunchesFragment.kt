package com.flagos.spacex.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.flagos.spacex.R
import com.flagos.spacex.databinding.FragmentLaunchesBinding
import com.flagos.spacex.domain.LaunchItem
import com.flagos.spacex.presentation.LaunchesViewModel.LaunchState
import com.flagos.spacex.presentation.LaunchesViewModel.LaunchState.*
import com.flagos.spacex.presentation.adapter.LaunchesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LaunchesFragment : Fragment() {

    private lateinit var adapter: LaunchesAdapter
    private lateinit var navController: NavController

    private val viewModel by viewModels<LaunchesViewModel>()

    private var _binding: FragmentLaunchesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLaunchesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

        initViews()
        initObservers()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun initViews() {
        adapter = LaunchesAdapter(onClick = { details, missionPath, missionName, launchDate ->
            //TODO: Change parameter for a parcelable ui object of LaunchItem.
            //TODO: Make shared element transition.
            val action = LaunchesFragmentDirections.actionLaunchesFragmentToLaunchDetailFragment(missionPath, missionName, details, launchDate)
            navController.navigate(action)
        })
        binding.recycler.adapter = adapter
    }

    private fun initObservers() {
        with(viewModel) {
            onLaunchStateChanged.observe(viewLifecycleOwner) { setUiState(it) }
            fetchLaunches()
        }
    }

    private fun setUiState(state: LaunchState) {
        when (state) {
            is OnListRetrieved -> setSuccessfulState(state.list)
            is OnLoading -> setLoadingState(state.loading)
            is OnError -> setErrorState(state.error)
            OnEmptyList -> setEmptyState()
        }
    }

    private fun setSuccessfulState(list: List<LaunchItem>) {
        adapter.submitList(list)
        changeListVisibility(VISIBLE)
    }

    private fun setLoadingState(loading: Boolean) {
        if (loading) {
            binding.progress.visibility = VISIBLE
            binding.textFailedMessage.visibility = View.GONE
        } else {
            binding.progress.visibility = View.GONE
        }
    }

    private fun setErrorState(error: String?) {
        changeListVisibility(View.GONE)
        with(binding.textFailedMessage) {
            text = String.format(getString(R.string.text_error), error)
            setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.drawable.ic_error, 0, 0)
            visibility = VISIBLE
        }
    }

    private fun setEmptyState() {
        changeListVisibility(View.GONE)
        with(binding.textFailedMessage) {
            text = getString(R.string.text_empty)
            setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.drawable.ic_empty, 0, 0)
            visibility = VISIBLE
        }
    }

    private fun changeListVisibility(visibility: Int) {
        binding.recycler.visibility = visibility
    }
}
