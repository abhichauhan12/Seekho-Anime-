package com.example.seekho.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.seekho.presentation.viewModel.DataViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun AnimeDetail(animeID:Int) {

    val viewModel: DataViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.getAnimeDetailData(animeID)
    }

    val animeDetailData by viewModel.animeDetailData.collectAsState()

    if (animeDetailData == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
//        return
        return

    }

    val scrollState = rememberScrollState()

    Column (modifier = Modifier
        .fillMaxSize()
        .padding(top= WindowInsets.statusBars.asPaddingValues().calculateTopPadding())
        .padding(horizontal = 16.dp)
        .verticalScroll(scrollState)
    ){

        if (animeDetailData?.trailer?.youtube_id.isNullOrBlank()){

            GlideImage(model = animeDetailData!!.images.jpg.large_image_url, contentDescription = "Anime Poster", modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp).height(220.dp)
                ,
                contentScale = ContentScale.Crop

            )

        }else{
            AndroidView(
                factory = { context ->
                    YouTubePlayerView(context).apply {

                        addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                            override fun onReady(youTubePlayer: YouTubePlayer) {
                                youTubePlayer.loadVideo(
                                    animeDetailData?.trailer?.youtube_id.toString(),
                                    0f
                                )
                            }
                        })

                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            )
        }



        Text(text =  animeDetailData?.title ?: "Unknown Title",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
            ,
            text = "Number of Episodes: "+" " +animeDetailData?.episodes.toString(),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )

//        Text(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 8.dp)
//            ,
//            text = "Main Cast "+" " +animeDetailData?.toString(),
//            fontWeight = FontWeight.Bold,
//            fontSize = 16.sp
//        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
            ,
            text = "Age rating :"+animeDetailData?.rating.toString(),
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )

        Row (   modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            ,

            verticalAlignment = Alignment.CenterVertically){
            Text(
                text = "Rating: \t",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Icon(Icons.Rounded.Star, tint = Color.Yellow, contentDescription = "Star")
            Text(
                text = animeDetailData?.score.toString(),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
        }

        Row (   modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            ,
            verticalAlignment = Alignment.CenterVertically
            ){

            Text(
                "Genre(s):",
                modifier = Modifier
                ,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = animeDetailData?.genres?.joinToString(", ") { it.name } ?: "No genres available",
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                ,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )

        }


        Text(
            "Plot/Synopsis:",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
            ,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            animeDetailData?.synopsis.toString(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal
        )


    }


}