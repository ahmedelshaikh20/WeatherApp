package com.example.weatherapp.ui


import android.content.Context
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.weatherapp.viewmodel.SearchViewModel
import java.text.SimpleDateFormat
import java.util.Date

val firSansFamily = FontFamily(
  Font(R.font.poppins_bold, FontWeight.Light),
  Font(R.font.poppins_bold, FontWeight.Bold)
)


@Composable
fun HomeScreen(
  navController: NavController,
  searchViewmodel: SearchViewModel,
) {
  val weatherResponse by searchViewmodel.weatherResponse.collectAsState()
  Column(
    horizontalAlignment = CenterHorizontally
  ) {
    SearchBar(navController, screen = "Home Screen")
    WeatherImage(
      weatherResponse?.icon, modifier = Modifier
        .size(200.dp)
        .align(CenterHorizontally)
        .padding(top = 60.dp)
    )

    weatherLocationSection(
      weatherResponse,
      modifier = Modifier
        .align(CenterHorizontally)
        .padding(top = 15.dp)
    )

    WeatherInformationSection(
      weatherResponse,
      Modifier
        .align(CenterHorizontally)
        .padding(start = 15.dp, end = 15.dp)
        .clip(RoundedCornerShape(15.dp))
    )
  }

}

@Composable
fun WeatherImage(icon: String?, modifier: Modifier = Modifier) {
  AsyncImage(
    model = stringResource(id = R.string.baseIconUrl) + icon + "@2x.png",
    contentDescription = "WeatherIcon",
    modifier = modifier

  )
}

@Composable
fun weatherLocationSection(weatherResponse: WeatherDataItem?, modifier: Modifier = Modifier) {
  Column(
    modifier = modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = CenterHorizontally
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = "${weatherResponse?.name}",
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

        append("${weatherResponse?.temperature}")
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


fun simpleToastMessage(context: Context, query: String, error: Boolean = false) {
  if (!error)
    Toast.makeText(context, "Your query string: $query", Toast.LENGTH_SHORT).show()
  else
    Toast.makeText(context, "Your Query String is Too Short", Toast.LENGTH_SHORT).show()

}

@Composable
fun WeatherInformationSection(weatherResponse: WeatherDataItem?, modifier: Modifier = Modifier) {
  Box(
    modifier = modifier
      .background(colorResource(id = R.color.ContainerColor))
      .fillMaxWidth()
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceAround,
    ) {
      DataSection(label = "Time", info = getDate(weatherResponse?.time))
      DataSection(label = "Description", info = weatherResponse?.description)
      DataSection(label = "Humidity", info = weatherResponse?.humidity.toString() + " %")
      DataSection(label = "Pressure", info = weatherResponse?.pressure.toString() + " hPa")
    }
  }

}

fun getDate(dt: Long?): String {
  if (dt != null) {
    val date = Date((dt) * 1000)
    val sdf = SimpleDateFormat("yyyy-MM-dd,HH:mm")
    val formattedDate = sdf.format(date)
    val time = formattedDate.split(',').get(1)
    return time;
  }
  return ""
}

@Composable
fun DataSection(label: String, info: String?, modifier: Modifier = Modifier) {
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
