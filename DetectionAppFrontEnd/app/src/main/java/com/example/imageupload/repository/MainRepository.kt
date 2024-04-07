package com.example.imageupload.repository

import ApiUpload
import com.example.imageupload.network.Retro
import okhttp3.MultipartBody

class MainRepository {
    private val retro = Retro().getRetroClientInstance()
    fun uploadImage(image: MultipartBody.Part, description:String) = retro.create(ApiUpload::class.java).uploadImage(image, "17.4065,78.4772")
}