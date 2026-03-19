package com.example.yelp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun DisplayMap(modifier: Modifier = Modifier, onNavigateToYelpListings: (Double, Double) -> Unit) {

    val singapore = LatLng(1.35, 103.87)

    val markerState = remember { MarkerState(singapore) }

    var addressInfo by remember { mutableStateOf("") }

    val cameraPositionState = rememberCameraPositionState {
        CameraPosition.fromLatLngZoom(singapore, 10f)
    }
    Box(modifier=modifier.fillMaxSize())
    {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            onMapLongClick = {
                markerState.position = it
                addressInfo = "Resolving Address..."
                println("Long clicked at ${it.latitude}, ${it.longitude}")
            }
        )
        {
            Marker(
                state = markerState,
                title = addressInfo,
                snippet = "${markerState.position.latitude}, ${markerState.position.longitude}"
            )
        }
        Button(
            onClick = {
                onNavigateToYelpListings(markerState.position.latitude, markerState.position.longitude)
            },
            modifier = Modifier
                .align (Alignment.BottomCenter )
                .padding(16.dp)
        ){
            Text(text = "go to YelpListings")
        }
    }
}
