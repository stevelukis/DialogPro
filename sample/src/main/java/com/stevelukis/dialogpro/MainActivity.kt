package com.stevelukis.dialogpro

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.stevelukis.dialogpro.listener.OnPageSelectedListener

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    @Suppress("UNUSED_PARAMETER")
    fun onClick(view: View) {
        val fragments = arrayOf<Fragment>(
                SampleFragment().let {
                    it.arguments = Bundle().apply {
                        putString(SampleFragment.ARG_TITLE, "a")
                        putString(SampleFragment.ARG_CONTENT, "b")
                    }
                    it
                },
                SampleFragment().let {
                    it.arguments = Bundle().apply {
                        putString(SampleFragment.ARG_TITLE, "c")
                        putString(SampleFragment.ARG_CONTENT, "d")
                    }
                    it
                },
                SampleFragment().let {
                    it.arguments = Bundle().apply {
                        putString(SampleFragment.ARG_TITLE, "e")
                        putString(SampleFragment.ARG_CONTENT, "f")
                    }
                    it
                }
        )

        DialogPro()
                .setFlag(DialogPro.FLAG_KEEP)
                .setFragments(fragments)
                .setOnPageSelectedListener(object : OnPageSelectedListener {
                    override fun onPageSelected(pagePosition: Int) {
                        Log.e("steve", pagePosition.toString())
                    }
                })
                .show(this)
    }
}
