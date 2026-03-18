package solutions.sgbrightkit.cinemaflow

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import solutions.sgbrightkit.cinemaflow.ui.theme.CinemaFlowTheme

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CinemaFlowTheme {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.channel_splash_screen),
                        contentDescription = "CinemaFlow Splash",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }

                LaunchedEffect(Unit) {
                    delay(2000)  // 2 seconds
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    finish()
                }
            }
        }
    }
}