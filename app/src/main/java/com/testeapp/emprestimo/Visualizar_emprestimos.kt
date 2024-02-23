package com.testeapp.emprestimo

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Visualizar_emprestimos : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EmprestimoAdapter
    private lateinit var emprestimos: MutableList<Emprestimos>
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualizar_emprestimos)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        emprestimos = mutableListOf()
        adapter = EmprestimoAdapter(emprestimos)
        recyclerView.adapter = adapter

        database = FirebaseDatabase.getInstance().reference
        val emprestimosRef = database.child("emprestimos")

        emprestimosRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (emprestimoSnapshot in snapshot.children) {
                    val emprestimo = emprestimoSnapshot.getValue(Emprestimos::class.java)
                    emprestimo?.let {
                        emprestimos.add(it)
                    }
                }
                emprestimos.reverse() // Inverte a ordem da lista
                adapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Visualizar_emprestimos, "Failed to retrieve data", Toast.LENGTH_SHORT).show()
            }
        })
    }
}