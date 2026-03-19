package com.example.yelp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.yelp.ui.theme.YelpTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            /*
            YelpTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Navigate(modifier = Modifier.padding(innerPadding))
                }
            }
            */
             Navigate2()
        }
    }
}

@Composable
fun Navigate(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "maps", modifier = modifier) {

        composable("maps") {
            DisplayMap(
                onNavigateToYelpListings = { lat, lon ->
                    navController.navigate("YelpListings/$lat/$lon")
                }
            )
        }
        composable(
            route = "YelpListings/{lat}/{long}",
            arguments = listOf(
                navArgument("lat") { type = NavType.StringType },
                navArgument("long") { type = NavType.StringType }
            )
        ) { data ->
            val lat = data.arguments?.getString("lat")?.toDoubleOrNull() ?: 0.0
            val lon = data.arguments?.getString("long")?.toDoubleOrNull() ?: 0.0
            DisplayYelpList(lat, lon)
        }
    }
}

@Composable
fun Navigate2() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "login") {
        composable("login") {
            LoginScreen2(
                onLoginSuccess = {
                    navController.navigate("home")
                }
            )
        }
        composable("home"){
            HomeScreen()
        }
    }
}


@Composable
fun HomeScreen() {
    Text(
        "Hello, welcome to the home screen!"
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    YelpTheme {
        HomeScreen()
    }
}
