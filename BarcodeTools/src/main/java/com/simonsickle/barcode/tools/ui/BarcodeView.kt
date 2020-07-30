package com.simonsickle.barcode.tools.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import com.simonsickle.barcode.tools.R
import com.simonsickle.barcode.tools.support.BarcodeBitmapGenerator
import com.simonsickle.barcode.tools.support.BarcodeConfig
import com.simonsickle.barcode.tools.support.BarcodeType

class BarcodeImageView : View {
    constructor(context: Context) : super(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val a = context.obtainStyledAttributes(
            attrs,
            R.styleable.BarcodeImageView
        )

        if (a.hasValue(R.styleable.BarcodeImageView_barcodeValue)) {
            barcodeConfig.rawString =
                a.getText(R.styleable.BarcodeImageView_barcodeValue)?.toString() ?: ""
        }
        if (a.hasValue(R.styleable.BarcodeImageView_barcodeType)) {
            barcodeConfig.type =
                a.getInt(
                    R.styleable.BarcodeImageView_barcodeType,
                    BarcodeType.CODE_128
                )
        }

        a.recycle()
    }

    private var safeWidth = 0f
        set(value) {
            barcodeConfig.width = value.toInt()
            field = value
        }

    private var safeHeight = 0f
        set(value) {
            barcodeConfig.height = value.toInt()
            field = value
        }

    private val barcodeConfig = BarcodeConfig()

    @get:BarcodeType
    var barcodeType: Int
        get() = barcodeConfig.type
        set(@BarcodeType value) {
            if (barcodeConfig.type != value) {
                barcodeConfig.type = value
                barcodeConfig.padding =
                    BarcodeBitmapGenerator.paddingForType(resources.displayMetrics, value)
            }
        }

    var barcode: String
        get() = barcodeConfig.rawString
        set(value) {
            if (barcodeConfig.rawString != value) {
                barcodeConfig.rawString = value
            }
        }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        // Calculate our safe to draw width and height
        safeWidth = w.toFloat() - (paddingLeft + paddingRight).toFloat()
        safeHeight = h.toFloat() - (paddingTop + paddingBottom).toFloat()
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Can't generate on a bad barcode value
        if (barcodeConfig.rawString.isEmpty()) return

        canvas.apply {
            // Set the image background
            drawColor(Color.WHITE)

            // Get our barcode and then draw it onto the canvas
            drawBitmap(BarcodeBitmapGenerator.generateBarcodeBitmap(barcodeConfig), 0f, 0f, null)
        }
    }
}
