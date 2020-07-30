package com.simonsickle.barcode.tools.support

import android.graphics.Bitmap
import android.graphics.Color
import android.util.DisplayMetrics
import android.util.TypedValue
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter

object BarcodeBitmapGenerator {
    fun generateBarcodeBitmap(config: BarcodeConfig): Bitmap {
        // Calculate the safe padding for the dead space on the barcode
        val bitMatrix = MultiFormatWriter().encode(
            config.rawString,
            getBarcodeFormat(barcodeType = config.type),
            (config.width - (2 * config.padding.width)),
            (config.height - (2 * config.padding.height))
        )

        // Go through the matrix to create a bitmap
        return Bitmap.createBitmap(config.width, config.height, Bitmap.Config.ARGB_8888).apply {
            for (x in 0 until bitMatrix.width) {
                for (y in 0 until bitMatrix.height) {
                    // If we get true at this point of the matrix, we want to set a black pixel at
                    // this point, plus the padding offsets. No need to set white color as we will
                    // paint the canvas itself to white.
                    if (bitMatrix[x, y]) {
                        setPixel(
                            (x + config.padding.width),
                            (y + config.padding.height),
                            Color.BLACK
                        )
                    }
                }
            }
        }
    }

    fun paddingForType(displayMetrics: DisplayMetrics, @BarcodeType type: Int): BarcodePadding {
        val widthInDip = when (type) {
            BarcodeType.CODE_128 -> 2F
            else -> 1F
        }

        val heightInDip = when (type) {
            BarcodeType.CODE_128 -> 4F
            else -> 1F
        }

        return BarcodePadding(
            width = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                widthInDip,
                displayMetrics
            ).toInt(),
            height = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                heightInDip,
                displayMetrics
            ).toInt()
        )
    }

    private fun getBarcodeFormat(@BarcodeType barcodeType: Int): BarcodeFormat {
        return when (barcodeType) {
            BarcodeType.CODE_128 -> BarcodeFormat.CODE_128
            BarcodeType.QR -> BarcodeFormat.QR_CODE
            else -> throw IllegalArgumentException("Barcode type is not implemented yet")
        }
    }
}