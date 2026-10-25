package dev.myaring.mobile_butler

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.myaring.mobile_butler.ui.screen.EScreen
import dev.myaring.mobile_butler.ui.screen.GameScreen
import dev.myaring.mobile_butler.ui.screen.MainMenuScreen
import dev.myaring.mobile_butler.ui.screen.ResultScreen
import dev.myaring.mobile_butler.ui.theme.MobilebutlerTheme
import dev.myaring.mobile_butler.data.GameRecordStore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MobilebutlerTheme {
                val context = this@MainActivity

                var records by remember {
                    mutableStateOf(GameRecordStore.load(context))
                }

                var finalScore by remember {
                    mutableIntStateOf(0)
                }

                var currentScore by remember {
                    mutableIntStateOf(0)
                }

                var currentScreen by remember {
                    mutableStateOf(EScreen.MAIN_MENU)
                }

                when (currentScreen)
                {
                    EScreen.MAIN_MENU -> {
                        MainMenuScreen(
                            onStartGame = {
                                currentScore = 0
                                currentScreen = EScreen.GAME
                            },
                            onExitGame = {
                                finish()
                            },
                            highScore = records.highScore,
                            recentScores = records.recentScores
                        )
                    }
                    EScreen.GAME -> {
                        GameScreen(
                            currentScore = currentScore,
                            highScore = records.highScore,
                            onScoreUp = { currentScore += (1..10).random() },
                            onScoreDown = { currentScore -= (1..10).random() },
                            onConfirmScore = {
                                finalScore = currentScore

                                records = GameRecordStore.addScore(
                                    context,
                                    finalScore
                                )

                                currentScreen = EScreen.RESULT
                            },
                            onBack = {
                                currentScore = 0
                                currentScreen = EScreen.MAIN_MENU
                            }
                        )
                    }
                    EScreen.RESULT -> {
                        ResultScreen(
                            finalScore = finalScore,
                            highScore = records.highScore,
                            onReturnToMenu = { currentScreen = EScreen.MAIN_MENU },
                            onRestartGame = {
                                currentScore = 0
                                currentScreen = EScreen.GAME
                            }
                        )
                    }

                    EScreen.RESULT -> TODO()
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
    MobilebutlerTheme {
        Greeting("Android")
    }
}