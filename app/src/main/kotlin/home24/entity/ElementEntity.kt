package home24.databases.entity

import com.google.gson.annotations.SerializedName

/**
 * Created by Sammy on 7/19/2018
 */
//Element Entity
class ElementEntity {
    @SerializedName("_embedded")
    var articleEntity: ArticleWrapperEntity? = null
}