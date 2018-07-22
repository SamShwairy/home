package home24.view.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sam.rijkmuseum.R
import home24.config.Parameters
import home24.databases.entity.ArticleEntity
import home24.view.adapter.ReviewAdapter
import kotlinx.android.synthetic.main.fragment_list_review.*

/**
 * Created by sammy on 7/21/2018
 */
class ListGridReviewFragment : Fragment(), ReviewAdapter.OnArticleClicked {
    //Callback and listener to interact with activity
    private var callback: FragmentActivity? = null
    private var listener: ReviewFragmentListener? = null

    //Type whether it is a grid or a list
    private var type: Int? = Parameters.LIST

    //ArrayList of Articles to display
    private var articlesList: ArrayList<ArticleEntity>? = ArrayList()


    //Interface to go to the article fragment from the review
    override fun goToArticle(position: Int) {
        listener?.goToArticleFragment(position)
    }

    //New instance passing the arraylist of Articles to the review fragment along with the type
    fun newInstance(articleList: ArrayList<ArticleEntity>?, @Parameters.TYPE type: Int): ListGridReviewFragment {
        val fragment = ListGridReviewFragment()
        val bundle = Bundle()
        bundle.putParcelableArrayList(Parameters.ARTICLES_LIST, articleList)
        bundle.putInt(Parameters.FRAGMENT_TYPE_IDENTIFIER, type)
        fragment.arguments = bundle
        return fragment
    }

    //Getting the fields from the argument
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list_review, container, false)
        articlesList = arguments?.getParcelableArrayList(Parameters.ARTICLES_LIST)
        type = arguments?.getInt(Parameters.FRAGMENT_TYPE_IDENTIFIER)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Declaring the recyclerview adapter
        val reviewAdapter = ReviewAdapter(articlesList, context, type, this)
        recyclerView.adapter = reviewAdapter

        //Showing the items as a LIST or a grid based on the type
        if (type == Parameters.LIST) {
            val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            recyclerView.layoutManager = linearLayoutManager
        } else {
            val gridLayoutManager = GridLayoutManager(context, Parameters.TWO_ITEMS_PER_ROW)
            recyclerView.layoutManager = gridLayoutManager
        }


    }

    //Attaching the callback to interact with activity
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callback = activity
        listener = try {
            activity as ReviewFragmentListener
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString()
                    + " must implement ReviewFragmentListener")
        }
    }

    //Disposing the callback
    override fun onDetach() {
        super.onDetach()
        callback = null
    }

    //Interface to go to the Articles fragment with position
    interface ReviewFragmentListener {
        fun goToArticleFragment(position: Int)
    }
}