package com.angler.task;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    MaterialTextView registerTxt;
    MaterialButton loginBtn;
    TextInputEditText userNameTxt, passwordTxt;
    TextInputLayout userNameLayout, passwordLayout;
    private DatabaseHelper dbHelper;
    TextWatcher textWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        userNameTxt.addTextChangedListener(
         new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!userNameTxt.getText().toString().isEmpty() &&
                        !passwordTxt.getText().toString().isEmpty()) {
                    loginBtn.setEnabled(true);
                } else {
                    loginBtn.setEnabled(false);
                }
            }
        });
        passwordTxt.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (!userNameTxt.getText().toString().isEmpty() &&
                                !passwordTxt.getText().toString().isEmpty()) {
                            loginBtn.setEnabled(true);
                        } else {
                            loginBtn.setEnabled(false);
                        }
                    }
                });


    }

    private void initView() {
        dbHelper = new DatabaseHelper(this);
        registerTxt = findViewById(R.id.register_txt);
        loginBtn = findViewById(R.id.login_btn);
        userNameLayout = findViewById(R.id.username_layout);
        userNameTxt = findViewById(R.id.username_txt);
        passwordLayout = findViewById(R.id.password_layout);
        passwordTxt = findViewById(R.id.password_txt);
        registerTxt.setOnClickListener(this);
        loginBtn.setOnClickListener(this);

    }

    private boolean checkCredentials(String username, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {DatabaseHelper.COLUMN_ID};
        String selection = DatabaseHelper.COLUMN_USERNAME + " = ? AND " + DatabaseHelper.COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_USERS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String userId = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
            PreferencesHelper.setPreference(LoginActivity.this, PreferencesHelper.PREF_TOKEN, "TOKEN");
            PreferencesHelper.setPreference(LoginActivity.this, PreferencesHelper.PREF_USERID, String.valueOf(userId));

        }
        boolean isValid = cursor.moveToFirst();
        cursor.close();
        db.close();

        return isValid;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.register_txt) {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            finish();
        }
        if (view.getId() == R.id.login_btn) {

            String username = userNameTxt.getText().toString();
            String password = passwordTxt.getText().toString();

            boolean isValid = checkCredentials(username, password);

            if (isValid) {
                Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
            }

        }
    }
}