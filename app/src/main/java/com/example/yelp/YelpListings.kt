package com.example.yelp

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun DisplayYelpList(lat: Double, lon: Double) {

    val context = LocalContext.current
    val yelpManager = YelpManager()
    val apiKey = context.getString(R.string.YelpKey)

    var myYelpList by remember { mutableStateOf<List<YelpBusiness>>(emptyList()) }

    LaunchedEffect(lat, lon) {
        val result = withContext(Dispatchers.IO) {
            yelpManager.retrieveYelps(lat, lon, apiKey)
        }
        myYelpList = result
        Log.d("YelpCount", "myYelpList is ${myYelpList.size}")
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(myYelpList) { currentYelp ->
            YelpBusinessCard(
                yelp = currentYelp,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun YelpBusinessCard(yelp: YelpBusiness, modifier:Modifier=Modifier){
    Card(modifier=Modifier.fillMaxWidth()
        .padding(1.dp)){
        Row(modifier=Modifier.padding(2.dp)) {
            Image(
                painter = painterResource(R.drawable.adventure_brewing),
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .padding(1.dp)
            )
            Spacer(modifier=Modifier.width(5.dp))
            Column() {
                Text(yelp.restaurantName)
                Text(yelp.category)
                Text(yelp.rating.toString())
                Text(yelp.url)
            }

        }
    }
}


fun getFakeData():List<YelpBusiness>{
    return listOf(
        YelpBusiness("Panera","Breakfast",4.2,"https://...", "none"),
        YelpBusiness("WingsToGo","Comfort",5.3,"https://...", "none"),
        YelpBusiness("Log Cabin","Seafood",4.2,"https://...", "none"),
        YelpBusiness("Dunkin Donut","Breakfast",3.1,"https://...", "none"),
        YelpBusiness("Starbucks","Coffee",3.1,"https://...", "none"),
        YelpBusiness("Panera","Breakfast",3.1,"https://...", "none"),
        YelpBusiness("Panera","Breakfast",3.1,"https://...", "none"),
        YelpBusiness("Panera","Breakfast",2.8,"https://...", "none"),
        YelpBusiness("Panera","Breakfast",5.3,"https://...", "none"),
        YelpBusiness(icon="https://....", category="Dinner", rating=1.2, restaurantName ="Subway",url="none"),
        YelpBusiness("Panera","Breakfast",4.2,"https://...", "none"),
        YelpBusiness("WingsToGo","Comfort",5.3,"https://...", "none"),
        YelpBusiness("Log Cabin","Seafood",4.2,"https://...", "none"),
        YelpBusiness("Dunkin Donut","Breakfast",3.1,"https://...", "none"),
        YelpBusiness("Starbucks","Coffee",3.1,"https://...", "none"),
        YelpBusiness("Panera","Breakfast",3.1,"https://...", "none"),
        YelpBusiness("Panera","Breakfast",3.1,"https://...", "none"),
        YelpBusiness("Panera","Breakfast",2.8,"https://...", "none"),
        YelpBusiness("Panera","Breakfast",5.3,"https://...", "none")
    )
}

/*
@Preview(showBackground =true)
@Composable
fun YelpCardPreview(){
    val yelp = YelpBusiness("Panera","Breakfast",4.2,"https://...", "none")
    YelpBusinessCard(yelp)
}
 */

//@Preview(showBackground =true)
@Composable
fun DisplayYelpPreview(){
     DisplayYelpList(55.67,45.67)
 }