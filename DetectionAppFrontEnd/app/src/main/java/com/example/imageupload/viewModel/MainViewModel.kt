package com.example.imageupload.viewModel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.imageupload.models.BasicResponse
import com.example.imageupload.repository.MainRepository
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
    private val repository = MainRepository()
    var output:String=""
    val isUploaded = MutableLiveData<Boolean>()

    fun uploadImage(image: MultipartBody.Part, msg: String  ) {
        repository.uploadImage(image,"17.4065,78.4777").enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                Log.d("Myinfo", response.body().toString()+"...."+response.isSuccessful)
                //Toast.makeText(null, response.body().toString(), Toast.LENGTH_LONG)
                if (response.isSuccessful) {
                    isUploaded.postValue(true)
                    output= response.body()?.output ?: "-"
                } else {
                    isUploaded.postValue(false)

                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                isUploaded.postValue(false)
                Log.e("Error2", t.toString() +"-"+call.toString())
            }
        })
    }
}