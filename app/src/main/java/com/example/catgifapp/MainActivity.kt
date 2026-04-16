package com.example.catgifapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import coil.compose.AsyncImage
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        setContent
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

    fun fetchCats() {
        scope.launch {
            try {
                cats = RetrofitInstance.api.getCats(
                    limit = 10,
                    page = 0,
                    mimeTypes = "gif"
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    LaunchedEffect(Unit) {
        fetchCats()
    }
    LazyColumn {
        items(cats) { cat ->
            AsyncImage(
                model = cat.url,
                contentDescription = null
            )
        }
    }
}
