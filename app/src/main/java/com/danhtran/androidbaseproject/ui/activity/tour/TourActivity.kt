package com.danhtran.androidbaseproject.ui.activity.tour

import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.observe
import androidx.viewpager.widget.ViewPager
import com.danhtran.androidbaseproject.R
import com.danhtran.androidbaseproject.databinding.ActivityTourBinding
import com.danhtran.androidbaseproject.extras.enums.HawkKey
import com.danhtran.androidbaseproject.ui.activity.BaseActivityViewModel
import com.danhtran.androidbaseproject.ui.activity.BaseAppCompatActivity
import com.danhtran.androidbaseproject.ui.activity.main.MainActivity
import com.orhanobut.hawk.Hawk

/**
 * Created by DanhTran on 8/13/2019.
 */
class TourActivity : BaseAppCompatActivity() {
    private var mBinding: ActivityTourBinding? = null
    private val tourActivityVM: TourActivityVM by viewModels { TourActivityVMFactory() }

    private var myViewPagerAdapter: MyViewPagerAdapter? = null

    // layouts of all welcome sliders
    private val layouts =
        intArrayOf(R.layout.item_tour_slider_1, R.layout.item_tour_slider_2, R.layout.item_tour_slider_3)

    //  viewpager change listener
    private var viewPagerPageChangeListener: ViewPager.OnPageChangeListener = object : ViewPager.OnPageChangeListener {

        override fun onPageSelected(position: Int) {
            addBottomDots(position)

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.size - 1) {
                // last page. make button text to GOT IT
                mBinding?.btnDone?.visibility = View.VISIBLE
                mBinding?.btnSkip?.visibility = View.GONE

                //last page
                saveFlag()
            } else {
                // still pages are left
                mBinding?.btnDone?.visibility = View.INVISIBLE
                mBinding?.btnSkip?.visibility = View.VISIBLE
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

        // adding bottom dots
        addBottomDots(0)
        myViewPagerAdapter = MyViewPagerAdapter(this, layouts)
        mBinding?.viewPager?.adapter = myViewPagerAdapter
        mBinding?.viewPager?.addOnPageChangeListener(viewPagerPageChangeListener)
    }

    override fun initViewModel(): BaseActivityViewModel? {
        tourActivityVM.doneAction.observe(this) {
            moveNextTour()
        }
        tourActivityVM.skipAction.observe(this) {
            launchHomeScreen()
        }
        return tourActivityVM
    }

    override fun initData() {
        mBinding?.viewModel = tourActivityVM
    }

    override fun initListener() {

    }

    private fun moveNextTour() {
        // checking for last page
        // if last page home screen will be launched
        val current = getItem(+1)
        if (current < layouts.size) {
            // move to next screen
            mBinding?.viewPager?.currentItem = current
        } else {
            launchHomeScreen()
        }
    }

    private fun launchHomeScreen() {
        saveFlag()

        startActivityAsRoot(MainActivity::class.java.name, null)
    }

    private fun addBottomDots(currentPage: Int) {
        when (currentPage) {
            0 //left
            -> mBinding?.layoutSlide?.gravity = Gravity.START
            1 //center
            -> mBinding?.layoutSlide?.gravity = Gravity.CENTER
            2 //right
            -> mBinding?.layoutSlide?.gravity = Gravity.END
        }
    }

    private fun getItem(i: Int): Int {
        return (mBinding?.viewPager?.currentItem ?: 0) + i
    }

    private fun saveFlag() {
        Hawk.put(HawkKey.IS_NOT_FIRST_VIEW.value, true)
    }

    companion object {

        fun createIntent(context: Context): Intent {
            return Intent(context, TourActivity::class.java)
        }
    }
}
