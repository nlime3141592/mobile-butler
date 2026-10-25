package dev.myaring.mobile_butler.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainMenuScreen(
    onStartGame: () -> Unit,
    onExitGame: () -> Unit,
    highScore: Int,
    recentScores: List<Int>
) {
    var showRecordDialog by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Score Game",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "최고 점수: $highScore",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = onStartGame,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("게임 시작")
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = {
                showRecordDialog = true
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("게임 기록")
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = onExitGame,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("게임 종료")
        }
    }

    if (showRecordDialog) {
        AlertDialog(
            onDismissRequest = {
                showRecordDialog = false
            },
            title = {
                Text("게임 기록")
            },
            text = {
                Column {
                    Text(
                        text = "역대 최고 점수: $highScore",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "최근 5게임",
                        style = MaterialTheme.typography.titleSmall
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    if (recentScores.isEmpty()) {
                        Text("아직 게임 기록이 없습니다.")
                    } else {
                        recentScores
                            .take(5)
                            .forEachIndexed { index, score ->
                                Text(
                                    text = "${index +1}. $score 점",
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(vertical = 4.dp)
                                )
                            }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showRecordDialog = false
                    }
                ) {
                    Text("닫기")
                }
            }
        )
    }
}