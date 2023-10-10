package com.angler.task

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    var loginTxt: MaterialTextView? = null
    var registerBtn: MaterialButton? = null
    var userNameTxt: TextInputEditText? = null
    var passwordTxt: TextInputEditText? = null
    var firstNameTxt: TextInputEditText? = null
    var mobileNumberTxt: TextInputEditText? = null
    var lastNameTxt: TextInputEditText? = null
    var userNameLayout: TextInputLayout? = null
    var passwordLayout: TextInputLayout? = null
    var firstNameLayout: TextInputLayout? = null
    var mobileNumberLayout: TextInputLayout? = null
    var lastNameLayout: TextInputLayout? = null
    var dbHelper = DatabaseHelper(this@RegisterActivity)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initView()
        userNameTxt!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (!userNameTxt!!.text.toString().isEmpty()) {
                    userNameLayout!!.error = null
                }
            }
        })
        firstNameTxt!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (!firstNameTxt!!.text.toString().isEmpty()) {
                    firstNameLayout!!.error = null
                }
            }
        })
        lastNameTxt!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (!lastNameTxt!!.text.toString().isEmpty()) {
                    lastNameLayout!!.error = null
                }
            }
        })
        mobileNumberTxt!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (!mobileNumberTxt!!.text.toString().isEmpty()) {
                    mobileNumberLayout!!.error = null
                }
            }
        })
        passwordTxt!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (!passwordTxt!!.text.toString().isEmpty()) {
                    passwordLayout!!.error = null
                }
            }
        })
    }

    private fun initView() {
        loginTxt = findViewById(R.id.login_txt)
        registerBtn = findViewById(R.id.register_btn)
        userNameLayout = findViewById(R.id.username_layout)
        userNameTxt = findViewById(R.id.username_txt)
        firstNameTxt = findViewById(R.id.first_name_txt)
        firstNameLayout = findViewById(R.id.first_name_layout)
        lastNameTxt = findViewById(R.id.last_name_txt)
        lastNameLayout = findViewById(R.id.last_name_layout)
        mobileNumberLayout = findViewById(R.id.mobile_number_layout)
        mobileNumberTxt = findViewById(R.id.mobile_number_txt)
        passwordLayout = findViewById(R.id.password_layout)
        passwordTxt = findViewById(R.id.password_txt)
        loginTxt?.setOnClickListener(this)
        registerBtn?.setOnClickListener(this)
    }

    private fun checkCredentials(username: String, mobileNumber: String): Boolean {
        val db = dbHelper.readableDatabase
        val projection = arrayOf(DatabaseHelper.COLUMN_ID)
        val selection =
            DatabaseHelper.COLUMN_USERNAME + " = ? AND " + DatabaseHelper.COLUMN_MOBILENUMBER + " = ?"
        val selectionArgs = arrayOf(username, mobileNumber)
        val cursor = db.query(
            DatabaseHelper.TABLE_USERS,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        val isValid = cursor.moveToFirst()
        cursor.close()
        db.close()
        return isValid
    }

    override fun onClick(view: View) {
        if (view.id == R.id.login_txt) {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            finish()
        }
        if (view.id == R.id.register_btn) {
            if (userNameTxt!!.text.toString().length == 0) {
                userNameLayout!!.error = "fill this field"
            }
            if (firstNameTxt!!.text.toString().length == 0) {
                firstNameLayout!!.error = "fill this field"
            }
            if (lastNameTxt!!.text.toString().length == 0) {
                lastNameLayout!!.error = "fill this field"
            }
            if (mobileNumberTxt!!.text.toString().length == 0) {
                mobileNumberLayout!!.error = "fill this field"
            }
            if (passwordTxt!!.text.toString().length == 0) {
                passwordLayout!!.error = "fill this field"
            }
            if (userNameTxt!!.text.toString().length != 0 && firstNameTxt!!.text.toString().length != 0 && lastNameTxt!!.text.toString().length != 0 && mobileNumberTxt!!.text.toString().length != 0 && passwordTxt!!.text.toString().length != 0) {
                val isValid = checkCredentials(
                    userNameTxt!!.text.toString(),
                    mobileNumberTxt!!.text.toString()
                )
                if (isValid) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "already used mobile number or userName",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val db = dbHelper.writableDatabase
                    val values = ContentValues()
                    values.put(DatabaseHelper.COLUMN_USERNAME, userNameTxt!!.text.toString())
                    values.put(DatabaseHelper.COLUMN_FIRSTNAME, firstNameTxt!!.text.toString())
                    values.put(DatabaseHelper.COLUMN_LASTNAME, lastNameTxt!!.text.toString())
                    values.put(
                        DatabaseHelper.COLUMN_MOBILENUMBER,
                        mobileNumberTxt!!.text.toString()
                    )
                    values.put(DatabaseHelper.COLUMN_PASSWORD, passwordTxt!!.text.toString())
                    val userId = db.insert(DatabaseHelper.TABLE_USERS, null, values)
                    db.close()
                    PreferencesHelper.setPreference(
                        this@RegisterActivity,
                        PreferencesHelper.PREF_TOKEN,
                        "TOKEN"
                    )
                    PreferencesHelper.setPreference(
                        this@RegisterActivity,
                        PreferencesHelper.PREF_USERID,
                        userId.toString()
                    )
                    val intent = Intent(this@RegisterActivity, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}