package com.stevelukis.dialogpro.listener

/**
 * Implement this interface to listen when the page is changed to other page.
 */
interface OnPageSelectedListener {

    /**
     * @param pagePosition position of the current page. Zero-based indexing.
     */
    fun onPageSelected(pagePosition: Int)

}