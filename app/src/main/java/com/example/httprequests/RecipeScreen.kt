package com.example.httprequests

import android.text.Layout
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter


@Composable
fun RecipeScreen() {
    val recipeViewModel: MainViewModel = viewModel()
    val viewState by recipeViewModel.categoriesState

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            viewState.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(alignment = Alignment.Center))
            }
            viewState.error != null -> {
                ErrorScreen(modifier = Modifier.fillMaxSize())
            }
            else -> {
                CategoryScreen(categories = viewState.list)
            }
        }
    }
}

@Composable

fun CategoryScreen(categories: List<Category>) {

    LazyVerticalGrid(GridCells.Fixed(2), modifier = androidx.compose.ui.Modifier.fillMaxSize()) {
        items (categories){
                     category->
                      CategoryItem(category = category)
        }
    }
}

// how each items looks like
@Composable

fun CategoryItem(category: Category) {
    val showDialog = remember { mutableStateOf(false) }
  Column(modifier = Modifier
      .padding(8.dp)
      .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {

      Image(painter = rememberAsyncImagePainter(category.strCategoryThumb), contentDescription = null,
          modifier = Modifier
              .fillMaxSize()
              .aspectRatio(1f)
              .clickable {
                  showDialog.value = true
              }

      )


     Text(text = category.strCategory,
          color = Color.Black,
           style = TextStyle(fontWeight = FontWeight.Bold),
         modifier = Modifier.padding(top = 4.dp)
         )

      if (showDialog.value) {
          AlertDialog(
              onDismissRequest = { showDialog.value = false },
              confirmButton = {
                  Button(onClick = { showDialog.value = false }) {
                      Text("OK")
                  }
              },
              text = {
                  LazyColumn(modifier = Modifier.padding(10.dp)) {
                      item {
                          Text(text = category.strCategoryDescription)
                      }
                  }
              }
          )
      }
      }

  }
@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.img), contentDescription = ""
        )
        Text(text = "Loading Failed", modifier = Modifier.padding(16.dp))
    }
}