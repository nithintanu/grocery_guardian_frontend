import com.example.imageupload.models.BasicResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiUpload {
    @Multipart
    @POST("http://192.168.29.218:5000")
    fun uploadImage(
        @Part image: MultipartBody.Part,
        @Part("description") description: String
    ) : Call<BasicResponse>
}