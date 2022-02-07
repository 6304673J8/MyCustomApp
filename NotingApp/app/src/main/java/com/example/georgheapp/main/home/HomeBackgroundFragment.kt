package com.example.georgheapp.main.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.georgheapp.databinding.ActivityMainBinding
import com.example.georgheapp.databinding.FragmentHomeMainBinding
import com.example.georgheapp.utils.toast

class HomeBackgroundFragment : Fragment() {
    //lateinit var binding: FragmentHomeMainBinding
    lateinit var binding: ActivityMainBinding
    /*override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentHomeMainBinding.inflate(inflater).also {
            binding = it
        }.root
    }*/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ActivityMainBinding.inflate(inflater).also {
            binding = it
        }.root
    }

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmapThumbnail ->
            // Serà null si l'usuari no fa cap foto
            if (bitmapThumbnail != null)
                binding.standardBackgroundImage.setImageBitmap(bitmapThumbnail)
        }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission())
        { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted. Continue the action or workflow in your
                // app.
                context?.toast("Now I can use camera :)")
                cameraLauncher.launch()
            } else {
                // Explain to the user that the feature is unavailable because the
                // features requires a permission that the user has denied. At the
                // same time, respect the user's decision. Don't link to system
                // settings in an effort to convince the user to change their
                // decision.
                context?.toast("User has denied the permission :(")
            }
        }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.standardBackgroundImage.setOnClickListener {
            // IMPORTANT: abans de tot això, recorda declarar el permís al manifest o el permís es
            // denegarà automàticament

            val ctx = context ?: return@setOnClickListener

            if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED
            ) {
                // Permission Obtained
                ctx.toast("I can use camera because I already have the permission :D")
                cameraLauncher.launch()
            } else {
                // No Permission Granted, Ask For It
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }
}