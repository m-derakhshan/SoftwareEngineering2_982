package de.co.derakhshan.hamdad

class Arrange {

    private fun nullChecking(data: String?): String {
        return if (data.isNullOrEmpty() || data == "null") ""
        else data
    }

    fun safelyPersianConvert(data: String?): String {
        val number = nullChecking(data)
        val result = StringBuilder()
        for (i in number.indices) {
            when (number[i]) {
                '1' -> result.append("۱")
                '2' -> result.append("۲")
                '3' -> result.append("۳")
                '4' -> result.append("۴")
                '5' -> result.append("۵")
                '6' -> result.append("۶")
                '7' -> result.append("۷")
                '8' -> result.append("۸")
                '9' -> result.append("۹")
                '0' -> result.append("۰")
                else -> result.append(number[i])
            }
        }
        return result.toString()
    }

    fun dateStringMaker(date: String): String {
        val time = date.split("-")
        return when (time[1].toInt()) {
            1 -> "${time[2]} فروردین ماه ${time[0]}"
            2 -> "${time[2]} اردیبهشت ماه ${time[0]}"
            3-> "${time[2]} خرداد ماه ${time[0]}"
            4 -> "${time[2]} تیر ماه ${time[0]}"
            5-> "${time[2]} مرداد ماه ${time[0]}"
            6-> "${time[2]} شهریور ماه ${time[0]}"
            7 -> "${time[2]} مهر ماه ${time[0]}"
            8-> "${time[2]} آبان ماه ${time[0]}"
            9-> "${time[2]} آذر ماه ${time[0]}"
            10-> "${time[2]} دی ماه ${time[0]}"
            11 -> "${time[2]} بهمن ماه ${time[0]}"
            else -> "${time[2]} اسفند ماه ${time[0]}"
        }
    }
}