package com.glide.androidbaseproject.ui.custom_adapater

import android.content.Context
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter

import java.util.ArrayList

/**
 * Created by danhtran on 18/05/2016.
 */
class ViewPageCustomTabAdapter(manager: FragmentManager, private val context: Context) :
    FragmentStatePagerAdapter(manager) {
    private val mFragmentList = ArrayList<Fragment>()
    private val mFragmentTitleList = ArrayList<String>()
    private var isShowTitle = true

    init {
        mFragmentList.clear()
    }

    fun setListFragment(arrayList: ArrayList<Fragment>) {
        this.mFragmentList.clear()
        this.mFragmentList.addAll(arrayList)
    }

    fun setListTitle(arrayString: ArrayList<String>) {
        this.mFragmentTitleList.clear()
        this.mFragmentTitleList.addAll(arrayString)
    }

    fun setShowTitle(showTitle: Boolean) {
        isShowTitle = showTitle
    }

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }

    fun addFragment(fragment: Fragment, title: String) {
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (isShowTitle)
            mFragmentTitleList[position]
        else
            null
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    //    public View getTabView(int position) {
    //        View view = LayoutInflater.from(context).inflate(R.layout.item_emoji_tab, null);
    //        ImageView img = (ImageView) view.findViewById(R.id.tvEmojiItem);
    //        FactoryImageLoader.getInstance().displayImage(mFragmentTitleList.get(position), img);
    //        view.requestFocus();
    //        return view;
    //    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        if (position >= count) {
            val manager = (`object` as Fragment).fragmentManager
            val trans = manager!!.beginTransaction()
            trans.remove(`object`)
            trans.commitNow()
        }
    }

    fun getmFragmentList(): List<Fragment> {
        return mFragmentList
    }
}
