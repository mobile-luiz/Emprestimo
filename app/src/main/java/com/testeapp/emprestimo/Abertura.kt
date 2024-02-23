package com.testeapp.emprestimo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class Abertura : AppCompatActivity() {

    private val SPLASH_TIME_OUT: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_abertura)
        startLoginActivityAfterDelay()
    }

    private fun startLoginActivityAfterDelay() {
        Handler().postDelayed({
            val loginIntent = Intent(this@Abertura, Login::class.java)
            startActivity(loginIntent)
            finish()
        }, SPLASH_TIME_OUT)
    }
}
