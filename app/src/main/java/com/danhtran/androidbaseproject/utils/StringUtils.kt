package com.danhtran.androidbaseproject.utils

import java.text.DecimalFormat

/**
 * Created by danhtran on 1/4/2019.
 */
object StringUtils {
    fun formatDecimal(value: Double): String {

        val pattern = "#,###.##"
        val decimalFormat = DecimalFormat(pattern)
        decimalFormat.groupingSize = 3

        return decimalFormat.format(value)
    }

    fun formatDecimal2(value: Double): String {

        val pattern = "#,#00.00"
        val decimalFormat = DecimalFormat(pattern)
        decimalFormat.groupingSize = 3

        return decimalFormat.format(value)
    }
}
