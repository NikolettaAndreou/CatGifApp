package com.example.catgifapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.itemsIndexed


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

    fun fetchCats() {
        if (isLoading) return

        scope.launch {
            try {
                isLoading = true

                val result = RetrofitInstance.api.getCats(
                    limit = 10,
                    page = currentPage,
                    mimeTypes = "gif"
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
    LazyColumn {
        itemsIndexed(cats) { index, cat ->

            AsyncImage(
                model = cat.url,
                contentDescription = null
            )

            if (index >= cats.size - 2 && !isLoading) {
                LaunchedEffect(Unit) {
                    fetchCats()
                }
            }
        }
    }
}
