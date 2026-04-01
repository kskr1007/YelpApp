package com.example.yelp
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.database
import com.google.firebase.database.ValueEventListener

@Composable
fun DisplayYelpList2(fbRef: String,modifier: Modifier=Modifier) {
    val yelpList = remember { mutableStateListOf<YelpBusiness>() }
    val context = LocalContext.current

    //read from the Firebase Database
    LaunchedEffect(fbRef) {
        val yelpRef = Firebase.database.getReference(fbRef)

        yelpRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!snapshot.exists()) {
                    Toast.makeText(context, "No data found at yelpRef", Toast.LENGTH_LONG).show()
                    return
                }

                yelpList.clear()
                snapshot.children
                    .mapNotNull { it.getValue(YelpBusiness::class.java) }
                    .forEach { yelpList.add(it) }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed to load data", Toast.LENGTH_SHORT).show()
            }
        })
    }
    LazyColumn(modifier = modifier) {
        items(yelpList) { currentYelp ->
            YelpBusinessCard(
                yelp = currentYelp,
                modifier = Modifier.padding(1.dp)
            )
        }
    }
}

@Composable
fun YelpScreen(modifier: Modifier = Modifier) {
    var showForm by remember { mutableStateOf(false) }
    val fbRef="yelpBusinesses"
   // val testYelp=YelpBusiness("Cobb2Panera","Breakfast",4.2,"https://...", "none")
    //addYelpBusinessToFirebase(fbRef,testYelp)

    Box(modifier = modifier.fillMaxSize()) {
        Column {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                "My Firebase Restaurants",
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            if (showForm) {
                YelpBusinessForm(
                    onSubmit = { newBusiness ->
                        addYelpBusinessToFirebase(fbRef, newBusiness)
                        showForm = false // hide form after submit
                    }
                )
            }
            DisplayYelpList2(fbRef)
        }
//add FloatingActionButton
        FloatingActionButton(
            onClick={showForm=!showForm},
            modifier= Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            if (showForm){
                Icon(
                    painter=painterResource(R.drawable.cancel_24),
                    contentDescription = "Cancel Add"

                )
            } else{
                Icon(
                    painter=painterResource(R.drawable.add_24),
                    contentDescription="Add Yelp"
                )
            }
        }

    }


}


@Composable
fun YelpBusinessCard2(yelp: YelpBusiness, modifier:Modifier=Modifier){
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
            Image( painter = painterResource(R.drawable.adventure_brewing),
                // AsyncImage(
                //model=yelp.icon,
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
fun addYelpBusinessToFirebase(fbRef: String,business: YelpBusiness) {
    val yelpRef = Firebase.database.getReference(fbRef)
    val newBusinessRef = yelpRef.push() // creates a unique ID
    newBusinessRef.setValue(business)
        .addOnSuccessListener {
            Log.d("FirebaseWrite", "Business added successfully")
        }
        .addOnFailureListener { error ->
            Log.e("FirebaseWrite", "Failed to add business", error)
        }
}

@Composable
fun YelpBusinessForm(onSubmit: (YelpBusiness)-> Unit,
                     modifier: Modifier = Modifier) {
    var name by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var rating by remember { mutableStateOf("") }
    var icon by remember { mutableStateOf("") }
    var url by remember { mutableStateOf("") }

    Column(modifier = modifier.padding(16.dp)) {
        TextField(value = name, onValueChange = { name = it }, label = { Text("Restaurant Name") })
        TextField(value = category, onValueChange = { category = it }, label = { Text("Category") })
        TextField(value = rating, onValueChange = { rating = it }, label = { Text("Rating") }, keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        )
        )
        TextField(value = icon, onValueChange = { icon = it }, label = { Text("Icon") })
        TextField(value = url, onValueChange = { url = it }, label = { Text("URL") })

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = {
            val parsedRating = rating.toDoubleOrNull() ?: 3.0

            val newBusiness = YelpBusiness(
                restaurantName = name,
                category = category,
                rating = parsedRating,
                icon = icon,
                url = url
            )
            onSubmit(newBusiness)
        }) {
            Text("Submit Business")
        }
    }
}