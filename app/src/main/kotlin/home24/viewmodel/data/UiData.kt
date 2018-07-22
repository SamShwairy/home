package home24.viewmodel.data

import home24.databases.entity.ElementEntity

/**
 * Created by sammy on 7/19/2018
 */
//Wrapping classes for the response from the repositories and the view model
data class ArticleList(val elementEntity: ElementEntity?, val error: Throwable? = null)

