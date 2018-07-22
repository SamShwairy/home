package home24.view.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.sam.rijkmuseum.R
import home24.databases.entity.ArticleEntity
import home24.view.fragment.ListGridReviewFragment

/**
 * Created by sammy on 7/21/2018
 */
//View pager adapter
class SimpleFragmentPagerAdapter(private val mContext: Context, fm: FragmentManager,
                                 private val articlesList: ArrayList<ArticleEntity>?) : FragmentStatePagerAdapter(fm) {

    // This determines the fragment for each tab
    override fun getItem(position: Int): Fragment {
        return if (position == 0) {
            ListGridReviewFragment().newInstance(articlesList, position)
        } else {
            ListGridReviewFragment().newInstance(articlesList, position)
        }
    }

    // This determines the number of tabs
    override fun getCount(): Int {
        return 2
    }

    // This determines the title for each tab
    override fun getPageTitle(position: Int): CharSequence? {
        // Generate title based on item position
        when (position) {
            0 -> return mContext.getString(R.string.list)
            1 -> return mContext.getString(R.string.grid)
        }
        return null
    }

}
