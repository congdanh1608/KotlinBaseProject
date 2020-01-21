package com.danhtran.androidbaseproject.ui.activity.tour

import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.danhtran.androidbaseproject.R
import com.danhtran.androidbaseproject.databinding.ActivityTourBinding
import com.danhtran.androidbaseproject.ui.activity.BaseAppCompatActivity
import com.danhtran.androidbaseproject.ui.activity.main.MainActivity

/**
 * Created by DanhTran on 8/13/2019.
 */
class TourActivity : BaseAppCompatActivity(), TourActivityListener {
    private var mBinding: ActivityTourBinding? = null
    private var presenter: TourActivityPresenter? = null

    private var myViewPagerAdapter: MyViewPagerAdapter? = null
    private var layouts: IntArray? = null

    //  viewpager change listener
    internal var viewPagerPageChangeListener: ViewPager.OnPageChangeListener = object : ViewPager.OnPageChangeListener {

        override fun onPageSelected(position: Int) {
            addBottomDots(position)

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts!!.size - 1) {
                // last page. make button text to GOT IT
                mBinding!!.btnDone.visibility = View.VISIBLE
                mBinding!!.btnSkip.visibility = View.GONE

                //last page
                presenter!!.saveFlag()
            } else {
                // still pages are left
                mBinding!!.btnDone.visibility = View.INVISIBLE
                mBinding!!.btnSkip.visibility = View.VISIBLE
            }
        }

        override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {

        }

        override fun onPageScrollStateChanged(arg0: Int) {

        }
    }

    override fun setLayout(): Int {
        return R.layout.activity_tour
    }

    override fun initUI() {
        mBinding = binding as ActivityTourBinding

        // layouts of all welcome sliders
        layouts = intArrayOf(R.layout.item_tour_slider_1, R.layout.item_tour_slider_2, R.layout.item_tour_slider_3)

        // adding bottom dots
        addBottomDots(0)
        myViewPagerAdapter = MyViewPagerAdapter(this, layouts)
        mBinding!!.viewPager.adapter = myViewPagerAdapter
        mBinding!!.viewPager.addOnPageChangeListener(viewPagerPageChangeListener)
    }

    override fun initData() {
        presenter = TourActivityPresenter(this)
        mBinding!!.presenter = presenter
        mBinding!!.executePendingBindings()
    }

    override fun initListener() {

    }

    override fun moveNextTour() {
        // checking for last page
        // if last page home screen will be launched
        val current = getItem(+1)
        if (current < layouts!!.size) {
            // move to next screen
            mBinding!!.viewPager.currentItem = current
        } else {
            launchHomeScreen()
        }
    }

    override fun launchHomeScreen() {
        presenter!!.saveFlag()

        startActivity(MainActivity::class.java.name, null)
    }

    private fun addBottomDots(currentPage: Int) {
        when (currentPage) {
            0 //left
            -> mBinding!!.layoutSlide.gravity = Gravity.START
            1 //center
            -> mBinding!!.layoutSlide.gravity = Gravity.CENTER
            2 //right
            -> mBinding!!.layoutSlide.gravity = Gravity.END
        }
    }

    private fun getItem(i: Int): Int {
        return mBinding!!.viewPager.currentItem + i
    }

    companion object {

        fun createIntent(context: Context): Intent {
            return Intent(context, TourActivity::class.java)
        }
    }
}
