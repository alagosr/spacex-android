package com.flagos.spacex.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.flagos.spacex.R
import com.flagos.spacex.databinding.FragmentLaunchDetailBinding
import com.flagos.spacex.utils.getLocalTimeFromUnix
import com.flagos.spacex.utils.loadImage

class LaunchDetailFragment : Fragment() {

    private val args: LaunchDetailFragmentArgs by navArgs()

    private var _binding: FragmentLaunchDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as MainActivity)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLaunchDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun initViews() {
        with(binding) {
            textMissionName.text = args.missionName
            textMissionDetails.text = args.missionDetails
            textMissionDate.text = String.format(getString(R.string.text_launch_date_detail), getLocalTimeFromUnix(args.missionDate))
            imagePatchLarge.loadImage(args.missionPatch)
        }
    }
}
