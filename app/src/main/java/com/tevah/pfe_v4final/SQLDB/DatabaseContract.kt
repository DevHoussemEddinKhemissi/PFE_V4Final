package com.tevah.pfe_v4final.SQLDB



import android.provider.BaseColumns

object DatabaseContract {
    object WishlistEntry : BaseColumns {
        const val TABLE_NAME = "Wishlist"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_IMAGE = "image"
        const val COLUMN_STOCK = "stock"
        const val COLUMN_VALID = "valid"
        const val COLUMN_PRIX = "prix"
    }
}
