package com.programming.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.programming.movieapp.ui.navigation.BottomBarNavigation
import com.programming.movieapp.ui.navigation.BottomBarRoutes
import com.programming.movieapp.ui.navigation.rememberAppState
import com.programming.movieapp.ui.theme.MovieAppTheme
import com.programming.movieapp.ui.viewmodels.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val movieViewModel: MovieViewModel by viewModels()
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppTheme {
                val appState = rememberAppState()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val tabList = listOf(
                        BottomBarRoutes.HOME,
                        BottomBarRoutes.FAVORITE
                    )

                    Scaffold(
                        bottomBar = {
                            if (appState.shouldShowBottomBar)
                                NavigationBar(modifier =  Modifier.background(Color.Transparent)) {
                                    val navStackBackEntry by appState.navHostController.currentBackStackEntryAsState()
                                    val currentDestination = navStackBackEntry?.destination

                                    tabList.forEach { tab ->
                                        NavigationBarItem(
                                            selected = currentDestination?.hierarchy?.any { it.route == tab.routes } == true,
                                            onClick = { appState.navHostController.navigate(tab.routes) },
                                            icon ={ Icon(tab.icon, contentDescription = null) },
                                        )
                                    }
                                }
                        }
                    ) { innerPadding ->
                        BottomBarNavigation(
                            navHostController = appState.navHostController,
                            padding = innerPadding,
                            movieViewModel = movieViewModel,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MovieAppTheme {
        Greeting("Android")
    }
}