package home24.repository

import home24.databases.entity.ElementEntity
import home24.server.RetrofitServices
import io.reactivex.Observable

/**
 * Created by sammy on 7/21/2018
 */
class ArticleRepository(private val retrofitApi: RetrofitServices?) {

    //Getting the data from the API
    fun getArticles(): Observable<ElementEntity>? {

        return  getArticlesFromApi()
                ?.firstElement()?.toObservable()
    }

    //Calling the Api
    private fun getArticlesFromApi(): Observable<ElementEntity>? {
        return retrofitApi?.getArticles()
        }

}