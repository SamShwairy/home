package home24.server

import home24.config.Parameters
import home24.databases.entity.ElementEntity
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by sammy on 7/19/2018
 */
//Retrofit API Calls
interface RetrofitServices {
    //Getting the carticles
    @GET(Parameters.API_ARTICLE_SUFFIX)
    fun getArticles(@Query(Parameters.API_LIMIT) query: Int = Parameters.API_DEFAULT_LIMIT): Observable<ElementEntity>
}
