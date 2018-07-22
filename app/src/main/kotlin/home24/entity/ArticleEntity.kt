package home24.databases.entity

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Sammy on 7/19/2018
 */
//Article Entity
class ArticleEntity() : Parcelable {
    var media: ArrayList<MediaEntity>? = ArrayList()
    var sku: String? = null
    var title: String? = null
    var price: PriceEntity? = null
    var status: Int? = 0

    constructor(parcel: Parcel) : this() {
        sku = parcel.readString()
        title = parcel.readString()
        status = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(sku)
        parcel.writeString(title)
        parcel.writeValue(status)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ArticleEntity> {
        override fun createFromParcel(parcel: Parcel): ArticleEntity {
            return ArticleEntity(parcel)
        }

        override fun newArray(size: Int): Array<ArticleEntity?> {
            return arrayOfNulls(size)
        }
    }
}