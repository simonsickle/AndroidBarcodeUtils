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
            (config.width ),
            (config.height )
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
                            (x + 0),
                            (y + 0),
                            Color.BLACK
                        )
                    }
                }
            }
        }
    }

    fun paddingForType(
        displayMetrics: DisplayMetrics,
        @BarcodeType type: Int
    ): BarcodeConfig.Padding {
        val widthInDip = when (type) {
            BarcodeType.CODE_128 -> 2F
            else -> 1F
        }

        val heightInDip = when (type) {
            BarcodeType.CODE_128 -> 4F
            else -> 1F
        }

        return BarcodeConfig.Padding(
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
            BarcodeType.EAN_8 -> BarcodeFormat.EAN_8
            BarcodeType.UPC_E -> BarcodeFormat.UPC_E
            BarcodeType.EAN_13 -> BarcodeFormat.EAN_13
            BarcodeType.UPC_A -> BarcodeFormat.UPC_A
            BarcodeType.QR_CODE -> BarcodeFormat.QR_CODE
            BarcodeType.CODE_39 -> BarcodeFormat.CODE_39
            BarcodeType.CODE_93 -> BarcodeFormat.CODE_93
            BarcodeType.CODE_128 -> BarcodeFormat.CODE_128
            BarcodeType.ITF -> BarcodeFormat.ITF
            BarcodeType.PDF_417 -> BarcodeFormat.PDF_417
            BarcodeType.CODABAR -> BarcodeFormat.CODABAR
            BarcodeType.DATA_MATRIX -> BarcodeFormat.DATA_MATRIX
            BarcodeType.AZTEC -> BarcodeFormat.AZTEC
            else -> throw IllegalArgumentException("Barcode type is not implemented yet")
        }
    }
}