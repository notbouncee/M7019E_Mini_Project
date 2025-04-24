package com.example.quickbite.screens


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class TabItem(val title: String, val icon: ImageVector) {
    object Home : TabItem("Home", Icons.Default.Home)
    object Favourites : TabItem("Favourites", Icons.Default.Star)
}