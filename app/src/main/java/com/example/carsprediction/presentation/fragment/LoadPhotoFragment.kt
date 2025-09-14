package com.example.carsprediction.presentation.fragment

import android.Manifest
import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.carsprediction.R
import com.example.carsprediction.databinding.FragmentLoadPhotoBinding
import com.example.carsprediction.presentation.extension.toPhoto
import com.example.carsprediction.presentation.viewmodel.LoadPhotoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoadPhotoFragment : Fragment() {

    private var _binding: FragmentLoadPhotoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoadPhotoViewModel by activityViewModels()

    private lateinit var cameraPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var galleryPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var cameraLauncher: ActivityResultLauncher<Void?>
    private lateinit var galleryLauncher: ActivityResultLauncher<PickVisualMediaRequest>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLaunchers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoadPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.takePhotoButton.setOnClickListener {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }

        binding.loadPhotoButton.setOnClickListener {
            val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                Manifest.permission.READ_MEDIA_IMAGES
            } else {
                Manifest.permission.READ_EXTERNAL_STORAGE
            }
            galleryPermissionLauncher.launch(permission)
        }
    }

    private fun initLaunchers() {
        cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            bitmap?.let { handleImage(it) }
        }

        galleryLauncher = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let { handleImage(uriToBitmap(it)) }
        }

        galleryPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->
            if (granted) openGallery()
        }

        cameraPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->
            if (granted) openCamera()
        }
    }

    private fun handleImage(bitmap: Bitmap) {
        val photo = bitmap.toPhoto()
        viewModel.startPredict(photo)
        parentFragmentManager.beginTransaction()
            .replace(R.id.main, ResultPredictionFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    private fun openCamera() = cameraLauncher.launch(null)

    private fun openGallery() = galleryLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

    private fun uriToBitmap(uri: Uri): Bitmap {
        val resolver: ContentResolver = requireContext().contentResolver
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(resolver, uri)
            ImageDecoder.decodeBitmap(source)
        } else {
            @Suppress("DEPRECATION")
            MediaStore.Images.Media.getBitmap(resolver, uri)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = LoadPhotoFragment()
    }
}
