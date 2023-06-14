package com.tevah.pfe_v4final.SQLDB
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.tevah.pfe_v4final.SQLDB.DatabaseContract.WishlistEntry

class Database(context: Context) : SQLiteOpenHelper(context, "Tevah.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = "CREATE TABLE ${WishlistEntry.TABLE_NAME} (" +
                "${WishlistEntry.COLUMN_ID} INTEGER PRIMARY KEY," +
                "${WishlistEntry.COLUMN_NAME} TEXT," +
                "${WishlistEntry.COLUMN_IMAGE} TEXT," +
                "${WishlistEntry.COLUMN_STOCK} INTEGER," +
                "${WishlistEntry.COLUMN_VALID} INTEGER," +
                "${WishlistEntry.COLUMN_PRIX} TEXT" +
                ")"
        db.execSQL(createTableQuery)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Handle database upgrade if needed
    }
    fun deleteProduct(name: String): Int {
        val db = writableDatabase
        return db.delete(
            WishlistEntry.TABLE_NAME,
            "${WishlistEntry.COLUMN_NAME} = ?",
            arrayOf(name)
        )
    }
}
