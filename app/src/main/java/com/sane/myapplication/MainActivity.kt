package com.sane.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.sane.myapplication.PreferencesHelper.getPreference


class MainActivity : AppCompatActivity() {
    var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        token = getPreference(this@MainActivity, PreferencesHelper.PREF_TOKEN)
        Handler().postDelayed({
            if (token!!.isNotEmpty()) {
                val intent = Intent(this@MainActivity, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 3000)
    }
}