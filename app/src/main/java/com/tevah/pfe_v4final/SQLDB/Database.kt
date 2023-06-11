package com.tevah.pfe_v4final.SQLDB

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Database(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE ${ProductContract.ProductEntry.TABLE_NAME} (" +
                "${ProductContract.ProductEntry.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${ProductContract.ProductEntry.COLUMN_NAME} TEXT," +
                "${ProductContract.ProductEntry.COLUMN_IMAGE} TEXT," +
                "${ProductContract.ProductEntry.COLUMN_PRIX} TEXT" +
                ")"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${ProductContract.ProductEntry.TABLE_NAME}")
        onCreate(db)
    }

    companion object {

        private const val DATABASE_NAME = "Wishlist.db"
        private const val DATABASE_VERSION = 1
    }
}
