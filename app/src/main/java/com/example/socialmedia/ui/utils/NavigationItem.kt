package com.example.socialmedia.ui.utils

import com.example.socialmedia.R

sealed class NavigationItem(var route: String, var icon: Int, var title: String) {
    data object Home : NavigationItem("home", R.drawable.baseline_home_filled_24, "Home")
    data object Add : NavigationItem("add", R.drawable.baseline_add_circle_outline_24, "Add")
    data object Activities : NavigationItem("activities", R.drawable.baseline_favorite_border_24, "Activities")
    data object Following : NavigationItem("following", R.drawable.following, "Following")
    data object Profile : NavigationItem("profile", R.drawable.baseline_person_2_24, "Profile")
}