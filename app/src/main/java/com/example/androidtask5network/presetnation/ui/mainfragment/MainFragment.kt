package com.example.androidtask5network.presetnation.ui.mainfragment

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
import androidx.fragment.app.activityViewModels
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
import com.example.androidtask5network.presetnation.ui.mainfragment.adapter.CatsAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    private var _binding: FragmentCatsBinding? = null
    private val binding: FragmentCatsBinding
        get() = requireNotNull(_binding)

    private val viewModel: MainViewModel by activityViewModels()

    private val adapter = CatsAdapter {
        val action = MainFragmentDirections.actionMainFragmentToDetailsFragment(it.id)
        this.findNavController().navigate(action)
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
        super.onViewCreated(view, savedInstanceState)
        if (isNetworkAvailable()) {
            initView()
            initObservers()
        } else {
            showNoInternetUi()
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun showNoInternetUi() {
        binding.apply {
            catsRecyclerView.isVisible = false
            errorImage.isVisible = true
            retryButton.isVisible = true
            retryButton.setOnClickListener {
                if (isNetworkAvailable()) {
                    initView()
                    initObservers()
                } else Toast.makeText(
                    requireContext(),
                    "No Internet connection",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun initView() {
        binding.apply {
            catsRecyclerView.isVisible = true
            errorImage.isVisible = false
            retryButton.isVisible = false
            catsRecyclerView.adapter = adapter
            catsRecyclerView.layoutManager =
                StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        }
        adapter.addLoadStateListener { state: CombinedLoadStates ->
            binding.catsRecyclerView.isVisible = state.refresh != LoadState.Loading
            binding.progress.isVisible = state.refresh == LoadState.Loading
        }
    }

    private fun initObservers() {
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
    }

    private fun isNetworkAvailable(): Boolean {
        val context = requireContext()
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
}
