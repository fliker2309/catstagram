package com.example.androidtask5network.presetnation.ui.details

import android.content.ContentValues
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.androidtask5network.R
import com.example.androidtask5network.data.model.Cat
import com.example.androidtask5network.databinding.FragmentDetailsBinding
import com.example.androidtask5network.presetnation.MainViewModel
import com.example.androidtask5network.presetnation.TAG
import com.example.androidtask5network.utils.sdk29AndUp
import java.io.IOException

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get() = requireNotNull(_binding)
    private val args by navArgs<DetailsFragmentArgs>()
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            val catId = args.imageId
            viewModel.getCat(catId)
            viewModel.cat.observe(viewLifecycleOwner) {
                Log.d(TAG, "Single cat url: ${it.url}")
                setHasOptionsMenu(true)
                setImage(cropCatImage, it)
                urlTextView.text = getString(R.string.cat_url, it.url)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.save_cat_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_button -> {
                val cat = viewModel.cat.value
                val bitmap: Bitmap = binding.cropCatImage.drawable.toBitmap()
                cat?.let { cat ->
                    saveImageToExternalStorage(cat, bitmap)
                    Toast.makeText(requireContext(), "Successful saved", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setImage(imageView: ImageView, cat: Cat) {
        with(binding) {
            cropCatImage.load(cat.url) {
                placeholder(R.drawable.ic_placeholder)
                error(R.drawable.ic_error)
                size(1000, 1000)
                target(imageView)
                allowHardware(false)
            }
        }
    }

    // Возвращаемое значение не проверяется.
    private fun saveImageToExternalStorage(cat: Cat, bitmap: Bitmap): Boolean {
        val resolver = context?.applicationContext?.contentResolver
        val imageCollection = sdk29AndUp {
            MediaStore.Images.Media.getContentUri(
                MediaStore.VOLUME_EXTERNAL_PRIMARY
            )
        } ?: MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "${cat.id}.jpeg")// расширение можно вынести в константу.
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        }
        return try {
            // Блок плохо читается. Две вложенные scope функции, функция как отрицательное условие и элвис оператор.
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
