package com.example.androidtask5network.presetnation.ui.catstagram

import android.content.ContentValues
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager

import com.example.androidtask5network.feature_catlist.data.model.Cat
import com.example.androidtask5network.feature_catlist.presetnation.MainViewModel

import com.example.androidtask5network.presetnation.ui.catstagram.adapter.CatActionListener
import com.example.androidtask5network.presetnation.ui.catstagram.adapter.CatstagramAdapter
import com.example.androidtask5network.utils.sdk29AndUp
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.IOException

private const val IMAGE_FORMAT = ".jpeg"
private const val MIME_TYPE = "image/jpeg"

class CatstagramFragment : Fragment() {

    private var _binding: Frag? = null
    private val binding: FragmentCatstagramBinding
        get() = requireNotNull(_binding)

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var adapter: CatstagramAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatstagramBinding.inflate(inflater, container, false)
        adapter = CatstagramAdapter(object : CatActionListener {
            override fun onCatDownload(cat: Cat) {
                val myCat = viewModel.cat.value!!

                saveImage(myCat)
                Toast.makeText(context, "onDownloadClick ${cat.id}", Toast.LENGTH_SHORT).show()
            }

            override fun onCatLike(cat: Cat) {
                Toast.makeText(context, "onLikeClick ${cat.url}", Toast.LENGTH_SHORT).show()
            }
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObservers()
    }

    override fun onDestroy() {
        _binding = null

        super.onDestroy()
    }

    private fun initView() {
        binding.apply {
            catstagramRV.adapter = adapter
            catstagramRV.layoutManager =
                GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
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

    private fun saveImage(cat: Cat) {
        val resolver = context?.applicationContext?.contentResolver
        val imageCollection = sdk29AndUp {
            MediaStore.Images.Media.getContentUri(
                MediaStore.VOLUME_EXTERNAL_PRIMARY
            )
        } ?: MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "${cat.id}.$IMAGE_FORMAT")
            put(MediaStore.Images.Media.MIME_TYPE, MIME_TYPE)
        }
        try {
            resolver?.insert(imageCollection, contentValues)?.also { uri ->
                resolver.openOutputStream(uri)
            } ?: throw IOException("Couldn't save cat")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
