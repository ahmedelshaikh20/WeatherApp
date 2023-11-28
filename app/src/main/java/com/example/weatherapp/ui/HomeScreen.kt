package com.example.weatherapp.ui


import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.weatherapp.R
import com.example.weatherapp.model.WeatherDataItem
import com.example.weatherapp.navigation.Screen
import com.example.weatherapp.viewmodel.SearchViewmodel
import com.example.weatherapp.viewmodel.searchListviewmodel
import java.text.SimpleDateFormat
import java.util.Date

val firSansFamily = FontFamily(
  Font(R.font.poppins_bold, FontWeight.Light),
  Font(R.font.poppins_bold, FontWeight.Bold)
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
  navController: NavController,
  searchViewmodel: SearchViewmodel,
  searchListViewmodel: searchListviewmodel
) {
  val weatherResponse by searchViewmodel.weatherResponse.collectAsState()
  Log.d("Ahmmmmed", stringResource(id = R.string.baseIconUrl) + weatherResponse.icon + "@2x.png")
  Column(
    horizontalAlignment = CenterHorizontally
  ) {
    searhBar(navController , searchListViewModel = searchListViewmodel)
    AsyncImage(
      model = stringResource(id = R.string.baseIconUrl) + weatherResponse.icon + "@2x.png",
      contentDescription = "WeatherIcon",
      modifier = Modifier
        .size(200.dp)
        .align(CenterHorizontally)
        .padding(top = 60.dp)
    )
    weatherLocationSection(
      modifier = Modifier
        .align(CenterHorizontally)
        .padding(top = 15.dp),
      weatherResponse
    )

    weatherInformationSection(
      Modifier
        .align(CenterHorizontally)
        .padding(start = 15.dp, end = 15.dp)
        .clip(RoundedCornerShape(15.dp)),
      weatherResponse
    )
  }

}

@Composable
fun weatherLocationSection(modifier: Modifier = Modifier, weatherResponse: WeatherDataItem) {
  Column(
    modifier = modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = CenterHorizontally
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = "${weatherResponse.name}",
        fontWeight = FontWeight.Bold,
        fontFamily = firSansFamily,
        fontStyle = FontStyle.Normal,
        fontSize = 30.sp,
        textAlign = TextAlign.Center
      )
      Spacer(modifier = Modifier.width(15.dp))
      Icon(
        painter = painterResource(id = R.drawable.img),
        contentDescription = "Location Image",
        Modifier
          .size(20.dp)
          .align(Alignment.CenterVertically)
      )
    }
    Text(
      buildAnnotatedString {

        append("${weatherResponse.temperature}")
        withStyle(
          style = SpanStyle(
            baselineShift = BaselineShift.Superscript,
            fontSize = 20.0.sp,
            fontStyle = FontStyle.Italic,
          )
        ) {
          append(text = "\u00B0")
        }
      },
      fontWeight = FontWeight.Bold,
      fontFamily = firSansFamily,
      fontStyle = FontStyle.Normal,
      fontSize = 50.sp,
      textAlign = TextAlign.Center
    )
  }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun searhBar(
  navController: NavController, screen: String? = null,
  searchListViewModel: searchListviewmodel? = null
) {
  val context = LocalContext.current
  var query by remember {
    mutableStateOf("")
  }
  var isActive by remember {
    mutableStateOf(false)
  }
  SearchBar(
    modifier = Modifier
      .fillMaxWidth()
      .padding(top = 45.dp, start = 10.dp, end = 10.dp)
      .clip(RoundedCornerShape(10.dp)),

    query = query,
    onQueryChange = { newQuery ->
      query = newQuery
      if (query.length > 2) {
        searchListViewModel?.searchTextChanged(newQuery)
      } else if (query.length==0){
        searchListViewModel?.EmptySuggestionList()
      }
    },
    onSearch = {
      if (screen != "searchScreen") {
        navController.navigate(Screen.SearchScreen.route)
        simpleToastMessage(context = context, query = query)}
    },
    active = false,
    onActiveChange = { activeChange ->
        isActive = activeChange

    },
    shape = SearchBarDefaults.inputFieldShape,
    colors = SearchBarDefaults.colors(containerColor = colorResource(id = R.color.ContainerColor)),
    trailingIcon = {
      Icon(
        imageVector = Icons.Default.Search,
        contentDescription = "SearchIcon",
        tint = Color.Gray,
        modifier = Modifier.size(30.dp)
      )
    },
    placeholder = {
      Text(
        text = "Search Location",
        modifier = Modifier
          .fillMaxWidth()
          .size(30.dp),
        color = colorResource(id = R.color.SearchHintColor)
      )
    },
    tonalElevation = 0.dp


  ) {

  }
}

fun simpleToastMessage(context: Context, query: String) {
  Toast.makeText(context, "Your query string: $query", Toast.LENGTH_SHORT).show()
}

@Composable
fun weatherInformationSection(modifier: Modifier = Modifier, weatherResponse: WeatherDataItem) {
  Box(
    modifier = modifier
      .background(colorResource(id = R.color.ContainerColor))
      .fillMaxWidth()
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceAround,
    ) {
      dataSection(label = "Time", info = getDate(weatherResponse.time))
      dataSection(label = "Description", info = weatherResponse.description)
      dataSection(label = "Humidity", info = weatherResponse.humidity.toString() + " %")
      dataSection(label = "Pressure", info = weatherResponse.pressure.toString() + " hPa")
    }


  }

}

fun getDate(dt: Long): String {
  val date = Date((dt) * 1000)
  val sdf = SimpleDateFormat("yyyy-MM-dd,HH:mm")
  val formattedDate = sdf.format(date)
  val time = formattedDate.split(',').get(1)
  return time;

}

@Composable
fun dataSection(label: String, info: String) {
  Column(
    modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
    verticalArrangement = Arrangement.SpaceAround
  ) {
    Text(
      text = "$label", modifier = Modifier
        .padding(start = 10.dp)
        .align(CenterHorizontally),
      fontFamily = firSansFamily,
      fontSize = 12.sp,
      color = colorResource(id = R.color.WeatherInfoColor)

    )
    Text(
      text = "$info", modifier = Modifier
        .padding(start = 10.dp)
        .align(CenterHorizontally),
      fontFamily = firSansFamily,
      fontSize = 15.sp,
      color = colorResource(id = R.color.DataColor)

    )
  }
}
