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
import kotlinx.android.synthetic.main.row_list_grid_review.view.*

/**
 * Created by sammy on 7/21/2018
 */
class ReviewAdapter(articleList: ArrayList<ArticleEntity>?, private var mContext: Context?, private var type: Int?, private var onArticleClicked: OnArticleClicked) : RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {
    //the data to display
    private var myChildren = articleList

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bindItems(articleObject: ArticleEntity?, context: Context, type: Int?, onArticleClicked: OnArticleClicked) {
            //Using glide to render the url for the image
            Glide.with(context).load(articleObject?.media?.get(0)?.uri).into(itemView.artworkImage)
            //Setting the article Title
            itemView.articleTitle.text = articleObject?.title
            //Showing the title in the LIST while hiding it in the Grid
            if (type == Parameters.LIST)
                itemView.articleTitle.visibility = View.VISIBLE
            else itemView.articleTitle.visibility = View.GONE
            //Showing the status whether an item is liked or disliked
            when {
                articleObject?.status == Parameters.LIKE -> itemView.likeDislikeImage.setImageResource(R.drawable.ic_article_liked)
                articleObject?.status == Parameters.DISLIKE -> itemView.likeDislikeImage.setImageResource(R.drawable.ic_article_disliked)
                else -> itemView.likeDislikeImage.setImageDrawable(null)
            }
            //Clicking on an article fires the interface
            itemView.artworkImage.setOnClickListener {
                onArticleClicked.goToArticle(adapterPosition)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_list_grid_review, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        mContext?.let { holder.bindItems(myChildren?.get(position), it, type, onArticleClicked) }

    }

    override fun getItemCount(): Int {
        return Parameters.API_DEFAULT_LIMIT
    }
    //Interface to go from Review Screen to Article screen -> and to a specific article
    interface OnArticleClicked {
        fun goToArticle(position: Int)
    }

}
