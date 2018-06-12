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

class DialogPro : DialogFragment() {

    companion object {

        const val FLAG_DESTROY_IMMEDIATELY = 78
        const val FLAG_KEEP = 79

        fun show(activity: AppCompatActivity, fragments: Array<Fragment>, flag: Int): DialogPro {
            val dialog = DialogPro()
            dialog.fragments = fragments
            dialog.flag = flag
            dialog.show(activity.supportFragmentManager, "")
            return dialog
        }

    }

    private lateinit var fragments: Array<Fragment>
    private var leftArrowResId = R.drawable.left_arrow
    private var rightArrowResId = R.drawable.right_arrow
    private var onPageSelectedListener: OnPageSelectedListener? = null
    private var flag = FLAG_KEEP

    fun setFlag(flag: Int): DialogPro =
            this.let {
                it.flag = flag
                it
            }

    fun setFragments(fragments: Array<Fragment>): DialogPro =
            this.let {
                it.fragments = fragments
                it
            }

    fun setOnPageSelectedListener(onPageSelectedListener: OnPageSelectedListener): DialogPro =
            this.let {
                it.onPageSelectedListener = onPageSelectedListener
                it
            }

    fun setArrowFromRes(leftArrowResId: Int, rightArrowResId: Int): DialogPro {
        this.leftArrowResId = leftArrowResId
        this.rightArrowResId = rightArrowResId
        return this
    }

    fun show(activity: AppCompatActivity): DialogPro =
            this.let {
                it.show(activity.supportFragmentManager, "")
                it
            }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.dialog_pro, container).let {
                val viewPager = it.findViewById<ViewPager>(R.id.viewPager)

                val adapter = ProPagerAdapter(fragments)
                val fragmentManager = childFragmentManager

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
                viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                    override fun onPageScrollStateChanged(state: Int) {}

                    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

                    override fun onPageSelected(position: Int) {
                        onPageSelectedListener?.onPageSelected(position)
                    }
                })


                val viewPagerArrowIndicator = it.findViewById<ViewPagerArrowIndicator>(R.id.viewPagerArrowIndicator)
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