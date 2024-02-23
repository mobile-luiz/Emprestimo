package com.testeapp.emprestimo

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth


class Recuperar_senha : AppCompatActivity() {
    private var inputEmail: EditText? = null
    private var btnReset: Button? = null
    private var btnBack: Button? = null
    private var auth: FirebaseAuth? = null
    private var progressBar: ProgressBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperar_senha)



        inputEmail = findViewById<View>(R.id.emaill) as EditText
        btnReset = findViewById<View>(R.id.btn_reset_password) as Button
        btnBack = findViewById<View>(R.id.btn_back) as Button
        progressBar = findViewById<View>(R.id.progressBar) as ProgressBar
        auth = FirebaseAuth.getInstance()
        btnBack!!.setOnClickListener { v: View? -> finish() }
        btnReset!!.setOnClickListener { v: View? ->
            val email = inputEmail!!.text.toString().trim { it <= ' ' }
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(
                    application,
                    "Digite seu ID de e-mail cadastrado",
                    Toast.LENGTH_SHORT
                )
                    .show()
                return@setOnClickListener
            }
            progressBar!!.visibility = View.VISIBLE
            auth!!.sendPasswordResetEmail(email)
                .addOnCompleteListener { task: Task<Void?> ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            this@Recuperar_senha,
                            "Nós lhe enviamos instruções para redefinir sua senha!",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    } else {
                        Toast.makeText(
                            this@Recuperar_senha,
                            "Não foi possível enviar o email de redefinição!",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                    progressBar!!.visibility = View.GONE
                }
        }
    }
}

