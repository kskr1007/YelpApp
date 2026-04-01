package com.example.yelp

import java.io.Serializable


data class YelpBusiness2(
    val restaurantName: String="",
    val category:String="",
    val rating: Double=0.0,
    val icon: String="",
    val url: String=""
): Serializable