package com.example.yelp

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.core.net.toUri
import coil.compose.AsyncImage
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
    val context= LocalContext.current
    Card(modifier=Modifier.fillMaxWidth()
        .padding(1.dp)
        //add
        .clickable(
            onClick={
                val yelpCardIntent= Intent(Intent.ACTION_VIEW)
                    .apply{
                        data= yelp.url.toUri()
                    }
                context.startActivity(yelpCardIntent)
            }
        )

    )  {

        Row(modifier=Modifier.padding(2.dp)) {
            // Image(
            // painter = painterResource(R.drawable.adventure_brewing),
            AsyncImage(
                model=yelp.icon,
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .padding(1.dp)
            )
            //AsyncImage  use model verses painter. model=yelp.icon
            Spacer(modifier=Modifier.width(5.dp))
            Column() {
                Text(yelp.restaurantName)
                Text(yelp.category)
                Text(yelp.rating.toString())
                //Text(yelp.url)
            }

        }
    }
}
