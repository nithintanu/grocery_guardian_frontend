package com.example.imageupload

import android.Manifest
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.imageupload.databinding.ActivityAboutBinding
import com.example.imageupload.models.UploadRequest
import com.example.imageupload.viewModel.MainViewModel
import okhttp3.MultipartBody
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class AboutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAboutBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var imageUri: Uri
    private var isPermission: Boolean = false
    private var isUpload: Boolean = false

    companion object {
        const val IMAGE_PICK_CODE = 100
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        checkPermission()


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if ((ContextCompat.checkSelfPermission(this@AboutActivity, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
                        isPermission = true
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    isPermission = false
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this@AboutActivity,
                Manifest.permission.READ_EXTERNAL_STORAGE) !==
            PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this@AboutActivity,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                isPermission = true
                ActivityCompat.requestPermissions(this@AboutActivity,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
            } else {
                isPermission = false
                ActivityCompat.requestPermissions(this@AboutActivity,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
            }
        } else {
            isPermission = true

        }
    }

    fun ContentResolver.getFileName(fileUri: Uri): String {
        var name = ""
        val returnCursor = this.query(fileUri, null, null, null, null)
        if (returnCursor != null) {
            val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            returnCursor.moveToFirst()
            name = returnCursor.getString(nameIndex)
            returnCursor.close()
        }
        return name
    }

    private fun uploadImage() {
        val parcelFileDescriptor = contentResolver.openFileDescriptor(imageUri, "r", null) ?: return

        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val file = File(cacheDir, contentResolver.getFileName(imageUri))
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)

        val body = UploadRequest(file, "image")

        viewModel.uploadImage(
            MultipartBody.Part.createFormData(
                "profile",
                file.name,
                body
            ),
            ""

        )

        viewModel.isUploaded.observe(this, Observer {
            if (it) {
                Toast.makeText(this, "Success Uploaded Image", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Failed Uploaded Image", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun chooseImage() {
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            val mimeTypes = arrayOf("image/jpeg", "image/png")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(it, IMAGE_PICK_CODE)
        }
    }
}