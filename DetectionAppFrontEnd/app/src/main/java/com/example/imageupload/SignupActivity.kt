package com.example.imageupload

import android.Manifest
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.imageupload.databinding.ActivitySignupBinding
import com.example.imageupload.models.UploadRequest
import com.example.imageupload.viewModel.MainViewModel
import okhttp3.MultipartBody
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var imageUri: Uri
    private var isPermission: Boolean = false
    private var isUpload: Boolean = false

    companion object {
        const val IMAGE_PICK_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)



        binding.btnsignup2.setOnClickListener {

            var i = Intent(this, SigninActivity::class.java)
            startActivity(i)

        }
    }


}