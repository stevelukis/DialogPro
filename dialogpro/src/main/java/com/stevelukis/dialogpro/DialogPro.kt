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
    private var flag = FLAG_KEEP

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.dialog_pro, container).let {
                val htViewPager = it.findViewById<ViewPager>(R.id.viewPager)

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

                htViewPager.adapter = pagerAdapter

                val htViewPagerArrowIndicator = it.findViewById<ViewPagerArrowIndicator>(R.id.viewPagerArrowIndicator)
                htViewPagerArrowIndicator.bind(htViewPager)
                htViewPagerArrowIndicator.setArrowIndicatorRes(R.drawable.left_arrow, R.drawable.right_arrow)

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