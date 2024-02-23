package com.testeapp.emprestimo


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.testeapp.emprestimo.databinding.ActivityUsuarioLogadoBinding

class Usuario_logado : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityUsuarioLogadoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsuarioLogadoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarUsuarioLogado.toolbar)

        binding.appBarUsuarioLogado.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_usuario_logado)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Encontre o TextView com o ID "t1" e defina um OnClickListener
        val novoEmprestimoTextView: TextView = findViewById(R.id.t1)
        novoEmprestimoTextView.setOnClickListener {
            // Inicie a nova atividade aqui
            val intent = Intent(this, Emprestimo::class.java)
            startActivity(intent)
        }

        // Encontre o TextView com o ID "t2" e defina um OnClickListener
        val T2TextView: TextView = findViewById(R.id.t2)
        T2TextView.setOnClickListener {
            // Inicie a nova atividade aqui
            val intent = Intent(this, Visualizar_emprestimos::class.java)
            startActivity(intent)
        }


        // Encontre o TextView com o ID "t2" e defina um OnClickListener
        val T3TextView: TextView = findViewById(R.id.t3)
        T3TextView.setOnClickListener {
            // Inicie a nova atividade aqui
            val intent = Intent(this, GraficoActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.usuario_logado, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.t1 -> {
                // Abra a tela T1
                val intent = Intent(this, Emprestimo::class.java)
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_usuario_logado)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}