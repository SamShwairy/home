package home24.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.sam.rijkmuseum.R
import home24.config.Parameters
import home24.databases.entity.ArticleEntity
import kotlinx.android.synthetic.main.row_article.view.*

/**
 * Created by sammy on 3/3/2018
 */
//Recycler Adapter for the Artwork
class ArticleAdapter(articleList: ArrayList<ArticleEntity>?, reviewedItemsNumber: Int, context: Context) : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {
    //list of Items
    private var myChildren = articleList
    //context
    private var mContext: Context? = context
    //number of items reviewed
    private var mReviewedItemsNumber = reviewedItemsNumber

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(articleObject: ArticleEntity?, context: Context) {
            //Setting the price of the article
            itemView.articlePrice.text = StringBuilder(articleObject?.price?.currency.toString() + " " + articleObject?.price?.amount)
            //Setting the title of the article
            itemView.articleTitle.text = articleObject?.title
            //Setting the number of which article is being reviewed
            itemView.articleNumber.text = StringBuilder(context.getString(R.string.reviewing_item_number) +" "+(adapterPosition+1))
            //Setting the status of the article after it being reviewed
            when {
                articleObject?.status==Parameters.LIKE -> itemView.likeDislikeImage.setImageResource(R.drawable.ic_article_liked)
                articleObject?.status==Parameters.DISLIKE -> itemView.likeDislikeImage.setImageResource(R.drawable.ic_article_disliked)
                else -> itemView.likeDislikeImage.setImageDrawable(null)
            }
            //Using glide to load the article url into the image view
            Glide.with(context).load(articleObject?.media?.get(0)?.uri).into(itemView.artworkImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_article, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        mContext?.let { holder.bindItems(myChildren?.get(position), it) }

    }

    override fun getItemCount(): Int {
        //If the user reviewed less than 10 items, show the number of items reviewed plus 1
        //Else show 10 items
        val childCount = mReviewedItemsNumber + 1
        return if (childCount <= Parameters.API_DEFAULT_LIMIT)
            childCount
        else Parameters.API_DEFAULT_LIMIT
    }

   fun setReviewedNumber(reviewedItemsNumber: Int, articleList: ArrayList<ArticleEntity>?) {
        //Assigning the new list with the statuses whether liked or disliked
        myChildren = articleList
        //Assigning the number of reviewed items
        mReviewedItemsNumber = reviewedItemsNumber
        //Notifying the adapter with Animation
        notifyItemRangeChanged(0,mReviewedItemsNumber)
    }
}
