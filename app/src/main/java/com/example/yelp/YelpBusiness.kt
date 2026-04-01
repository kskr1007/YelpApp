package com.example.yelp

data class YelpBusiness(
    val restaurantName: String = "",
    val category: String = "",
    val rating: Double = 0.0,
    val icon: String = "",
    val url: String = ""
)
