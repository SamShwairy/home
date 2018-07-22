package home24.viewmodel

import home24.repository.ArticleRepository
import home24.viewmodel.data.ArticleList
import io.reactivex.Observable

/**
 * Created by sammy on 7/19/2018
 */
//View Model for the Artwork, building an object to be return to the Article Fragment
class ArticleListViewModel(private val articleRepository: ArticleRepository?) {

    fun getArticleList(): Observable<ArticleList>? {
        //Create the data the UI
        return articleRepository?.getArticles()?.map {
            ArticleList(it)
        }?.onErrorReturn {
                    ArticleList(null, it)
                }
    }
}