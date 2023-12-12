package com.example.weatherapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.weatherapp.R
import com.example.weatherapp.navigation.Screen
import com.example.weatherapp.viewmodel.SearchListViewModel


@Composable
fun SearchScreen(
  navController: NavHostController,
  query: String?,
  searchListViewModel: SearchListViewModel
) {
  val suggestionList by searchListViewModel.suggestionList.collectAsState()

  LaunchedEffect(key1 = query) {
    if (query != null && query != "") {
      searchListViewModel.updateSearchQuery(query)
    }
  }


  Column(modifier = Modifier.fillMaxSize()) {
    query?.let {
      searchBar(
        navController,
        query = it,
        screen = "searchScreen",
        searchListViewModel = searchListViewModel
      )
    }
    Spacer(modifier = Modifier.size(20.dp))
    LazyColumn(
      modifier = Modifier
        .fillMaxHeight()
        .weight(1f)
        .padding(start = 10.dp, end = 10.dp)
        .wrapContentWidth(),
      verticalArrangement = Arrangement.spacedBy(4.dp),

      ) {

      itemsIndexed(suggestionList) { index, item ->
        if (item.name != null) {
          CityInfoContainer(
            country = item.name,
            temperature = item.temperature.toString(),
            icon = item.icon.toString()
          )
        } else {
          CityInfoContainer(
            country = item.country,
            temperature = item.temperature.toString(),
            icon = item.icon.toString()
          )
        }
      }
    }
  }
}


@Composable
fun CityInfoContainer(
  modifier: Modifier = Modifier,
  country: String?,
  temperature: String,
  icon: String
) {
  Column(
    modifier = modifier
      .padding(7.5.dp)
      .clip(RoundedCornerShape(10.dp))
      .background(colorResource(id = R.color.ContainerColor))
  ) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
      Column(
        modifier = Modifier
          .padding(start = 10.dp, top = 10.dp)
          .weight(1f),
        verticalArrangement = Arrangement.SpaceAround
      ) {
        Text(
          text = "$country",
          maxLines = 1,
          overflow = TextOverflow.Ellipsis,
          fontWeight = FontWeight.Bold,
          fontFamily = firSansFamily,
          fontStyle = FontStyle.Normal,
          fontSize = 30.sp,
          textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(
          buildAnnotatedString {

            append("$temperature")
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
      AsyncImage(
        model = stringResource(id = R.string.baseIconUrl) + icon + "@2x.png",
        contentDescription = "Icon Description",
        modifier = Modifier.size(100.dp)
      )

    }

  }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun searchBar(
  navController: NavController, screen: String,
  query: String = "",
  searchListViewModel: SearchListViewModel? = null,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  var query by remember {
    mutableStateOf(query)
  }

  var isActive by remember {
    mutableStateOf(false)
  }
  SearchBar(
    modifier = modifier
      .fillMaxWidth()
      .padding(top = 45.dp, start = 10.dp, end = 10.dp)
      .clip(RoundedCornerShape(10.dp)),

    query = query,
    onQueryChange = { newQuery ->
      query = newQuery
      if (screen == "searchScreen") {
        if (query.length > 2) {
          searchListViewModel?.updateSearchQuery(newQuery)
        } else if (query.length == 0) {
          searchListViewModel?.EmptySuggestionList()
        }
      }
    },
    onSearch = {
      if (screen != "searchScreen") {
        if (query.length > 2)
          navController.navigate(Screen.SearchScreen.withArgs(query + ""))
        else
          simpleToastMessage(context = context, query = query, error = true)
      }
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

