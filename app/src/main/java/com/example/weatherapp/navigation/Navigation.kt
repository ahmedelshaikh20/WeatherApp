package com.example.weatherapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.ui.HomeScreen
import com.example.weatherapp.ui.SearchScreen
import com.example.weatherapp.ui.SplashScreen
import com.example.weatherapp.viewmodel.SearchViewmodel
import com.example.weatherapp.viewmodel.searchListviewmodel

@Composable
fun Navigation(searchViewModel : SearchViewmodel = hiltViewModel() , searchListviewmodel: searchListviewmodel = hiltViewModel()) {
  val navController = rememberNavController()
  NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {
    composable(route = Screen.SplashScreen.route) {
      SplashScreen(navController = navController , searchViewModel )
    }
    composable(route = Screen.HomeScreen.route) {
      HomeScreen(navController=navController , searchViewModel , searchListviewmodel)
    }
    composable(route = Screen.SearchScreen.route) {
      SearchScreen(navController=navController , searchListviewmodel)
    }
  }


}
