package com.example.ict08


import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, DBNAME, null, 1) {

    override fun onCreate(MyDB: SQLiteDatabase?) {
        MyDB!!.execSQL("create Table users(id TEXT primary key, password TEXT, email TEXT, userType TEXT)")
    }

    override fun onUpgrade(MyDB: SQLiteDatabase?, i: Int, i1: Int) {
        MyDB!!.execSQL("drop Table if exists users")
        onCreate(MyDB)
    }

    fun insertData(id: String?, password: String?, email: String?, userType: String?): Boolean {
        val MyDB = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("id", id)
        contentValues.put("password", password)
        contentValues.put("email", email)
        contentValues.put("userType", userType)
        val result = MyDB.insert("users", null, contentValues)
        MyDB.close()
        return result != -1L
    }

    fun checkUser(id: String?): Boolean {
        val MyDB = this.readableDatabase
        val cursor = MyDB.rawQuery("Select * from users where id =?", arrayOf(id))
        val res = cursor.count > 0
        cursor.close()
        MyDB.close()
        return res
    }

    fun checkEmail(email: String?): Boolean {
        val MyDB = this.readableDatabase
        val cursor = MyDB.rawQuery("Select * from users where email =?", arrayOf(email))
        val res = cursor.count > 0
        cursor.close()
        MyDB.close()
        return res
    }

    fun checkUserpass(email: String, password: String): Boolean {
        val MyDB = this.writableDatabase
        val cursor = MyDB.rawQuery(
            "Select * from users where email = ? and password = ?",
            arrayOf(email, password)
        )
        val res = cursor.count > 0
        cursor.close()
        MyDB.close()
        return res
    }

    companion object {
        const val DBNAME = "Login.db"
    }
}
