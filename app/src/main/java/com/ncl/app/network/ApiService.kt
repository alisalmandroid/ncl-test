import com.ncl.app.data.APIResponse
import com.ncl.app.utils.baseUrl
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {

    companion object {
        const val ACCEPT_JSON = "Accept: application/json"
        const val CONTENT_TYPE_JSON = "Content-Type: application/json"
        operator fun invoke(): ApiService {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }

    @GET("SKY")
    suspend fun getSKY(): Response<APIResponse>

    @GET("ESCAPE")
    suspend fun getESCAPE(): Response<APIResponse>

    @GET("BLISS")
    suspend fun getBLISS(): Response<APIResponse>

}