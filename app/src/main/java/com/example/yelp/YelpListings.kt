package com.example.yelp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DisplayYelpList(modifier: Modifier=Modifier){
    val myYelpList = getFakeData()
    LazyColumn(modifier = modifier) {
        items(myYelpList){currentYelp ->
            YelpBusinessCard(
                yelp = currentYelp,
                modifier = Modifier.padding(1.dp)
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

@Preview(showBackground =true)
@Composable
fun DisplayYelpPreview(){
     DisplayYelpList()
 }