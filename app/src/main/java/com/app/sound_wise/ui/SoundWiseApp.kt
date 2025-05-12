package com.app.sound_wise.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.sound_wise.ui.features.home.HomeScreen
import com.app.sound_wise.ui.features.loading.LoadingScreen
import com.app.sound_wise.ui.features.result.ResultScreen
import com.app.sound_wise.ui.features.splash.SplashScreen
import com.app.sound_wise.ui.navigation.Screen
import com.app.sound_wise.ui.features.question.QuestionsScreen
import com.app.sound_wise.ui.features.question.QuestionsViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun SoundWiseApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val viewModel = koinViewModel<QuestionsViewModel>()

    Scaffold(
        topBar = {},
        bottomBar = {},
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Splash.route) { SplashScreen(navController) }
            composable(Screen.Loading.route) { LoadingScreen(navController) }
            composable(Screen.Home.route) { HomeScreen() }
            composable("${Screen.Question.route}/{index}") { backStack ->
                val index = backStack.arguments?.getString("index")?.toIntOrNull() ?: 0

                QuestionsScreen(
                    viewModel = viewModel,
                    index = index,
                    onNext = {
                        if (index + 1 < viewModel.questions.size) {
                            navController.navigate(Screen.Question.createRoute(index + 1))
                        } else {
                            navController.navigate(Screen.Loading.route)
                        }
                    },
                    onBack = if (index > 0) {
                        { navController.navigate(Screen.Question.createRoute(index - 1)) }
                    } else null)
            }
            composable(Screen.Result.route) {
                ResultScreen(
                    viewModel = viewModel,
                    facts = viewModel.answers.map { it.questionId to it.selectedAnswer },
                    onClick = {
                        viewModel.reset()
                        navController.navigate(Screen.Question.createRoute(0)) {
                            popUpTo(Screen.Result.route) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}