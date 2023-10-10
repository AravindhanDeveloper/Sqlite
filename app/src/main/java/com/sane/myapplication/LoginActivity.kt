package com.sane.myapplication

import android.annotation.SuppressLint
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
import com.sane.myapplication.RegisterActivity

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private var registerTxt: MaterialTextView? = null
    private var loginBtn: MaterialButton? = null
    private var userNameTxt: TextInputEditText? = null
    private var passwordTxt: TextInputEditText? = null
    private var userNameLayout: TextInputLayout? = null
    private var passwordLayout: TextInputLayout? = null
    private var dbHelper: DatabaseHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initView()


    }

    private fun initView() {
        dbHelper = DatabaseHelper(this)
        registerTxt = findViewById(R.id.register_txt)
        loginBtn = findViewById(R.id.login_btn)
        userNameLayout = findViewById(R.id.username_layout)
        userNameTxt = findViewById(R.id.username_txt)
        passwordLayout = findViewById(R.id.password_layout)
        passwordTxt = findViewById(R.id.password_txt)
        registerTxt?.setOnClickListener(this)
        loginBtn?.setOnClickListener(this)

        // Initialize the TextWatcher
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateLoginButtonState()
            }

            override fun afterTextChanged(s: Editable?) {}
        }
        userNameTxt ?. addTextChangedListener (textWatcher)
        passwordTxt?.addTextChangedListener(textWatcher)

        // Disable the login button initially
        loginBtn?.isEnabled = false
    }

    private fun updateLoginButtonState() {
        val username = userNameTxt?.text.toString()
        val password = passwordTxt?.text.toString()
        val isValidInput = !username.isEmpty() && !password.isEmpty()
        loginBtn?.isEnabled = isValidInput
    }

    @SuppressLint("Range")
    private fun checkCredentials(username: String, password: String): Boolean {
        val db = dbHelper?.readableDatabase
        val projection = arrayOf(DatabaseHelper.COLUMN_ID)
        val selection =
            "${DatabaseHelper.COLUMN_USERNAME} = ? AND ${DatabaseHelper.COLUMN_PASSWORD} = ?"
        val selectionArgs = arrayOf(username, password)
        val cursor = db?.query(
            DatabaseHelper.TABLE_USERS,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        cursor?.use {
            if (it.moveToFirst()) {
                val userId = it.getString(it.getColumnIndex(DatabaseHelper.COLUMN_ID))
                PreferencesHelper.setPreference(
                    this@LoginActivity,
                    PreferencesHelper.PREF_TOKEN,
                    "TOKEN"
                )
                PreferencesHelper.setPreference(
                    this@LoginActivity,
                    PreferencesHelper.PREF_USERID,
                    userId.toString()
                )
                return true
            }
        }
        return false
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.register_txt -> {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
                finish()
            }

            R.id.login_btn -> {
                val username = userNameTxt?.text.toString()
                val password = passwordTxt?.text.toString()
                val isValid = checkCredentials(username, password)
                if (isValid) {
                    Toast.makeText(this@LoginActivity, "Login successful", Toast.LENGTH_SHORT)
                        .show()
                    startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        "Invalid username or password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}
