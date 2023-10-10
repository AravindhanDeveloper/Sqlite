package com.angler.task

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView

class HomeActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private var userId: Long = 0
    private lateinit var logoutBtn: MaterialButton
    private lateinit var userNameTxt: MaterialTextView
    private lateinit var fullNameTxt: MaterialTextView
    private lateinit var mobileNumberTxt: MaterialTextView
    private lateinit var lastNameTxt: MaterialTextView

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        dbHelper = DatabaseHelper(this)

        logoutBtn = findViewById(R.id.logout_btn)
        fullNameTxt = findViewById(R.id.fullname_txt)
        lastNameTxt = findViewById(R.id.last_name_txt)
        mobileNumberTxt = findViewById(R.id.mobile_number_txt)
        userNameTxt = findViewById(R.id.username_txt)

        logoutBtn.setOnClickListener {
            PreferencesHelper.signOut(this@HomeActivity)
            val intent = Intent(this@HomeActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        userId = PreferencesHelper.getPreference(this@HomeActivity, PreferencesHelper.PREF_USERID).toLong()

        val db = dbHelper.readableDatabase
        val projection = arrayOf(
            DatabaseHelper.COLUMN_ID,
            DatabaseHelper.COLUMN_FIRSTNAME,
            DatabaseHelper.COLUMN_USERNAME,
            DatabaseHelper.COLUMN_LASTNAME,
            DatabaseHelper.COLUMN_MOBILENUMBER,
            DatabaseHelper.COLUMN_PASSWORD
        )
        val selection = "${DatabaseHelper.COLUMN_ID} = ?"
        val selectionArgs = arrayOf(userId.toString())
        val cursor = db.query(
            DatabaseHelper.TABLE_USERS,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            val username = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USERNAME))
            val firstname = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FIRSTNAME))
            val lastname = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_LASTNAME))
            val mobilenumber = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_MOBILENUMBER))

            fullNameTxt.text = firstname
            lastNameTxt.text = lastname
            mobileNumberTxt.text = mobilenumber
            userNameTxt.text = username
        }

        cursor.close()
        db.close()
    }
}
