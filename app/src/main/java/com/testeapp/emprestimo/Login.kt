package com.testeapp.emprestimo
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var inputEmail: EditText
    private lateinit var inputPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        FirebaseAuth.getInstance().signOut() // opção sair do farebase


        mAuth = FirebaseAuth.getInstance()

        // Check if user is already logged in
        if (mAuth.currentUser != null) {
            goToLoggedInActivity()
        }

        inputEmail = findViewById(R.id.email)
        inputPassword = findViewById(R.id.password)
        btnLogin = findViewById(R.id.btn_login)

        btnLogin.setOnClickListener {
            val email = inputEmail.text.toString()
            val password = inputPassword.text.toString()

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            signIn(email, password)
        }

        val cadastroButton = findViewById<Button>(R.id.cadastro)
        cadastroButton.setOnClickListener {
            startActivity(Intent(this, Cadastro::class.java))
        }

        val senhaButton = findViewById<Button>(R.id.senha)
        senhaButton.setOnClickListener {
            startActivity(Intent(this, Recuperar_senha::class.java))
        }
    }

    private fun signIn(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    goToLoggedInActivity()
                } else {
                    Toast.makeText(this, "Erro ao conectar", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun goToLoggedInActivity() {
        startActivity(Intent(this, Usuario_logado::class.java))
        finish()
    }
}
