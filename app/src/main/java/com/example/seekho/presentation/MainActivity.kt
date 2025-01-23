package com.example.seekho.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.seekho.ui.theme.SeekhoTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SeekhoTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){

                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = ScreenA,
                        enterTransition = { EnterTransition.None },
                        exitTransition = { ExitTransition.None }
                    ){
                        composable<ScreenA> {
                            Scaffold(modifier =Modifier.fillMaxSize() ) { innerPadding ->
                                Home(navController,
                                    modifier = Modifier.padding(innerPadding))

                            }

                        }
                        composable<ScreenB> {
                            val args = it.toRoute<ScreenB>()
                            AnimeDetail(args.id)

                        }

                    }

                }

            }
        }
    }
}


@Serializable
object ScreenA

@Serializable
data class ScreenB(
    val id : Int,
)




