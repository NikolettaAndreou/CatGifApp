package com.example.catgifapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        setContent()
        {
            CatApp()
        }
    }
}
@Composable
fun CatApp()
{

    var cats by remember { mutableStateOf(listOf<CatResponse>()) }
    val scope = rememberCoroutineScope()
    var currentPage by remember { mutableStateOf(0) }
    var isLoading by remember { mutableStateOf(false) }
    var selectedType by remember { mutableStateOf("gif") }
    fun fetchCats() {
        if (isLoading) return

        scope.launch {
            try {
                isLoading = true

                val result = RetrofitInstance.api.getCats(
                    limit = 10,
                    page = currentPage,
                    mimeTypes = selectedType
                )

                cats = cats + result
                currentPage++

            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }


    LaunchedEffect(Unit) {
        fetchCats()
    }
    Column {


        Row {
            Button(onClick = {
                selectedType = "gif"
                currentPage = 0
                cats = emptyList()
                fetchCats()
            }) {
                Text("GIFs")
            }

            Button(onClick = {
                selectedType = "jpg"
                currentPage = 0
                cats = emptyList()
                fetchCats()
            }) {
                Text("Images")
            }

            Button(onClick = {
                selectedType = "gif,jpg,png"
                currentPage = 0
                cats = emptyList()
                fetchCats()
            }) {
                Text("Both")
            }
        }

        LazyColumn (
            modifier = Modifier.weight(1f)
        ){
            itemsIndexed(cats) { index, cat ->

                AsyncImage(
                    model = cat.url,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                )

                if (index >= cats.size - 2 && !isLoading) {
                    LaunchedEffect(index) {
                        fetchCats()
                    }
                }
            }
        }
    }

}
