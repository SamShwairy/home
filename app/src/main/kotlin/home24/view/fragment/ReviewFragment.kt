package home24.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sam.rijkmuseum.R
import home24.config.Parameters
import home24.databases.entity.ArticleEntity
import home24.view.adapter.SimpleFragmentPagerAdapter
import kotlinx.android.synthetic.main.fragment_review.*


/**
 * Created by sammy on 7/20/2018.
 */
class ReviewFragment: MvvmFragment() {
    //Arraylist of Articles
    private var articlesList : ArrayList<ArticleEntity>? = ArrayList()

    //Getting the list from the activity
    fun newInstance(articleList: ArrayList<ArticleEntity>?): ReviewFragment {
        val fragment = ReviewFragment()
        val bundle = Bundle()
        bundle.putParcelableArrayList(Parameters.ARTICLES_LIST, articleList)
        fragment.arguments = bundle
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_review, container, false)
        articlesList = arguments?.getParcelableArrayList(Parameters.ARTICLES_LIST)
        showToast(getString(R.string.press_on_article))
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let { context ->
            val adapter = SimpleFragmentPagerAdapter(context, context.supportFragmentManager, articlesList)

            // Set the adapter onto the view pager
            viewPager.adapter = adapter

            // Give the TabLayout the ViewPager
            tabLayout?.setupWithViewPager(viewPager)
        }
    }

}