package com.najhi.notes.presentation.util

import java.text.SimpleDateFormat
import java.util.Locale

/**
 * @author Muhammad Najhi Ullah
 *         MozgoTech
 *         Author Email: najhi1989@gmail.com
 *         Created on: 22/07/2024
 */
object Utils
{
    fun formatTimeStamp(timeStamp: Long): String
    {
        return SimpleDateFormat("d MMM yyyy", Locale.US).format(timeStamp)
    }
}