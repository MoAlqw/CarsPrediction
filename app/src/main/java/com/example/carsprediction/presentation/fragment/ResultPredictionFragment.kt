package com.example.carsprediction.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.carsprediction.databinding.FragmentResultPredictionBinding
import com.example.carsprediction.presentation.model.PredictionState
import com.example.carsprediction.presentation.model.mapCleanliness
import com.example.carsprediction.presentation.model.mapDamage
import com.example.carsprediction.presentation.viewmodel.LoadPhotoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ResultPredictionFragment : Fragment() {

    private val viewModel: LoadPhotoViewModel by activityViewModels()

    private var _binding: FragmentResultPredictionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultPredictionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is PredictionState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.analyzeTextView.visibility = View.VISIBLE
                        binding.damageResultText.visibility = View.GONE
                        binding.cleanResultText.visibility = View.GONE
                        binding.backButton.visibility = View.GONE
                    }
                    is PredictionState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.analyzeTextView.visibility = View.GONE
                        binding.damageResultText.visibility = View.VISIBLE
                        binding.cleanResultText.visibility = View.VISIBLE
                        binding.backButton.visibility = View.VISIBLE

                        val damageLabel = mapDamage(state.prediction.damage)
                        binding.damageResultText.text = damageLabel.text
                        binding.damageResultText.setTextColor(ContextCompat.getColor(requireContext(), damageLabel.colorRes))

                        val cleanlinessLabel = mapCleanliness(state.prediction.cleanliness)
                        binding.cleanResultText.text = cleanlinessLabel.text
                        binding.cleanResultText.setTextColor(ContextCompat.getColor(requireContext(), cleanlinessLabel.colorRes))
                    }
                    is PredictionState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.analyzeTextView.text = state.e
                        binding.analyzeTextView.visibility = View.VISIBLE
                        binding.backButton.visibility = View.VISIBLE
                    }
                }
            }
        }

        binding.backButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = ResultPredictionFragment()
    }
}