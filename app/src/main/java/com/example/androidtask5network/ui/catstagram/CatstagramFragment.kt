package com.example.androidtask5network.ui.catstagram.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.androidtask5network.databinding.FragmentCatstagramBinding
import com.example.androidtask5network.presetnation.MainViewModel

class CatstagramFragment : Fragment() {

    private var _binding: FragmentCatstagramBinding? = null
    private val binding: FragmentCatstagramBinding
        get() = requireNotNull(_binding)

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatstagramBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
