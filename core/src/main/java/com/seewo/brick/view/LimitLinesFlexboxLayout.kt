package com.seewo.brick.view

import android.content.Context
import com.google.android.flexbox.FlexLine
import com.google.android.flexbox.FlexboxLayout

class LimitLinesFlexboxLayout(context: Context): FlexboxLayout(context) {
    private var mMaxLine = NOT_SET
    override fun setMaxLine(maxLine: Int) {
        mMaxLine = maxLine
    }

    override fun getMaxLine(): Int {
        return NOT_SET
    }

    override fun getFlexLinesInternal(): MutableList<FlexLine> {
        val flexLines = super.getFlexLinesInternal()
        if (mMaxLine > 0 && flexLines.size > mMaxLine) {
            flexLines.subList(mMaxLine, flexLines.size).clear()
        }
        return flexLines
    }
}