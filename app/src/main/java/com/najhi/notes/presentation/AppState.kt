package com.najhi.notes.presentation

/**
 * @author Muhammad Najhi Ullah
 *         MozgoTech
 *         Author Email: najhi1989@gmail.com
 *         Created on: 12/08/2024
 */
enum class AppState
{
    DEFAULT, SELECT, SEARCH, SEARCH_SELECT
}

enum class SortBy(val displayName: String)
{
    TITLE("Title"), DATE_CREATED("Date created"), DATE_MODIFIED("Date modified")
}

enum class OrderBy
{
    DESCENDING, ASCENDING
}