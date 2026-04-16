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
import androidx.compose.material3.Button
import androidx.compose.material3.Text


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

        // 🔘 ADD YOUR BUTTONS HERE
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
                selectedType = "jpg,png"
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

        LazyColumn {
            itemsIndexed(cats) { index, cat ->

                AsyncImage(
                    model = cat.url,
                    contentDescription = null
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
