package com.app.sound_wise.ui.features.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.app.sound_wise.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(navController: NavHostController) {
    val splashViewModel: SplashViewModel = koinViewModel()

    val isSplashShow by splashViewModel.isSplashShow.collectAsState()

    LaunchedEffect(isSplashShow) {
        if (!isSplashShow) {
            navController.navigate("question/0") {
                popUpTo("splash") { inclusive = true }
            }
        }
    }

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.sound_wise))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF39608F)),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(240.dp)
                .align(Alignment.Center)
        ) {
            LottieAnimation(
                composition,
                modifier = Modifier.matchParentSize(),
                restartOnPlay = true,
                iterations = 100,
            )

            Text(
                text = "Sound Wise",
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 8.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}