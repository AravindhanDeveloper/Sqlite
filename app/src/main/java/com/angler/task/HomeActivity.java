package com.angler.task;

import static com.angler.task.PreferencesHelper.signOut;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class HomeActivity extends AppCompatActivity {
    DatabaseHelper dbHelper = new DatabaseHelper(HomeActivity.this);
    long userId;
    MaterialButton logoutBtn;
    MaterialTextView userNameTxt, fullNameTxt, mobileNumberTxt, lastNameTxt;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        logoutBtn = findViewById(R.id.logout_btn);
        fullNameTxt = findViewById(R.id.fullname_txt);
        lastNameTxt = findViewById(R.id.last_name_txt);
        mobileNumberTxt = findViewById(R.id.mobile_number_txt);
        userNameTxt = findViewById(R.id.username_txt);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut(HomeActivity.this);
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        userId = Long.parseLong(PreferencesHelper.getPreference(HomeActivity.this, PreferencesHelper.PREF_USERID));

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                DatabaseHelper.COLUMN_ID,
                DatabaseHelper.COLUMN_FIRSTNAME,
                DatabaseHelper.COLUMN_USERNAME,
                DatabaseHelper.COLUMN_LASTNAME,
                DatabaseHelper.COLUMN_MOBILENUMBER,
                DatabaseHelper.COLUMN_PASSWORD
        };

        String selection = DatabaseHelper.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(userId)};

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
            @SuppressLint("Range")String username = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USERNAME));
            @SuppressLint("Range") String firstname = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FIRSTNAME));
            @SuppressLint("Range")  String lastname = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_LASTNAME));
            @SuppressLint("Range")String mobilenumber = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_MOBILENUMBER));
            fullNameTxt.setText(firstname);
            lastNameTxt.setText(lastname);
            mobileNumberTxt.setText(mobilenumber);
            userNameTxt.setText(username);

        }

        cursor.close();
        db.close();

    }
}