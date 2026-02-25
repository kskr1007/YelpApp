package com.example.yelp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState



@Composable
fun DisplayMap(modifier: Modifier = Modifier) {

    val singapore = LatLng(1.35, 103.87)

    val markerState = remember { MarkerState(singapore) }

    var addressInfo by remember { mutableStateOf("") }

    val cameraPositionState = rememberCameraPositionState {
        CameraPosition.fromLatLngZoom(singapore, 10f)
    }

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
            state= markerState,
            title = addressInfo,
            snippet = "${markerState.position.latitude}, " + {markerState.position.longitude}
        )
    }
}

