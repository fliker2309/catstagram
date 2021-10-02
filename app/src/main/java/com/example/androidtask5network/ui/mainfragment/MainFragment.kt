package com.example.androidtask5network.ui.mainfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.androidtask5network.data.model.Cat
import com.example.androidtask5network.databinding.FragmentCatsBinding
import com.example.androidtask5network.presetnation.MainViewModel
import com.example.androidtask5network.presetnation.MainViewModelFactory
import com.example.androidtask5network.ui.mainfragment.adapter.CatsAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    private var _binding: FragmentCatsBinding? = null
    private val binding: FragmentCatsBinding
        get() = requireNotNull(_binding)

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

        val adapter = CatsAdapter {
            val action = MainFragmentDirections.actionMainFragmentToDetailsFragment(it.id)
            this.findNavController().navigate(action)
        }

        binding.apply {
            catsRecyclerView.adapter = adapter
            catsRecyclerView.layoutManager =
                StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            binding.retryButton.setOnClickListener {
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.flow.collectLatest { pagingData: PagingData<Cat> ->
                adapter.submitData(pagingData)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest {
                if (it.append is LoadState.Error) {
                    adapter.retry()
                }
            }
        }

        adapter.addLoadStateListener { state: CombinedLoadStates ->
            val refreshState = state.refresh

            when (state.refresh) {
                LoadState.Loading ->TODO()
                LoadState.Error -> TODO()

            }
            binding.catsRecyclerView.isVisible = state.refresh != LoadState.Loading
            binding.progress.isVisible = state.refresh == LoadState.Loading
            if (refreshState is LoadState.Error) {
                binding.catsRecyclerView.isVisible = false
                binding.errorImage.isVisible = true
                binding.retryButton.isVisible = true
                Snackbar.make(
                    binding.root,
                    "Please, turn on Internet connection and retry",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
