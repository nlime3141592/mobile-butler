package dev.myaring.mobile_butler.data

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

data class GameRecords(
    val highScore: Int = 0,
    val recentScores: List<Int> = emptyList()
)

object GameRecordStore {
    private const val FILE_NAME = "game_records.json"

    private fun getFile(context: Context): File {
        return File(context.filesDir, FILE_NAME)
    }

    fun load(context: Context): GameRecords {
        val file = getFile(context)

        if (!file.exists()) {
            return GameRecords()
        }

        return try {
            val json = JSONObject(file.readText())

            val highScore = json.optInt("highScore", 0)
            val array = json.optJSONArray("recentScores")
                ?: JSONArray()

            val recentScores = (0 until array.length())
                .map { array.getInt(it) }
                .take(5)

            GameRecords(
                highScore = highScore,
                recentScores = recentScores
            )
        } catch (e: Exception) {
            // 파일이 없거나 손상된 경우 기본 기록으로 시작한다.
            GameRecords()
        }
    }

    fun save(context: Context, records: GameRecords) {
        val json = JSONObject().apply {
            put("highScore", records.highScore)
            put("recentScores", JSONArray(records.recentScores.take(5)))
        }

        getFile(context).writeText(json.toString(2))
    }

    fun addScore(context: Context, score: Int): GameRecords {
        val previous = load(context)

        val updated = GameRecords(
            highScore = maxOf(previous.highScore, score),
            recentScores = (listOf(score) + previous.recentScores).take(5)
        )

        save(context, updated)
        return updated
    }
}
