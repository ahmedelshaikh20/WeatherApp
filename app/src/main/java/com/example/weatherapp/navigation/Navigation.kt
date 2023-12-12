package com.example.weatherapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.weatherapp.ui.HomeScreen
import com.example.weatherapp.ui.SearchScreen
import com.example.weatherapp.ui.SplashScreen
import com.example.weatherapp.viewmodel.SearchViewModel
import com.example.weatherapp.viewmodel.SearchListViewModel

@Composable
fun Navigation(
  searchViewModel: SearchViewModel = hiltViewModel(),
  searchListviewmodel: SearchListViewModel = hiltViewModel()
) {
  val navController = rememberNavController()
  NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {
    composable(route = Screen.SplashScreen.route) {
      SplashScreen(navController = navController, searchViewModel)
    }
    composable(route = Screen.HomeScreen.route) {
      HomeScreen(navController = navController, searchViewModel)
    }
    composable(route = Screen.SearchScreen.route+"/{query}", arguments = listOf(navArgument(name = "query") {
      type = NavType.StringType
      defaultValue =" "
      nullable = true
    })) {entry->
      SearchScreen(navController = navController, query = entry.arguments?.getString("query") ,searchListviewmodel)
    }
  }


}
