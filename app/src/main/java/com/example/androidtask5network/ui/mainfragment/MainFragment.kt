package com.example.androidtask5network.ui.mainfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidtask5network.data.database.CatDatabase
import com.example.androidtask5network.data.repository.CatRepository
import com.example.androidtask5network.databinding.FragmentCatsBinding
import com.example.androidtask5network.network.NetworkModule

class MainFragment : Fragment() {

    private var _binding : FragmentCatsBinding? = null
    private val binding : FragmentCatsBinding
    get() = _binding!!

    private val repository: CatRepository by lazy{
        val db = CatDatabase.getDatabase((this.requireContext().applicationContext))
        CatRepository(db.catDao())
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
     _binding = FragmentCatsBinding.inflate(inflater,container,false)
        postponeEnterTransition()


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