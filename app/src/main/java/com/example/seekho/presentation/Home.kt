package com.example.seekho.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.seekho.data.network.apiResponse.Data
import com.example.seekho.presentation.viewModel.DataViewModel

@Composable
fun Home(navController : NavHostController, modifier: Modifier = Modifier){
    val viewModel: DataViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.getData()
    }

    val animeData by viewModel.animeData.collectAsState()

    Column(modifier=modifier.fillMaxSize()) {
        Text("Top Anime" ,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp)
        )

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(200.dp),
//        verticalItemSpacing = 4.dp,
//        horizontalArrangement = Arrangement.spacedBy(4.dp),
            content = {

                items(animeData) { data ->
                    AnimeItemList(data){
                        navController.navigate(ScreenB(data.mal_id))
                    }
                }
            }
        )
    }


}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun AnimeItemList(item: Data , onClick: () -> Unit) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    val imageWidth = screenWidth * 0.45f
    val imageHeight = screenHeight * 0.30f



    ElevatedCard(modifier = Modifier
        .padding(horizontal = 8.dp, vertical = 8.dp)
        .border(1.dp, color = Color.Black, shape = RoundedCornerShape(8.dp))
        .clickable {
            onClick()
        }
    ) {
        Column (modifier = Modifier.padding(8.dp)) {


            GlideImage(model = item.images.jpg.image_url, contentDescription = "Anime Poster", modifier = Modifier
                .border(0.5.dp, color = Color.Black , shape = RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .size(width = imageWidth , height = imageHeight) ,
                contentScale = ContentScale.Crop

                )

                Text(
                    text = item.title,
                    maxLines = 2,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Total Episodes: ${item.episodes}",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp

                )

                Text(
                    text = item.rating,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp
                )

                Row {
                    Icon(Icons.Rounded.Star, tint = Color.Yellow, contentDescription = item.title)
                    Text(
                        text = item.score.toString(),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                }

        }

    }
}
