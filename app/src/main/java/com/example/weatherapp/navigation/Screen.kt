package com.example.weatherapp.navigation

sealed class Screen(val route:String){
  object SplashScreen : Screen("splash_screen")
  object HomeScreen : Screen("home_screen")
  object SearchScreen : Screen("search_screen")


  fun withArgs (vararg args: String):String{
    return buildString {
      append(route)
      args.forEach {arg->
        append(("/$arg"))
      }
    }
  }

}
