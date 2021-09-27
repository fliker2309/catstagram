package com.example.androidtask5network.ui.mainfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.androidtask5network.R
import com.example.androidtask5network.databinding.FragmentCatsBinding
import com.example.androidtask5network.presetnation.MainViewModel
import com.example.androidtask5network.presetnation.MainViewModelFactory
import com.example.androidtask5network.ui.mainfragment.adapter.CatsAdapter
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    private var _binding: FragmentCatsBinding? = null
    private val binding: FragmentCatsBinding
        get() =requireNotNull(_binding)

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatsBinding.inflate(inflater, container, false)
      /*  postponeEnterTransition()*/




        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val adapter = CatsAdapter{
         /*   findNavController().navigate(R.id.action_mainFragment_to_detailsFragment)*/
        }

        binding.apply {
            catsRecyclerView.adapter = adapter
            catsRecyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        }

        viewLifecycleOwner.lifecycleScope.launch{
            viewModel.flow.collectLatest { pagingData ->
            coroutineScope { adapter.submitData(pagingData) }}
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}