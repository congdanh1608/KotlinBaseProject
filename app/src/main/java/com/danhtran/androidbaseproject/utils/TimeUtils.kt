package com.danhtran.androidbaseproject.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by danhtran on 11/19/2018.
 */

object TimeUtils {

    /**
     * Get list name of month in the past
     *
     * @return
     */
    //<code>JANUARY</code> which is 0
    val nameOldMonthYTD: Array<String?>
        get() {
            val monthCount = numberOfMonthsUntillNow()
            val months = arrayOfNulls<String>(monthCount)

            val format = SimpleDateFormat("MMM", Locale.getDefault())
            val calendar = Calendar.getInstance().clone() as Calendar
            calendar.add(Calendar.MONTH, -monthCount + 1)

            var count = 0
            do {
                months[count] = format.format(calendar.time)
                count++
                calendar.add(Calendar.MONTH, 1)
            } while (count < monthCount)

            return months
        }
    /**
     * Format moment ago for time
     *
     * implementation 'org.ocpsoft.prettytime:prettytime...'
     *
     * @return String
     */
    /*public static String getTimeInfo() {
        PrettyTime p = new PrettyTime(Locale.getDefault());
        Calendar calendar = (Calendar) Calendar.getInstance().clone();
        calendar.add(Calendar.MINUTE, -2);
        calendar.add(Calendar.SECOND, -40);
        String ago = p.format(calendar.getTime());
        return String.valueOf(ago);
    }*/

    /**
     * Get list name of monday in the past
     *
     * @param weekCount how many month from now you want to get
     * @return
     */
    fun getNameOldMondays(weekCount: Int): Array<String?> {
        val days = arrayOfNulls<String>(weekCount)

        val format = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        val calendar = Calendar.getInstance().clone() as Calendar
        calendar.add(Calendar.DAY_OF_YEAR, -(weekCount * 7))   //a week has 7 days

        var count = 0
        do {
            val day = calendar.get(Calendar.DAY_OF_WEEK)
            // check if it is a Saturday or Sunday
            if (day == Calendar.MONDAY) {
                days[count] = format.format(calendar.time)
                count++
            }
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        } while (count < weekCount)

        return days
    }

    /**
     * get count of first month to now
     *
     * @return
     */
    fun numberOfMonthsUntillNow(): Int {
        val calendar = Calendar.getInstance().clone() as Calendar
        //<code>JANUARY</code> which is 0
        return calendar.get(Calendar.MONTH) + 1
    }

    /**
     * Return MONDAY of the weekIndex ago from the current time
     *
     * @param weekIndexAgo
     * @return
     */
    fun startDateOfWeekFromNow(weekIndexAgo: Int): Date {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, -weekIndexAgo * 7)
        return startDateOfWeek(cal.time)
    }

    /**
     * Return SUNDAY of the weekIndex ago from current time
     *
     * @param weekIndexAgo
     * @return
     */
    fun endDateOfWeekFromNow(weekIndexAgo: Int): Date {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, -weekIndexAgo * 7)
        return endDateOfWeek(cal.time)
    }

    /**
     * Return the MONDAY 00:00:00 of the week of the given date
     *
     * @param date
     * @return
     */
    private fun startDateOfWeek(date: Date): Date {

        val cal = Calendar.getInstance()
        cal.time = date

        var dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)
        while (dayOfWeek != Calendar.SUNDAY) {
            cal.add(Calendar.DAY_OF_YEAR, -1)
            dayOfWeek = dayOfWeek - 1
        }

        // Make sure we return the MONDAY of the week
        cal.add(Calendar.DAY_OF_YEAR, -1)
        cal.set(Calendar.HOUR, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)

        return cal.time
    }

    /**
     * Return the SUNDAY 23:59:59 of the week of the given date
     *
     * @param date
     * @return
     */
    private fun endDateOfWeek(date: Date): Date {

        val cal = Calendar.getInstance()
        cal.time = date

        var dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)
        while (dayOfWeek != Calendar.SATURDAY) {
            cal.add(Calendar.DAY_OF_YEAR, +1)
            dayOfWeek = dayOfWeek + 1
        }

        // Make sure we return the SUNDAY of the week
        cal.add(Calendar.DAY_OF_YEAR, +1)
        cal.set(Calendar.HOUR, 23)
        cal.set(Calendar.MINUTE, 59)
        cal.set(Calendar.SECOND, 59)

        return cal.time
    }

    fun startDateOfMonthFromNow(monthIndexAgo: Int): Date {

        val calendar = Calendar.getInstance()
        val curMonth = calendar.get(Calendar.MONTH)
        calendar.set(Calendar.MONTH, curMonth - monthIndexAgo + 1)

        //The first day of the month has value 1.
        calendar.set(Calendar.DAY_OF_MONTH, 1)

        calendar.set(Calendar.HOUR, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        return calendar.time
    }

    fun endDateOfMonthFromNow(monthIndexAgo: Int): Date {

        val calendar = Calendar.getInstance()
        val curMonth = calendar.get(Calendar.MONTH)
        calendar.set(Calendar.MONTH, curMonth - monthIndexAgo + 2)

        //The first day of the month has value 1.
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.add(Calendar.DAY_OF_YEAR, -1)

        calendar.set(Calendar.HOUR, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)

        return calendar.time
    }
}
