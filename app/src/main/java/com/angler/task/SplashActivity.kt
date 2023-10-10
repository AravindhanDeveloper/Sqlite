package com.angler.task

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.angler.task.PreferencesHelper.getPreference

class SplashActivity : AppCompatActivity() {
    var token: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        token = getPreference(this@SplashActivity, PreferencesHelper.PREF_TOKEN)
        Handler().postDelayed({
            if (token!!.isNotEmpty()) {
                val intent = Intent(this@SplashActivity, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 3000)
    }
}