package com.example.signlanguageapp.data

import android.content.res.AssetManager
import android.graphics.Bitmap
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.channels.FileChannel


object ModelHelper {

    private lateinit var interpreter: Interpreter
    private lateinit var labels: List<String>

    fun loadModel(assetManager: AssetManager, modelFileName: String) {
        val fileDescriptor = assetManager.openFd(modelFileName)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        val model = fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)

        interpreter = Interpreter(model)
    }

    fun loadLabels(assetManager: AssetManager, labelsFileName: String) {
        labels = assetManager.open(labelsFileName).bufferedReader().readLines().map { it.trim() }
    }

    fun predict(bitmap: Bitmap): String {
        val input = preprocessImage(bitmap)
        val output = Array(1) { Array(8400) { FloatArray(44) } } // shape: [1, 8400, 44]
        interpreter.run(input, output)
        return getTopPrediction(output)
    }

    private fun preprocessImage(bitmap: Bitmap): Array<Array<Array<FloatArray>>> {
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, 640, 640, true)
        val input = Array(1) { Array(640) { Array(640) { FloatArray(3) } } }

        for (y in 0 until 640) {
            for (x in 0 until 640) {
                val pixel = resizedBitmap.getPixel(x, y)
                input[0][y][x][0] = ((pixel shr 16 and 0xFF) / 255.0f)
                input[0][y][x][1] = ((pixel shr 8 and 0xFF) / 255.0f)
                input[0][y][x][2] = ((pixel and 0xFF) / 255.0f)
            }
        }

        return input
    }

    private fun getTopPrediction(output: Array<Array<FloatArray>>): String {
        var bestScore = 0f
        var bestClass = -1

        for (i in 0 until 8400) {
            val classScores = output[0][i].sliceArray(5 until 44) // 5: skip bbox + obj
            for (clsIdx in classScores.indices) {
                if (classScores[clsIdx] > bestScore) {
                    bestScore = classScores[clsIdx]
                    bestClass = clsIdx
                }
            }
        }

        val label = if (bestClass in labels.indices) labels[bestClass] else "Unknown"
        return "Prediction: $label\nConfidence: ${"%.2f".format(bestScore * 100)}%"
    }
}
