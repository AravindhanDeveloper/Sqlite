package com.angler.task;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    MaterialTextView loginTxt;
    MaterialButton registerBtn;
    TextInputEditText userNameTxt, passwordTxt, firstNameTxt, mobileNumberTxt, lastNameTxt;
    TextInputLayout userNameLayout, passwordLayout, firstNameLayout, mobileNumberLayout, lastNameLayout;
    DatabaseHelper dbHelper = new DatabaseHelper(RegisterActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        userNameTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!userNameTxt.getText().toString().isEmpty() ) {
                    userNameLayout.setError(null);
                }
            }
        }); firstNameTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!firstNameTxt.getText().toString().isEmpty() ) {
                    firstNameLayout.setError(null);
                }
            }
        }); lastNameTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!lastNameTxt.getText().toString().isEmpty() ) {
                    lastNameLayout.setError(null);
                }
            }
        }); mobileNumberTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!mobileNumberTxt.getText().toString().isEmpty() ) {
                    mobileNumberLayout.setError(null);
                }
            }
        }); passwordTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!passwordTxt.getText().toString().isEmpty() ) {
                    passwordLayout.setError(null);
                }
            }
        });

    }

    private void initView() {
        loginTxt = findViewById(R.id.login_txt);
        registerBtn = findViewById(R.id.register_btn);
        userNameLayout = findViewById(R.id.username_layout);
        userNameTxt = findViewById(R.id.username_txt);
        firstNameTxt = findViewById(R.id.first_name_txt);
        firstNameLayout = findViewById(R.id.first_name_layout);
        lastNameTxt = findViewById(R.id.last_name_txt);
        lastNameLayout = findViewById(R.id.last_name_layout);
        mobileNumberLayout = findViewById(R.id.mobile_number_layout);
        mobileNumberTxt = findViewById(R.id.mobile_number_txt);
        passwordLayout = findViewById(R.id.password_layout);
        passwordTxt = findViewById(R.id.password_txt);
        loginTxt.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
    }

    private boolean checkCredentials(String username, String mobileNumber) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {DatabaseHelper.COLUMN_ID};
        String selection = DatabaseHelper.COLUMN_USERNAME + " = ? AND " + DatabaseHelper.COLUMN_MOBILENUMBER + " = ?";
        String[] selectionArgs = {username, mobileNumber};

        Cursor cursor = db.query(DatabaseHelper.TABLE_USERS, projection, selection, selectionArgs, null, null, null);

        boolean isValid = cursor.moveToFirst();
        cursor.close();
        db.close();

        return isValid;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.login_txt) {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        }
        if (view.getId() == R.id.register_btn) {
            if (userNameTxt.getText().toString().length() == 0) {
                userNameLayout.setError("fill this field");
            }
            if (firstNameTxt.getText().toString().length() == 0) {
                firstNameLayout.setError("fill this field");
            }
            if (lastNameTxt.getText().toString().length() == 0) {
                lastNameLayout.setError("fill this field");
            }
            if (mobileNumberTxt.getText().toString().length() == 0) {
                mobileNumberLayout.setError("fill this field");
            }
            if (passwordTxt.getText().toString().length() == 0) {
                passwordLayout.setError("fill this field");
            }
            if (userNameTxt.getText().toString().length() != 0 && firstNameTxt.getText().toString().length() != 0 && lastNameTxt.getText().toString().length() != 0 && mobileNumberTxt.getText().toString().length() != 0 && passwordTxt.getText().toString().length() != 0) {
                boolean isValid = checkCredentials(userNameTxt.getText().toString(), mobileNumberTxt.getText().toString());

                if (isValid) {
                    Toast.makeText(RegisterActivity.this, "already used mobile number or userName", Toast.LENGTH_SHORT).show();

                } else {
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put(DatabaseHelper.COLUMN_USERNAME, userNameTxt.getText().toString());
                    values.put(DatabaseHelper.COLUMN_FIRSTNAME, firstNameTxt.getText().toString());
                    values.put(DatabaseHelper.COLUMN_LASTNAME, lastNameTxt.getText().toString());
                    values.put(DatabaseHelper.COLUMN_MOBILENUMBER, mobileNumberTxt.getText().toString());
                    values.put(DatabaseHelper.COLUMN_PASSWORD, passwordTxt.getText().toString());
                    long userId = db.insert(DatabaseHelper.TABLE_USERS, null, values);
                    db.close();
                    PreferencesHelper.setPreference(RegisterActivity.this, PreferencesHelper.PREF_TOKEN, "TOKEN");
                    PreferencesHelper.setPreference(RegisterActivity.this, PreferencesHelper.PREF_USERID, String.valueOf(userId));
                    Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }

            }


        }
    }
}