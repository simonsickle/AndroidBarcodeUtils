package com.simonsickle.barcode.tools.support;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({
        BarcodeType.CODE_128,
        BarcodeType.QR
})
public @interface BarcodeType {
    public static final int CODE_128 = 0;
    public static final int QR = 1;
}