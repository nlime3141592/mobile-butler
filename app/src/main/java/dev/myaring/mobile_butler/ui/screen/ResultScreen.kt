package dev.myaring.mobile_butler.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ResultScreen(
    finalScore: Int,
    highScore: Int,
    onReturnToMenu: () -> Unit,
    onRestartGame: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "게임 결과",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "최종 점수",
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = finalScore.toString(),
            style = MaterialTheme.typography.displayLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "역대 최고 점수: $highScore",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = onRestartGame,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("게임 다시 시작")
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = onReturnToMenu,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("메인 메뉴로 돌아가기")
        }
    }
}
