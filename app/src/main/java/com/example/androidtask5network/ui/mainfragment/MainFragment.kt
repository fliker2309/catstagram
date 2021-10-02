package com.example.androidtask5network.ui.mainfragment

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (!isNetworkAvailable(requireContext())) {
            binding.catsRecyclerView.isVisible = false
            binding.errorImage.isVisible = true
            binding.retryButton.isVisible = true
            binding.retryButton.setOnClickListener {
                if (isNetworkAvailable(requireContext())) {
                    initView()
                } else Toast.makeText(
                    requireContext(),
                    "No internet connection",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            initView()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initView() {
        val adapter = CatsAdapter {
            val action = MainFragmentDirections.actionMainFragmentToDetailsFragment(it.id)
            this.findNavController().navigate(action)
        }

        binding.catsRecyclerView.isVisible = true
        binding.errorImage.isVisible = false
        binding.retryButton.isVisible = false

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
            binding.catsRecyclerView.isVisible = state.refresh != LoadState.Loading
            binding.progress.isVisible = state.refresh == LoadState.Loading
        }
    }

    private fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
