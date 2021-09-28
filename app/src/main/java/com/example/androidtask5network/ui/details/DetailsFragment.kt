package com.example.androidtask5network.ui.details

import android.content.ContentValues
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.imageLoader
import coil.request.ImageRequest
import com.example.androidtask5network.R
import com.example.androidtask5network.data.model.Cat
import com.example.androidtask5network.databinding.FragmentDetailsBinding
import com.example.androidtask5network.presetnation.MainViewModel
import com.example.androidtask5network.presetnation.MainViewModelFactory
import com.example.androidtask5network.utils.sdk29AndUp
import java.io.IOException

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get() = requireNotNull(_binding)
    private val args by navArgs<DetailsFragmentArgs>()
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val catId = args.imageid
        viewModel.getCat(catId)
        viewModel.cat.observe(this.viewLifecycleOwner) {
            setImage(binding.cropCatImage, it)
        }

        binding.saveButton.setOnClickListener {
            val cat = viewModel.cat.value!!
            val bitmap: Bitmap = binding.cropCatImage.drawable.toBitmap()
            saveImageToExternalStorage(cat, bitmap)
        }
    }

    private fun setImage(imageView: ImageView, cat: Cat) {
        val request = ImageRequest.Builder(requireContext())
            .data(cat.url)
            .size(1000, 1000)
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_error)
            .target(imageView)
            .allowHardware(false)
            .build()
        requireContext().imageLoader.enqueue(request)
    }

    private fun saveImageToExternalStorage(cat: Cat, bitmap: Bitmap): Boolean {
        val resolver = context?.applicationContext?.contentResolver
        val imageCollection = sdk29AndUp {
            MediaStore.Images.Media.getContentUri(
                MediaStore.VOLUME_EXTERNAL_PRIMARY
            )
        } ?: MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "${cat.id}.jpeg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.WIDTH, bitmap.width)
            put(MediaStore.Images.Media.HEIGHT, bitmap.height)
        }
        return try {
            resolver?.insert(imageCollection, contentValues)?.also { uri ->
                resolver.openOutputStream(uri).use { outputStream ->
                    if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 95, outputStream)) {
                        throw IOException("Couldn't save bitmap")
                    }
                }
            } ?: throw IOException("Couldn't create MediaStore entry")
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }
}
