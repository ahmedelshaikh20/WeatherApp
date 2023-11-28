package com.example.weatherapp.navigation

sealed class Screen(val route:String){
  object SplashScreen : Screen("splash_screen")
  object HomeScreen : Screen("home_screen")
  object SearchScreen : Screen("search_screen")
}
