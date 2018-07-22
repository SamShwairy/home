package home24.server

/**
 * Created by sammy on 7/21/2018
 */
//Getting a Retrofit Client
object ApiUtils {
    val soService: RetrofitServices?
        get() = RetrofitClient.getClient().create(RetrofitServices::class.java)
}
