package com.flagos.spacex.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.flagos.spacex.databinding.FragmentLaunchesBinding
import com.flagos.spacex.domain.LaunchItem
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
        adapter = LaunchesAdapter(onClick = {
            //TODO: Navigate to launch detail.
        })
        binding.recycler.adapter = adapter
    }

    private fun initObservers() {
        viewModel.fetchLaunches()
        viewModel.onLaunchStateChanged.observe(viewLifecycleOwner, { setUiState(it) })
    }

    private fun setUiState(state: LaunchesViewModel.LaunchState) {
        when (state) {
            is LaunchesViewModel.LaunchState.OnListRetrieved -> setSuccessfulUi(state.list)
        }
    }

    private fun setSuccessfulUi(list: List<LaunchItem>) {
        adapter.submitList(list)
    }
}
