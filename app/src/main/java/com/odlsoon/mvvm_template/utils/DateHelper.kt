package com.odlsoon.mvvm_template.utils

import android.annotation.SuppressLint
import com.odlsoon.mvvm_template.utils.model.DateSplit
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateHelper {

    const val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
    const val DATE_FORMAT_LITE = "yyyy-MM-dd"

    /**
     * 1 to "Januari"
     */
    fun parseMonthCodeToString(month: Int): String{
        val monthNames = arrayListOf(
            "","Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"
        )
        return monthNames[month+1]
    }

    /**
     * 1 to "Januari"
     */
    fun parseMonthCodeToStringEnglish(month: Int): String{
        val monthNames = arrayListOf(
            "","January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
        )
        return monthNames[month+1]
    }

    /**
     * "Januari" to 1
     */
    fun parseMonthStringToCode(month: String): Int{
        var code = 1

        val monthNames = arrayListOf(
            "", "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"
        )

        monthNames.forEachIndexed { index, s ->
            if(s == month) code = index
        }

        return code
    }

    /**
     * "Januari" to 1
     */
    fun parseMonthStringToCodeInEnglish(month: String): Int{
        var code = 1

        val monthNames = arrayListOf(
            "","January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
        )

        monthNames.forEachIndexed { index, s ->
            if(s == month) code = index
        }

        return code
    }

    /**
     * 1 to Senin
     */
    fun parseDayCodeToString(day: Int): String{
        val dayNames = arrayListOf<String>(
            "", "Minggu", "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu"
        )
        return dayNames[day]
    }

    /**
     * 1 to Sunday
     */
    fun parseDayCodeToStringInEnglish(day: Int): String{
        val dayNames = arrayListOf<String>(
            "", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
        )
        return dayNames[day]
    }

    /**
     * Date Format yyyy-MM-dd HH:mm to yyyy-MM-dd HH:mm:ss
     */
    fun parseDateStringFormat(dateString: String, originFormat: String, resultFormat: String): String {
        return try {
            val formatter = SimpleDateFormat(originFormat, Locale.US)
            val resultFormat = SimpleDateFormat(resultFormat, Locale.US)
            resultFormat.format(formatter.parse(dateString))
        } catch (e: ParseException) {""}
    }

    /**
     * Date Format yyyy-MM-dd HH:mm:ss to Date Format
     */
    fun parseDateStringToDate(dateString: String, originFormat: String) : Date {
        return try{
            SimpleDateFormat(originFormat, Locale.US).parse(dateString)
        }catch (e: Exception){
            Date()
        }
    }

    /**
     * Date Format yyyy-MM-dd HH:mm:ss to DateSplit Format
     */
    fun parseStringToDateSplit(dateString: String, originFormat: String): DateSplit{
        val date = parseDateStringToDate(dateString, originFormat)
        return parseDateToDateSplit(date)
    }

    /**
     * create 1 june 2000 from date picker
     */
    fun parseDatePickerToString(year: Int, month: Int, day: Int): String{
        val monthName = parseMonthCodeToString(month)
        return "$day $monthName $year"
    }

    /**
     * 1 june 2000 to yyyy-MM-dd HH:mm:ss
     */
    fun parseDateStringToDateStringFormat(dateString: String, resultFormat: String): String{
        val strings = dateString.trim().split(" "[0])

        val day = strings[0]
        val monthName = strings[1]
        val year = strings[2]
        val month: Int = parseMonthStringToCode(monthName)
        val simpleDate = "$year-$month-$day"

        return parseDateStringFormat(
            simpleDate,
            DATE_FORMAT_LITE,
            resultFormat
        )
    }

    /**
     * Date Now into yyyy-MM-dd HH:mm:ss
     */
    @SuppressLint("SimpleDateFormat")
    fun parseDateNowToDateStringFormat(originFormat: String, addDay: Int): String{
        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, addDay)
        val format = SimpleDateFormat(originFormat)
        return format.format(cal.time)
    }

    /**
     * yyyy-MM-dd HH:mm:ss to 1 januari 2000
     */
    fun parseDateStringFormatToDateString(dateString: String, originFormat: String, addDay: Int): String{
        val date = parseDateStringToDate(dateString, originFormat)
        val c = Calendar.getInstance()

        c.time = date
        c.add(Calendar.DATE, addDay)

        val y = c.get(Calendar.YEAR)
        val m = c.get(Calendar.MONTH)
        val d = c.get(Calendar.DAY_OF_MONTH)

        val monthName = parseMonthCodeToString(m)
        val viewDateString = "$d $monthName $y"

        return viewDateString
    }

    /**
     * Date To DateSplit
     */
    fun parseDateToDateSplit(date: Date): DateSplit {
        val c = Calendar.getInstance()
        c.time = date

        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_WEEK)
        val dayInMonth = c.get(Calendar.DAY_OF_MONTH)
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.HOUR_OF_DAY)
        val second = c.get(Calendar.SECOND)

        return DateSplit(second, minute, hour, day, dayInMonth, month, year)
    }

}