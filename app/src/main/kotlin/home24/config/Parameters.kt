package home24.config

import android.support.annotation.IntDef

/**
 * Created by sammy on 7/19/2018
 */
object Parameters {
    //Api Urls, keys and values
    const val API_BASE_URL = "https://api-mobile.home24.com/api/v1/"
    const val API_ARTICLE_SUFFIX = "articles?appDomain=1&locale=en_EN"
    const val API_LIMIT = "limit"
    //NUMBER of ITEMS TO REVIEW
    const val API_DEFAULT_LIMIT = 10
    //Number of items in the gridview in the review fragment
    const val TWO_ITEMS_PER_ROW = 2

    //Bundle Identifiers
    const val ARTICLES_LIST = "ARTICLES_LIST"
    const val FRAGMENT_TYPE_IDENTIFIER = "FRAGMENT_TYPE_IDENTIFIER"

    //Constant for the animation
    const val OVERSHOOT_INTERPOLATOR = 1f

    //Status of an Article: like, dislike, default
    @Retention(AnnotationRetention.SOURCE)
    @IntDef(LIKE, DISLIKE, DEFAULT)
    annotation class ACTION

    const val LIKE = 1
    const val DISLIKE = 2
    const val DEFAULT = 0

    //Whether it is a grid or a list
    @Retention(AnnotationRetention.SOURCE)
    @IntDef(LIST, GRID)
    annotation class TYPE

    const val LIST = 0
    const val GRID = 1



}