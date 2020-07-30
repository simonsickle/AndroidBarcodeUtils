package com.simonsickle.barcode.tools.support;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({
        BarcodeType.EAN_8,
        BarcodeType.UPC_E,
        BarcodeType.EAN_13,
        BarcodeType.UPC_A,
        BarcodeType.QR_CODE,
        BarcodeType.CODE_39,
        BarcodeType.CODE_93,
        BarcodeType.CODE_128,
        BarcodeType.ITF,
        BarcodeType.PDF_417,
        BarcodeType.CODABAR,
        BarcodeType.DATA_MATRIX,
        BarcodeType.AZTEC
})
public @interface BarcodeType {
    public static final int EAN_8 = 0;
    public static final int UPC_E = 1;
    public static final int EAN_13 = 2;
    public static final int UPC_A = 3;
    public static final int QR_CODE = 4;
    public static final int CODE_39 = 5;
    public static final int CODE_93 = 6;
    public static final int CODE_128 = 7;
    public static final int ITF = 8;
    public static final int PDF_417 = 9;
    public static final int CODABAR = 10;
    public static final int DATA_MATRIX = 11;
    public static final int AZTEC = 12;
}