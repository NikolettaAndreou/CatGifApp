package com.example.catgifapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import coil.compose.AsyncImage

class MainActivity : ComponentActivity() {
    private var currentPage = 0
    private var selectedType = "gif" // default
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            var cats by remember { mutableStateOf(listOf<CatResponse>()) }

            LaunchedEffect(Unit) {
                try {
                    cats = RetrofitInstance.api.getCats()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
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
    }
}