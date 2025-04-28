package com.example.lab3_homework

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab3_homework.Image
import com.example.lab3_homework.PixabayApi
import com.example.lab3_homework.RetrofitInstance
import com.example.lab3_homework.DetectedObject
import com.example.lab3_homework.ObjectDetector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class ImageWithObjects(
    val image: Image,
    val detectedObjects: List<DetectedObject> // Use our custom DetectedObject
)

class ImageViewModel(private val context: Context) : ViewModel() {
    private val apiKey = "49976003-81400ee274b749624a816717c"
    private val query = "nature"
    private val perPage = 20

    private val objectDetector = ObjectDetector(context)

    private val _imagesWithObjects = MutableStateFlow<List<ImageWithObjects>>(emptyList())
    val imagesWithObjects: StateFlow<List<ImageWithObjects>> = _imagesWithObjects

    private val _currentPage = MutableStateFlow(1)
    val currentPage: StateFlow<Int> = _currentPage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        fetchImages(1)
    }

    fun fetchImages(page: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitInstance.api.searchImages(apiKey, query, page, perPage)
                val images = response.hits
                // Perform object detection for each image
                val imagesWithObjects = images.map { image ->
                    val detectedObjects = objectDetector.detectObjects(image.webformatUrl)
                    ImageWithObjects(image, detectedObjects)
                }
                _imagesWithObjects.value = imagesWithObjects
                _currentPage.value = page
            } catch (e: Exception) {
                _imagesWithObjects.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun nextPage() {
        fetchImages(_currentPage.value + 1)
    }

    fun previousPage() {
        if (_currentPage.value > 1) {
            fetchImages(_currentPage.value - 1)
        }
    }
}