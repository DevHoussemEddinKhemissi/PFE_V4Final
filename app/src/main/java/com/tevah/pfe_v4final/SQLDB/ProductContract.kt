package com.tevah.pfe_v4final.SQLDB

import android.provider.BaseColumns

object ProductContract {
    object ProductEntry : BaseColumns {
        const val TABLE_NAME = "Product"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_IMAGE = "image"
        const val COLUMN_PRIX = "prix"
    }
}

