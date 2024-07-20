package com.example.socialmedia.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.socialmedia.R
import com.example.socialmedia.data.MockData
import com.example.socialmedia.model.Story

@Composable
fun ShowStoryScreen(id: Int) {
    val story = MockData.stories.find { x -> x.id == id }
    StoryView(story!!)
}

@Composable
fun StoryView(story: Story) {
    Card(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(story.profile)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.loading),
                contentDescription = story.userName,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StoryViewPreview() {
    StoryView(story = MockData.stories[0]) // Assuming MockData.stories is not empty
}