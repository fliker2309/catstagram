package com.example.androidtask5network.ui.details

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.androidtask5network.databinding.FragmentDetailsBinding
import com.example.androidtask5network.presetnation.MainViewModel
import com.example.androidtask5network.presetnation.MainViewModelFactory

class DetailsFragment : Fragment() {

    private val _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get() = requireNotNull(_binding)
    private val args by navArgs<DetailsFragmentArgs>()
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory()
    }
}
