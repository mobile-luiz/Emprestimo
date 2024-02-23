package com.testeapp.emprestimo

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Cadastro : AppCompatActivity() {
    private lateinit var mFullName: EditText
    private lateinit var mEmail: EditText
    private lateinit var mPassword: EditText
    private lateinit var mPhone: EditText
    private lateinit var mRegisterBtn: Button
    private lateinit var mLoginBtn: TextView
    private lateinit var fAuth: FirebaseAuth
    private lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        mFullName = findViewById(R.id.profileName)
        mEmail = findViewById(R.id.Email)
        mPassword = findViewById(R.id.password)
        mPhone = findViewById(R.id.phone)
        mRegisterBtn = findViewById(R.id.registerBtn)
        mLoginBtn = findViewById(R.id.createText)
        fAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().getReference("users")

        if (fAuth.currentUser != null) {
            startActivity(Intent(applicationContext, Usuario_logado::class.java))
            finish()
        }

        mRegisterBtn.setOnClickListener {
            val email = mEmail.text.toString().trim()
            val password = mPassword.text.toString().trim()
            val fullName = mFullName.text.toString()
            val phone = mPhone.text.toString()

            if (TextUtils.isEmpty(email)) {
                mEmail.error = "O e-mail é obrigatório."
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(password)) {
                mPassword.error = "Senha requerida."
                return@setOnClickListener
            }

            if (password.length < 6) {
                mPassword.error = "A senha deve ter no mínimo 6 caracteres"
                return@setOnClickListener
            }

            val progressDialog = ProgressDialog(this@Cadastro)
            progressDialog.setMessage("Verificando disponibilidade...")
            progressDialog.setCancelable(false)
            progressDialog.show()

            // Você pode remover a verificação de CPF aqui

            mDatabase.orderByChild("email").equalTo(email)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            progressDialog.dismiss()
                            Toast.makeText(this@Cadastro, "E-mail já cadastrado", Toast.LENGTH_SHORT).show()
                        } else {
                            fAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        val fuser = fAuth.currentUser
                                        val userID = fuser!!.uid

                                        val user = User(fullName, email, phone, userID)

                                        mDatabase.child(userID).setValue(user)
                                            .addOnSuccessListener {
                                                progressDialog.dismiss()
                                                Toast.makeText(
                                                    this@Cadastro,
                                                    "Usuário registrado com sucesso",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                startActivity(
                                                    Intent(
                                                        applicationContext,
                                                        Usuario_logado::class.java
                                                    )
                                                )
                                            }
                                            .addOnFailureListener {
                                                progressDialog.dismiss()
                                                Toast.makeText(
                                                    this@Cadastro,
                                                    "Falha ao registrar usuário",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }

                                        fuser.sendEmailVerification()
                                            .addOnSuccessListener {
                                                Toast.makeText(
                                                    this@Cadastro,
                                                    "O e-mail de verificação foi enviado.",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }.addOnFailureListener {
                                                // Handle the error.
                                            }
                                    } else {
                                        progressDialog.dismiss()
                                        Toast.makeText(
                                            this@Cadastro,
                                            "Erro! " + task.exception!!.message,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        progressDialog.dismiss()
                        Toast.makeText(
                            this@Cadastro,
                            "Erro ao verificar e-mail",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }

        mLoginBtn.setOnClickListener {
            startActivity(
                Intent(
                    applicationContext,
                    Login::class.java
                )
            )
        }
    }

    public override fun onPause() {
        super.onPause()
        finish()
    }

    @SuppressLint("MissingSuperCall")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    override fun onBackPressed() {
        finishAffinity()
    }
}
