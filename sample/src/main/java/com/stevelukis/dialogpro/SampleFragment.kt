package com.stevelukis.dialogpro

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_sample.view.*

class SampleFragment : Fragment() {

    companion object {
        const val ARG_TITLE = "a"
        const val ARG_CONTENT = "b"

    }

    private lateinit var title: String
    private lateinit var content: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.let {
                val view = it.inflate(R.layout.fragment_sample, container, false)

                arguments?.takeIf { it.containsKey(ARG_TITLE) && it.containsKey(ARG_CONTENT) }?.apply {
                    view.titleTextView.text = getString(ARG_TITLE)
                    view.contentTextView.text = getString(ARG_CONTENT)
                }

                view
            }

}