package com.simonsickle.barcode.tools.support

data class BarcodeConfig(
    @get:BarcodeType
    @setparam:BarcodeType
    var type: Int = BarcodeType.CODE_128,
    var width: Int = 0,
    var height: Int = 0,
    var padding: BarcodePadding = BarcodePadding(0, 0),
    var rawString: String = ""
)