package com.stevelukis.dialogpro

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sembozdemir.viewpagerarrowindicator.library.ViewPagerArrowIndicator
import com.stevelukis.dialogpro.listener.OnPageSelectedListener

/**
 * Dialog which has multiple pages and can be explored by swiping.
 */
class DialogPro : DialogFragment() {

    companion object {

        /**
         * Fragments will be destroyed immediately after leaving the screen.
         * May be expensive in retrieving fragment multiple times.
         * Suitable if you have a lot of fragments.
         */
        const val FLAG_DESTROY_IMMEDIATELY = 78

        /**
         * Fragments will be keep in memory after leaving the screen.
         * May use a lot of memory if you have a lot of fragments.
         * Suitable if you only have a few fragments.
         */
        const val FLAG_KEEP = 79

    }

    private lateinit var fragments: Array<Fragment>
    private var leftArrowResId = R.drawable.left_arrow
    private var rightArrowResId = R.drawable.right_arrow
    private var onPageSelectedListener: OnPageSelectedListener? = null
    private var flag = FLAG_KEEP

    /**
     * Set which kind of memory handling will be used.
     * <code>FLAG_KEEP</code> is the default.
     */
    fun setFlag(flag: Int): DialogPro =
            this.let {
                it.flag = flag
                it
            }

    /**
     * Set the fragments which will be shown.
     * @param fragments array of Fragment.
     */
    fun setFragments(fragments: Array<Fragment>): DialogPro =
            this.let {
                it.fragments = fragments
                it
            }

    /**
     * Set the listener when page is changed.
     * @param onPageSelectedListener implementation of <code>OnPageSelectedListener</code>
     */
    fun setOnPageSelectedListener(onPageSelectedListener: OnPageSelectedListener): DialogPro =
            this.let {
                it.onPageSelectedListener = onPageSelectedListener
                it
            }

    /**
     * Set custom left/right arrow.
     * @param leftArrowResId resource id of left arrow
     * @param rightArrowResId resource id of right arrow
     */
    fun setArrowFromRes(leftArrowResId: Int, rightArrowResId: Int): DialogPro {
        this.leftArrowResId = leftArrowResId
        this.rightArrowResId = rightArrowResId
        return this
    }

    /**
     * Show the dialog.
     * @param activity Activity where this Dialog will be shown.
     */
    fun show(activity: AppCompatActivity): DialogPro =
            this.let {
                it.show(activity.supportFragmentManager, "")
                it
            }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.dialog_pro, container).let {
                val viewPager = it.findViewById<ViewPager>(R.id.proViewPager)

                val adapter = ProPagerAdapter(fragments)
                val fragmentManager = childFragmentManager

                // Use the appropriate pager adapter according to the flag.
                val pagerAdapter: PagerAdapter = when (flag) {
                    FLAG_DESTROY_IMMEDIATELY -> {
                        object : FragmentPagerAdapter(fragmentManager) {
                            override fun getItem(position: Int): Fragment =
                                    adapter.getItem(position)

                            override fun getCount(): Int =
                                    adapter.getCount()

                        }
                    }

                    else -> {
                        object : FragmentStatePagerAdapter(fragmentManager) {
                            override fun getItem(position: Int): Fragment =
                                    adapter.getItem(position)

                            override fun getCount(): Int =
                                    adapter.getCount()

                        }
                    }
                }

                viewPager.adapter = pagerAdapter

                //Add the listener
                viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                    override fun onPageScrollStateChanged(state: Int) {}

                    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

                    override fun onPageSelected(position: Int) {
                        onPageSelectedListener?.onPageSelected(position)
                    }
                })

                //Add the left/right arrow.
                val viewPagerArrowIndicator = it.findViewById<ViewPagerArrowIndicator>(R.id.proViewPagerArrowIndicator)
                viewPagerArrowIndicator.bind(viewPager)
                viewPagerArrowIndicator.setArrowIndicatorRes(leftArrowResId, rightArrowResId)

                it
            }

    override fun onStart() {
        super.onStart()

        val dialog = dialog
        if (dialog != null) {
            dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }


    private class ProPagerAdapter(private val fragments: Array<Fragment>) {

        fun getItem(position: Int): android.support.v4.app.Fragment {
            return fragments[position]
        }

        fun getCount(): Int = fragments.size
    }

}